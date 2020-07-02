package com.t.p.gy.myrestaurantapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;

public class DrinksActivity extends AppCompatActivity {
    static ListView listView;
    static DrinkProcessor dp = new DrinkProcessor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drink_menu_layout);
//vissza gomb
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        //Log.i("MyLog", "DrinksActivity: itemadapter start");
        final ItemAdapter itemAdapter = new ItemAdapter(this, dp.getDrinksList(), R.color.MyCustomColorShade_5);
        listView = (ListView) findViewById(R.id.drink_menu_layout);
        listView.setAdapter(itemAdapter);
        //Log.i("MyLog", "DrinksActivity: itemadapter finish");

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