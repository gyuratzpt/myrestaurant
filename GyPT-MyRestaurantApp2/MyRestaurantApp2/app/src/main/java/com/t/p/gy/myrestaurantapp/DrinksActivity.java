package com.t.p.gy.myrestaurantapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class DrinksActivity extends AppCompatActivity {
    static DataProcessor dp = new DataProcessor();
    ProductsBackend myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Gson gson = new GsonBuilder().setLenient().create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drink_menu_layout);
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(ProductsBackend.class);

//vissza gomb
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



        Log.i("MyLog", "DrinksActivity: itemadapter start");
        ItemAdapter itemAdapter = new ItemAdapter(this, dp.getDrinksList(), R.color.MyCustomColorShade_5);
        ListView listView = (ListView) findViewById(R.id.drink_menu_layout);
        listView.setAdapter(itemAdapter);
        Log.i("MyLog", "DrinksActivity: itemadapter finish");

        myAPI = retrofit.create(ProductsBackend.class);
        compositeDisposable.add(myAPI.getProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300) {
                        JsonArray allUsersJsonArray = response.body().getAsJsonArray("product");
                        JsonObject jsonamount = allUsersJsonArray.get(0).getAsJsonObject();
                        Toast.makeText(DrinksActivity.this, jsonamount.get("price").toString().replaceAll("\"", "") + " HUF", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(DrinksActivity.this, response.code() + " " + response.errorBody().string(), Toast.LENGTH_LONG).show();
                    }                }));
    }



//vissza gomb
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onResume() {
        super.onResume();
        for(SingleMenuItem x : dp.getDrinksList()){
            x.resetOrderAmount(); //el és visszanavigálás után törli a korábbi értékeket
        }
        Log.i("MyLog", "DrinkActivity: onResume");
    }

}