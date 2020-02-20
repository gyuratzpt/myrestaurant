package com.t.p.gy.myrestaurantapp;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface Backend{

    @GET("/api/v1/products")
    Observable<Response<JsonObject>> getproducts(@Header("Authorization") String token );

}