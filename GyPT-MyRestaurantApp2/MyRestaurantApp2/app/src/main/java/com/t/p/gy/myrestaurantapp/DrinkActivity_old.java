package com.t.p.gy.myrestaurantapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.t.p.gy.myrestaurantapp.adapter.ItemAdapter;
import com.t.p.gy.myrestaurantapp.data.SingleMenuItem;

public class DrinkActivity_old extends AppCompatActivity {
    static ListView listView;
    //static DrinkProcessor dp = new DrinkProcessor();
    static DrinkProcessor_v2 dp = new DrinkProcessor_v2();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drink_menu_layout);
        initBackButton();

        Log.i("myLog", "Drinks, activity: itemadapter start");
        final ItemAdapter itemAdapter = new ItemAdapter(this, dp.getDrinksList(), R.color.MyCustomColorShade_5);
        listView = (ListView) findViewById(R.id.drink_menu_layout);
        listView.setAdapter(itemAdapter);
        Log.i("myLog", "Drinks, activity: itemadapter finish");
    }

    private void initBackButton() {
        //vissza gomb
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for(SingleMenuItem x : dp.getDrinksList()){
            x.resetOrderAmount(); //el és visszanavigálás után törli a korábbi értékeket
        }
        Log.i("MyLog", "DrinkActivity: onResume");
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
}