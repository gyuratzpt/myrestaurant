package com.t.p.gy.myrestaurantapp.connection;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductsBackend {

    //LEKÉRDEZÉSEK//
    //teljes lekérdezések
    @GET("/api/v1/products")
    Observable<Response<JsonObject>> getAllProducts();
    @GET("/api/v1/orders")
    Observable<Response<JsonObject>> getAllOrders();
    @GET("/api/v1/categories")
    Observable<Response<JsonObject>> getCategories();

    @HTTP(method = "GET", path = "/api/v1/products/cat/{categoryID}")
    Observable<Response<JsonObject>> getFilteredProducts(@Path("categoryID") int _cat);


    //elem lekérdezések
    @HTTP(method = "GET", path = "/api/v1/products/{id}")
    Observable<Response<JsonObject>> getProductByID(@Path("id") int id);

    @HTTP(method = "GET", path = "/api/v1/users/{email}")
    Observable<Response<JsonObject>> getOneUserByEmail(@Path("email") String _email);


    //product műveletek
    @HTTP(method = "DELETE", path = "/api/v1/products/items/{id}", hasBody = true)
    Observable<Response<ResponseBody>> deleteProduct(@Path("id") int id);

    @FormUrlEncoded
    @POST("/api/v1/products")
    Observable<Response<JsonObject>> addProduct(@Field("categoryID") Integer categoryID,
                                                @Field("name") String name,
                                                @Field("detail") String detail,
                                                @Field("price") Integer price,
                                                @Field("picture") String picture);

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "/api/v1/products/put/{id}", hasBody = true)
    Observable<Response<ResponseBody>> updateProduct(@Path("id") int id,
                                                        @Field("categoryID") Integer categoryID,
                                                        @Field("name") String name,
                                                        @Field("detail") String detail,
                                                        @Field("price") Integer price,
                                                        @Field("picture") String picture);








    //order műveletek
    @FormUrlEncoded
    @POST("/api/v1/orders")
    Observable<Response<JsonObject>> writeOrder(@Field("userid") int userID,
                                                @Field("productid") int productID,
                                                  @Field("amount") int amount);

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "/api/v1/orders/put/{id}", hasBody = true)
    Observable<Response<ResponseBody>> finalizeOrder(@Path("id") int id,
                                                     @Field("status") int status);


    @HTTP(method = "GET", path = "/api/v1/orders/get/{id}")
    Observable<Response<ResponseBody>> finalizeOrder_Gettel(@Path("id") int id);









    //user műveletek
    @FormUrlEncoded
    @POST("/api/v1/login")
    Observable<Response<JsonObject>> login(@Field("email") String email,
                                               @Field("password") String password);
    @FormUrlEncoded
    @POST("/api/v1/register")
    Observable<Response<JsonObject>> registration(@Field("email") String email,
                                            @Field("password") String password,
                                            @Field("username") String username,
                                            @Field("address") String address,
                                            @Field("phonenumber") String phone);

}
/*
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
    @POST("/api/v1/drinks")
    Observable<Response<JsonObject>> addDrinks(@Field("name") String name,
                                               @Field("detail") String detail,
                                               @Field("price") Integer price,
                                               @Field("picture") String picture);
    @HTTP(method = "DELETE", path = "/api/v1/drinks/items/{name}", hasBody = true)
    Observable<Response<ResponseBody>> deleteDrinks(@Path("name") String namepath);
    @HTTP(method = "DELETE", path = "/api/v1/foods/items/{name}", hasBody = true)
    Observable<Response<ResponseBody>> deleteFoods(@Path("name") String namepath);
    @FormUrlEncoded
    @HTTP(method = "PUT", path = "/api/v1/drinks/{name}", hasBody = true)
    Observable<Response<ResponseBody>> updateDrinks(@Path("name") String namepath,
                                                       @Field("name") String name,
                                                       @Field("detail") String detail,
                                                       @Field("price") Integer price,
                                                       @Field("picture") String picture);
 */