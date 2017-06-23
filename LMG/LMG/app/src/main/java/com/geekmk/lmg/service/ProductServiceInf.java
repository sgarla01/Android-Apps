package com.geekmk.lmg.service;


import java.util.LinkedHashMap;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Mani on 10/06/17.
 */

public interface ProductServiceInf {

    @GET("assignment.json")
    Call<LinkedHashMap<String,Object>> getProducts();
}
