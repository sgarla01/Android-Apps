package com.geekmk.lmg.catalog.product;

import java.math.BigDecimal;

/**
 * Created by Mani on 09/06/17.
 */

public class Product {


    private String url;

    private String name;

    private BigDecimal price;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    private String currency;

}
