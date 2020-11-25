package com.t.p.gy.myrestaurantapp.connection;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BackendAPI {
    //user műveletek
    @HTTP(method = "GET", path = "/api/v1/login", hasBody = false)
    Observable<Response<JsonObject>> login(@Query("email") String email,
                                           @Query("password") String password);

    @FormUrlEncoded
    @POST("/api/v1/registration")
    Observable<Response<JsonObject>> registration(@Field("email") String email,
                                                  @Field("password") String password,
                                                  @Field("username") String username,
                                                  @Field("address") String address,
                                                  @Field("phonenumber") String phone);

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "/api/v1/users/put/{id}", hasBody = true)
    Observable<Response<JsonObject>> updateUser(@Path("id") int id,
                                                @Field("name") String name,
                                                @Field("address") String address,
                                                @Field("phonenumber") String phonenumber);



    //LEKÉRDEZÉSEK//
    //teljes lekérdezések
    @GET("/api/v1/products")
    Observable<Response<JsonObject>> getAllProducts();
    @GET("/api/v1/orders")
    Observable<Response<JsonObject>> getAllOrders();
    @GET("/api/v1/categories")
    Observable<Response<JsonObject>> getCategories();

    //elem lekérdezések
    @HTTP(method = "GET", path = "/api/v1/products/{id}")
    Observable<Response<JsonObject>> getProductByID(@Path("id") int id);

    //product műveletek
    @HTTP(method = "DELETE", path = "/api/v1/products/items/{id}", hasBody = true)
    Observable<Response<JsonObject>> deleteProduct(@Path("id") int id);

    @FormUrlEncoded
    @POST("/api/v1/products")
    Observable<Response<JsonObject>> addProduct(@Field("categoryID") Integer categoryID,
                                                @Field("name") String name,
                                                @Field("detail") String detail,
                                                @Field("price") Integer price,
                                                @Field("picture") String picture);

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "/api/v1/products/put/{id}", hasBody = true)
    Observable<Response<JsonObject>> updateProduct(@Path("id") int id,
                                                        @Field("categoryID") Integer categoryID,
                                                        @Field("name") String name,
                                                        @Field("detail") String detail,
                                                        @Field("price") Integer price,
                                                        @Field("picture") String picture);

    //order műveletek
    @FormUrlEncoded
    @POST("/api/v1/orders")
    Observable<Response<JsonObject>> initOrder(@Field("userid") int userid,
                                                  @Field("note") String note);

    @FormUrlEncoded
    @POST("/api/v1/orders/items")
    Observable<Response<JsonObject>> fillOrder(@Field("orderid") int orderid,
                                               @Field("productid") int productid,
                                                @Field("amount") int amount);

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "/api/v1/orders/put/{id}", hasBody = true)
    Observable<Response<JsonObject>> finalizeOrder(@Path("id") int id,
                                                     @Field("status") int status);

}