package com.t.p.gy.myrestaurantapp;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ProductsBackend {

    @GET("/api/v1/foods")
    Observable<Response<JsonObject>> getFoods();
	
	@GET("/api/v1/drinks")
    Observable<Response<JsonObject>> getDrinks();
	
	@FormUrlEncoded
	@POST("/api/v1/foods")
    Observable<Response<JsonObject>> addFoods(@Field("product_category") String name,
                                              @Field("amount") String description,
                                              @Field("currency") Integer price);

	@FormUrlEncoded	
	@POST("/api/v1/drinks")
    Observable<Response<JsonObject>> addDrinks(@Field("product_category") String name,
                                              @Field("amount") String description,
                                              @Field("currency") Integer price);
}
