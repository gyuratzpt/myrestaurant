package com.t.p.gy.myrestaurantapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.t.p.gy.myrestaurantapp.adapter.AdapterForAdminRecyclerView;
import com.t.p.gy.myrestaurantapp.other.AddDialog;

import java.util.HashMap;
import java.util.Map;

public class AdminMaintenanceActivity extends AppCompatActivity{
    private RecyclerView adminRecyclerView;
    private RecyclerView.Adapter adminRecyclerViewAdapter;
    static Map<String, Integer> drawableMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintenance);
        Log.v("MyLog", "Admin oncreate start...");

        Button addButton = (Button) findViewById(R.id.adminactivity_addbutton);
        //Button filterButton = (Button) findViewById(R.id.admin_activity_filter);
        //Button searchButton = (Button) findViewById(R.id.admin_activity_search);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initAddButton();
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

    private void initAddButton(){
        AddDialog alert = new AddDialog();
        alert.showDialog(this);
    }

}