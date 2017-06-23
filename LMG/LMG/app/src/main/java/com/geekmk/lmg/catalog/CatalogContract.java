package com.geekmk.lmg.catalog;

import com.geekmk.lmg.catalog.product.Product;
import com.geekmk.lmg.mvp.BasePresenter;
import com.geekmk.lmg.mvp.BaseView;

import java.util.List;

/**
 * Created by Mani on 09/06/17.
 */

public interface CatalogContract {

    interface View extends BaseView<Presenter> {

        void systemFetchProductsSuccess(Catalog catalog);

        void systemFetchProductsFailure(String reason);

        void convertCurrencySuccess(boolean isAllConverted);

    }

    interface Presenter extends BasePresenter {
        void systemFetchProducts();
        boolean isDataMissing();
        void convertCurrency(List<Product> products,String selectedCurrency);

    }


}
