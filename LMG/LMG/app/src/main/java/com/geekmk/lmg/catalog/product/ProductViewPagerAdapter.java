package com.geekmk.lmg.catalog.product;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekmk.lmg.R;
import com.geekmk.lmg.utils.StringUtils;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Mani on 09/06/17.
 */

public class ProductViewPagerAdapter extends PagerAdapter {



    private List<Product> productList;
    private Context mContext;
    private LayoutInflater inflater;
    public ProductViewPagerAdapter(List<Product> productList, Context context){
        Log.d(ProductViewPagerAdapter.class.getName(),"Product Viewpager with product list ");
        this.productList = productList;
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {


        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_product, collection, false);
        try {

            View view1=layout.findViewById(R.id.ll_view_1);

            position=position * 2;
            populateFirstView(view1,productList.get(position));
            view1.setTag(position);

            position++;

            View view2=layout.findViewById(R.id.ll_view_2);

            if (position < getActualCount()) {
                view1.setTag(position);
                populateSecondView(view2, productList.get(position));

                view2.setVisibility(View.VISIBLE);
            }
            else {
                view2.setVisibility(View.INVISIBLE);
            }
        }catch (Exception e){
            Log.e(ProductViewPagerAdapter.class.getName(),"Error while setting product info to view "+e.getMessage());
        }
        collection.addView(layout);
        return layout;
    }

    private  void populateFirstView(View view,Product product){
        ((TextView)view.findViewById(R.id.tv_product_title_1)).setText(product.getName());
        ((TextView)view.findViewById(R.id.tv_product_price_1)).setText(product.getCurrency()+" "+product.getPrice().setScale(2, BigDecimal.ROUND_UP));
        if(StringUtils.isNotEmpty(product.getUrl())){
            ImageView productImage = (ImageView)view.findViewById(R.id.iv_product_1);
            Picasso.with(mContext).load(product.getUrl()).into(productImage);
        }
    }

    private void populateSecondView(View view,Product product){
        ((TextView)view.findViewById(R.id.tv_product_title_2)).setText(product.getName());
        ((TextView)view.findViewById(R.id.tv_product_price_2)).setText(product.getCurrency()+" "+product.getPrice().setScale(2, BigDecimal.ROUND_UP));
        if(StringUtils.isNotEmpty(product.getUrl())){
            ImageView productImage = (ImageView)view.findViewById(R.id.iv_product_2);
            Picasso.with(mContext).load(product.getUrl()).into(productImage);
        }
    }

    public List<Product> getHoldingProductList(){
        return productList;
    }
    @Override
    public int getCount() {
        return((getActualCount() / 2) + 1);
    }

    public int getActualCount(){
        return productList.size();
    }
    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);

    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
