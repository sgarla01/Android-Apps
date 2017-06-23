package com.geekmk.lmg.catalog;

import com.geekmk.lmg.catalog.product.Product;

import java.util.List;

/**
 * Created by Mani on 10/06/17.
 */

public class Catalog {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    private List<Product> productList;

}
