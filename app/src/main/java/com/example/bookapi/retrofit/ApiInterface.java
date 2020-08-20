package com.example.bookapi.retrofit;

import com.example.bookapi.model.Products;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("products.json")
    Call<List<Products>> getAllProduct();
}
