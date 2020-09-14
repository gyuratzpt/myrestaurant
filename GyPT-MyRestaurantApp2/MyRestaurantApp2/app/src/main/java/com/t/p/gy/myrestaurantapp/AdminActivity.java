package com.t.p.gy.myrestaurantapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.t.p.gy.myrestaurantapp.adapter.AdapterForAdminRecyclerView;
import com.t.p.gy.myrestaurantapp.connection.ProductsBackend;
import com.t.p.gy.myrestaurantapp.connection.RetrofitClient;

public class AdminActivity extends AppCompatActivity{
    private RecyclerView adminRecyclerView;
    private RecyclerView.Adapter adminRecyclerViewAdapter;
    //CompositeDisposable compositeDisposable = new CompositeDisposable();
    //ProductsBackend myAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_layout);
        Log.v("MyLog", "Admin oncreate start...");

        //Retrofit retrofit = RetrofitClient.getInstance();
        //myAPI = retrofit.create(ProductsBackend.class);
        //vissza gomb
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Button addButton = (Button) findViewById(R.id.admin_activity_addbutton);
        //Button filterButton = (Button) findViewById(R.id.admin_activity_filter);
        //Button searchButton = (Button) findViewById(R.id.admin_activity_search);
        Button testButton = findViewById(R.id.admin_temp_testbutton);

        //metódus teszteléshez ideiglenes gomb
        testButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                /*
                compositeDisposable.add(myAPI.getFoodKebab()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            Log.i("myLog", "response: " + response.toString());
                            if (response.code() >= 200 && response.code() < 300) {
                                Log.i("myLog", "tesztmetódus: " + response.body().toString());
                                JsonArray allUsersJsonArray = response.body().getAsJsonArray("food");
                                //Log.i("myLog", "Teszt, getFoodKebab input mérete: " + allUsersJsonArray.size());
                                //Log.i("myLog", "Teszt, getFoodKebab input elemei: " + allUsersJsonArray.toString());
                                JsonObject jsonamount = allUsersJsonArray.get(0).getAsJsonObject();
                                //Log.i("myLog", "Teszt, jsonamount input mérete: " + jsonamount.size());
                                //Log.i("myLog", "Teszt, jsonamount input elemei: " + jsonamount.toString());

                            } else {
                                Log.i("myLog", "HIBA!!!" + response.code() + " " + response.errorBody().string());
                            }
                        }));

                 */
            }
        });

        adminRecyclerView = (RecyclerView) findViewById(R.id.admin_activity_recyclerview) ;
        adminRecyclerView.setHasFixedSize(true);
        adminRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adminRecyclerViewAdapter = new AdapterForAdminRecyclerView();
        adminRecyclerView.setAdapter(adminRecyclerViewAdapter);
        Log.v("MyLog", "Admin oncreate vége...");
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


    public static void makeMessage(final String inputString){
        //Toast.makeText(this.getContext), inputString, Toast.LENGTH_LONG).show();
    }
    /*
    private void createDrinkItem(final String name, final String description, final Integer price,final String picture){

        compositeDisposable.add((Disposable) myAPI.addDrinks(name, description, price, picture)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                }, throwable -> {
                    Toast.makeText(AdminActivity.this, "Drink added successfully!", Toast.LENGTH_SHORT).show();
                })
        );
    }

    private void createFoodItem(final String name, final String description, final Integer price,final String picture){
        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(AdminActivity.this);

        compositeDisposable.add(myAPI.addFoods(name, description, price, picture)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300){
                        Toast.makeText(AdminActivity.this, "Food added successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AdminActivity.this, "" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void changeDrinkItem(final String name, final String description, final Integer price,final String picture){
        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(AdminActivity.this);

        compositeDisposable.add(myAPI.updateDrinks(name, name, description, price, picture)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300){
                        Toast.makeText(AdminActivity.this, "Drink change successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AdminActivity.this, "" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void changeFoodItem(final String name, final String description, final Integer price,final String picture){
        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(AdminActivity.this);

        compositeDisposable.add(myAPI.updateFoods(name, name, description, price, picture)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300){
                        Toast.makeText(AdminActivity.this, "Food change successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AdminActivity.this, "" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void deleteDrinkItem(final String name){
        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(AdminActivity.this);

        compositeDisposable.add(myAPI.deleteDrinks(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300){
                        Toast.makeText(AdminActivity.this, "Drink delete successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AdminActivity.this, "" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void deleteFoodItem(final String name){
        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(AdminActivity.this);

        compositeDisposable.add(myAPI.deleteFoods(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300){
                        Toast.makeText(AdminActivity.this, "Food delete successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AdminActivity.this, "" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    */
}
