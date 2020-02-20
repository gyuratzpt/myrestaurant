package com.t.p.gy.myrestaurantapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

public class DrinksActivity extends AppCompatActivity {
    static DataProcessor dp = new DataProcessor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drink_menu_layout);
        //vissza gomb
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Log.i("DrinksActivity", "1");
        // Create an ItemAdapter, whose data source is a list of drinks.
        // The adapter knows how to create list items for each item in the list.
        ItemAdapter itemAdapter = new ItemAdapter(this, dp.getDrinksList(), R.color.MyCustomColorShade_5);
        ListView listView = (ListView) findViewById(R.id.drink_menu_layout);
        listView.setAdapter(itemAdapter);
        Log.i("DrinksActivity", "2");
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
        Log.i("MainActivity", "onResume");
    }

}