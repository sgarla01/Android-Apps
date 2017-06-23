package com.geekmk.lmg.catalog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.geekmk.lmg.R;
import com.geekmk.lmg.catalog.product.ProductViewPagerAdapter;
import com.geekmk.lmg.pagerindicator.CirclePageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mani on 09/06/17.
 */

public class CatalogFragment extends Fragment implements CatalogContract.View {

    @BindView(R.id.vp_products)
    ViewPager vpProducts;

    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    @BindView(R.id.ll_content)
    View llContent;

    private CatalogContract.Presenter mPresenter;

    private  ProductViewPagerAdapter productViewPagerAdapter;

    @BindView(R.id.tv_category_name)
    TextView tvCatalogName;

    @BindView(R.id.cpi_products)
    CirclePageIndicator circlePageIndicator;

    public static CatalogFragment newInstance() {
        return new CatalogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_catalog, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void setPresenter(CatalogContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
        showHideLoadingBar(true);
    }

    private void showHideLoadingBar(boolean isLoading) {
        if(isLoading){
            pbLoading.setVisibility(View.VISIBLE);
            llContent.setVisibility(View.GONE);
        }else{
            pbLoading.setVisibility(View.GONE);
            llContent.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //any on click listeners from activity
    }

    @OnClick({R.id.rb_indian,R.id.rb_saudi,R.id.rb_uae}) void onCurrencySelected(RadioButton radioButton){
        if(radioButton.isChecked()){
                switch (radioButton.getId()){
                    case R.id.rb_indian:
                        mPresenter.convertCurrency(productViewPagerAdapter.getHoldingProductList(),
                                getResources().getString(R.string.currency_indian));
                        break;
                    case R.id.rb_saudi:
                        mPresenter.convertCurrency(productViewPagerAdapter.getHoldingProductList(),
                                getResources().getString(R.string.currency_saudi));
                        break;
                    case R.id.rb_uae:
                        mPresenter.convertCurrency(productViewPagerAdapter.getHoldingProductList(),
                                getResources().getString(R.string.currency_uae));
                        break;
                }
        }
    }

    @Override
    public void systemFetchProductsSuccess(Catalog catalog) {
        showHideLoadingBar(false);
        Toast.makeText(getContext(),"Success Called",Toast.LENGTH_SHORT).show();
        tvCatalogName.setText(catalog.getName());
        productViewPagerAdapter = new ProductViewPagerAdapter(catalog.getProductList(),getContext());
        vpProducts.setAdapter(productViewPagerAdapter);
        circlePageIndicator.setViewPager(vpProducts);
    }

    @Override
    public void systemFetchProductsFailure(String reason) {
        Toast.makeText(getContext(),reason,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void convertCurrencySuccess(boolean isAllConverted) {

        productViewPagerAdapter.notifyDataSetChanged();
        if(!isAllConverted){
            Toast.makeText(getContext(),
                    "Selected Currency not available for some products",Toast.LENGTH_SHORT).show();
        }
    }
}
