package com.t.p.gy.myrestaurantapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.t.p.gy.myrestaurantapp.connection.ProductsBackend;
import com.t.p.gy.myrestaurantapp.connection.RetrofitClient;
import com.t.p.gy.myrestaurantapp.data.SingleMenuItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AdminOrdersActivity extends AppCompatActivity {
    ProductsBackend myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    TextView tempTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders);
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(ProductsBackend.class);

        tempTV = (TextView) findViewById(R.id.admin_orders_temptv);
        String actualOrders = downloadOrders();
        tempTV.setText(actualOrders);

    }

    private String downloadOrders(){
        String tmp = "xyz";
        StringBuilder tmpString = new StringBuilder();

        compositeDisposable.add(myAPI.getAllOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300) {
                        JsonArray inputJSONArray = response.body().getAsJsonArray("order");
                        for (int i = 0; i < inputJSONArray.size(); i++) {
                            tmpString.append(inputJSONArray.get(i).getAsJsonObject().get("orderID").toString().replaceAll("\"", "")
                                    + " " + inputJSONArray.get(i).getAsJsonObject().get("userID").toString().replaceAll("\"", "")
                                    + " " + inputJSONArray.get(i).getAsJsonObject().get("productID").toString().replaceAll("\"", "")
                                    + " " + inputJSONArray.get(i).getAsJsonObject().get("amount").toString().replaceAll("\"", "")
                                    + "\n"
                            );}}
                    else {
                        Log.i("myLog", "AdminOrders error: " + response.code() + " " + response.errorBody().string());
                    }
                 })
        );

        Log.i("myLog", "végleges Stringbuilder: " + tmpString.toString());
        return tmpString.toString();
    }
}