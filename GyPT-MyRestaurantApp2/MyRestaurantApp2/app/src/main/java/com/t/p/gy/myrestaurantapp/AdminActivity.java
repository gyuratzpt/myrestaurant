package com.t.p.gy.myrestaurantapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.t.p.gy.myrestaurantapp.adapter.AdapterForAdminRecyclerView;

import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity{
    private RecyclerView adminRecyclerView;
    private RecyclerView.Adapter adminRecyclerViewAdapter;
    static Map<String, Integer> drawableMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Log.v("MyLog", "Admin oncreate start...");

        Button addButton = (Button) findViewById(R.id.adminactivity_addbutton);
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

        initBackButton();
        initDrawMap();

        adminRecyclerView = (RecyclerView) findViewById(R.id.adminactivity_recyclerview) ;
        adminRecyclerView.setHasFixedSize(true);
        adminRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adminRecyclerViewAdapter = new AdapterForAdminRecyclerView();
        adminRecyclerView.setAdapter(adminRecyclerViewAdapter);
        Log.i("myLog", "Admin oncreate vége...");
    }

    //vissza gomb
    private void initBackButton() {
        //vissza gomb
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //vissza gomb vége

    private void initDrawMap() {
        drawableMap = new HashMap<>();
        drawableMap.put("birramoretti", getApplicationContext().getResources().getIdentifier("birramoretti","drawable", getPackageName()));
        drawableMap.put("cola", getApplicationContext().getResources().getIdentifier("cola","drawable", getPackageName()));
        drawableMap.put("corona", getApplicationContext().getResources().getIdentifier("corona","drawable", getPackageName()));
        drawableMap.put("csiga", getApplicationContext().getResources().getIdentifier("csiga","drawable", getPackageName()));
        drawableMap.put("donerkebab", getApplicationContext().getResources().getIdentifier("donerkebab","drawable", getPackageName()));
        drawableMap.put("duplahamburger", getApplicationContext().getResources().getIdentifier("duplahamburger","drawable", getPackageName()));
        drawableMap.put("durum", getApplicationContext().getResources().getIdentifier("durum","drawable", getPackageName()));
        drawableMap.put("extrahamburger", getApplicationContext().getResources().getIdentifier("extrahamburger","drawable", getPackageName()));
        drawableMap.put("fanta", getApplicationContext().getResources().getIdentifier("fanta","drawable", getPackageName()));
        drawableMap.put("hamburger", getApplicationContext().getResources().getIdentifier("hamburger","drawable", getPackageName()));
        drawableMap.put("hell", getApplicationContext().getResources().getIdentifier("hell","drawable", getPackageName()));
        drawableMap.put("kilkenny", getApplicationContext().getResources().getIdentifier("kilkenny","drawable", getPackageName()));
        drawableMap.put("sprite", getApplicationContext().getResources().getIdentifier("sprite","drawable", getPackageName()));
        drawableMap.put("stella", getApplicationContext().getResources().getIdentifier("stella","drawable", getPackageName()));
        drawableMap.put("wizard", getApplicationContext().getResources().getIdentifier("wizard","drawable", getPackageName()));
    }

    public static Map getDrawableMap(){
        return drawableMap;
    }

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