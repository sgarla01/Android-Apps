package com.geekmk.lmg.catalog;


import android.util.Log;

import com.geekmk.lmg.catalog.product.Product;
import com.geekmk.lmg.constants.RestUrls;
import com.geekmk.lmg.service.ProductServiceInf;
import com.geekmk.lmg.utils.ClientModelUtils;
import com.geekmk.lmg.utils.CurrencyConverter;
import com.geekmk.lmg.utils.JacksonRestRequestBuilder;
import com.geekmk.lmg.utils.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.geekmk.lmg.utils.ClientModelUtils.getString;

/**
 * Created by Mani on 09/06/17.
 */

public class CatalogPresenter implements CatalogContract.Presenter {

    private CatalogContract.View view;

    private Catalog catalog;


    private boolean isReloadData;

    public CatalogPresenter(CatalogContract.View view,boolean isReloadData){
        this.view = view;
        this.isReloadData = isReloadData;
        this.catalog = new Catalog();
        view.setPresenter(this);
    }

    @Override
    public void start() {
        if(isReloadData){
            systemFetchProducts();
        }
    }

    @Override
    public void systemFetchProducts() {

        ProductServiceInf productServiceInf = JacksonRestRequestBuilder
                .setupRestService(RestUrls.BASE_URL_PRODUCT, ProductServiceInf.class);

        Call<LinkedHashMap<String,Object>> call = productServiceInf.getProducts();

        call.enqueue(new Callback<LinkedHashMap<String, Object>>() {
            @Override
            public void onResponse(Call<LinkedHashMap<String, Object>> call, Response<LinkedHashMap<String, Object>> response) {
                isReloadData = false;
                Log.d(CatalogPresenter.class.getName(),"Get catagory rest request succesfull");

                LinkedHashMap<String,Object> responseBody = response.body();

                List<Object> conversionList = ClientModelUtils.getDataFromResponseAsList(responseBody,"conversion");

                CurrencyConverter currencyConverter = CurrencyConverter.getInstance();
                for(Object exchangeRate: conversionList){
                    LinkedHashMap<String,Object> conversionInfoMap = (LinkedHashMap<String,Object>)exchangeRate;
                    String fromCurrency = ClientModelUtils.getString(conversionInfoMap,"from");
                    String toCurrency = ClientModelUtils.getString(conversionInfoMap,"to");
                    String rate = ClientModelUtils.getString(conversionInfoMap,"rate");
                    currencyConverter.setRate(fromCurrency,toCurrency,StringUtils.toDouble(rate));
                }

                catalog.setName(getString(responseBody,"title"));
                List<Product> products = new ArrayList<>();
                List<Object> productList = ClientModelUtils.getDataFromResponseAsList(responseBody,"products");
                for (Object productObject:productList){
                    LinkedHashMap<String,Object> productMap = (LinkedHashMap<String,Object>) productObject;
                    Product product = new Product();
                    product.setUrl(getString(productMap,"url"));
                    product.setName(getString(productMap,"name"));
                    String price = getString(productMap,"price");
                    product.setPrice(new BigDecimal(price));
                    product.setCurrency(getString(productMap,"currency"));
                    products.add(product);
                }

                catalog.setProductList(products);
                view.systemFetchProductsSuccess(catalog);
            }

            @Override
            public void onFailure(Call<LinkedHashMap<String, Object>> call, Throwable t) {
                Log.d(CatalogPresenter.class.getName(),"Get catagory rest request failure");
                isReloadData = true;
                view.systemFetchProductsFailure("We could not contact out servers. Please check your internet connection and retry");
            }
        });
    }

    @Override
    public boolean isDataMissing() {
        return isReloadData;
    }

    @Override
    public void convertCurrency(List<Product> products, String selectedCurrency) {
        CurrencyConverter currencyConverter = CurrencyConverter.getInstance();

        boolean isAllConverted = true;

        for(Product product:products){
            if(currencyConverter.isConversionAvailable(product.getCurrency(),selectedCurrency)){
                BigDecimal result = currencyConverter.exchange(product.getCurrency(),selectedCurrency,product.getPrice());
                product.setCurrency(selectedCurrency);
                product.setPrice(result);
            }else{
                isAllConverted = product.getCurrency().equalsIgnoreCase(selectedCurrency);
            }
        }
        view.convertCurrencySuccess(isAllConverted);
    }
}
