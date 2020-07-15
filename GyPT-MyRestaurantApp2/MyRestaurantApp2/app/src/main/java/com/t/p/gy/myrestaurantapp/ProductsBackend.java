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
    Observable<Response<JsonObject>> addFoods(@Field("name") String name,
                                              @Field("detail") String detail,
                                              @Field("price") Integer price,
                                              @Field("picture") String picture);

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "/api/v1/foods/{name}", hasBody = true)
    Observable<Response<ResponseBody>> updateFoods(@Path("name") String namepath,
                                                       @Field("name") String name,
                                                       @Field("detail") String detail,
                                                       @Field("price") Integer price,
                                                       @Field("picture") String picture);

    @HTTP(method = "DELETE", path = "/api/v1/foods/items/{name}", hasBody = true)
    Observable<Response<ResponseBody>> deleteFoods(@Path("name") String namepath);

	@FormUrlEncoded	
	@POST("/api/v1/drinks")
    Observable<Response<JsonObject>> addDrinks(@Field("name") String name,
                                              @Field("detail") String detail,
                                              @Field("price") Integer price,
                                               @Field("picture") String picture);

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "/api/v1/drinks/{name}", hasBody = true)
    Observable<Response<ResponseBody>> updateDrinks(@Path("name") String namepath,
                                                       @Field("name") String name,
                                                       @Field("detail") String detail,
                                                       @Field("price") Integer price,
                                                       @Field("picture") String picture);

    @HTTP(method = "DELETE", path = "/api/v1/drinks/items/{name}", hasBody = true)
    Observable<Response<ResponseBody>> deleteDrinks(@Path("name") String namepath);

    @FormUrlEncoded
    @POST("/api/v1/login")
    Observable<Response<JsonObject>> login(@Field("email") String email,
                                               @Field("password") String password);

    @FormUrlEncoded
    @POST("/api/v1/register")
    Observable<Response<JsonObject>> registration(@Field("email") String email,
                                           @Field("password") String password);
}
