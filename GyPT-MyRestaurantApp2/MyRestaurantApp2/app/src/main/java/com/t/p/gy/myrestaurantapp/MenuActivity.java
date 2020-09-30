package com.t.p.gy.myrestaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.t.p.gy.myrestaurantapp.adapter.AdapterForMenuRecyclerView;
import com.t.p.gy.myrestaurantapp.data.DataProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.v7.widget.RecyclerView.VERTICAL;

public class MenuActivity extends AppCompatActivity {
    DataProcessor myDataProcessor = DataProcessor.getInstance();
    RecyclerView menuRecyclerView;
    Spinner menuSpinner;
    private RecyclerView.Adapter menuRecyclerViewAdapter;
    static TextView tv_SummedPrice;
    static Map<String, Integer> drawableMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initBackButton();
        initSpinner();
        Log.i("myLog", "Menuactivity: start");
        Button addToCartbutton;
        initDrawMap();



        tv_SummedPrice = (TextView) findViewById(R.id.menuactivity_price);
        tv_SummedPrice.setText(R.string.menuactivity_price_text);
        addToCartbutton = (Button) findViewById(R.id.menuactivity_addtocartbutton);
        addToCartbutton.setText(R.string.menuactivity_addtocart_button_text);


        menuRecyclerView = (RecyclerView) findViewById(R.id.menuactivity_recyclerview) ;
        menuRecyclerView.setHasFixedSize(true);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        menuRecyclerViewAdapter = new AdapterForMenuRecyclerView(tv_SummedPrice);
        DividerItemDecoration decoration = new DividerItemDecoration(this, VERTICAL);
        menuRecyclerView.addItemDecoration(decoration);
        menuRecyclerView.setAdapter(menuRecyclerViewAdapter);

        addToCartbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmpMessage;
                if (myDataProcessor.addSelectedItemsToCart(MenuActivity.this)){
                    tmpMessage ="A tételek a kosárba kerültek!";
                    Toast.makeText(MenuActivity.this, "A tételek a kosárba kerültek!", Toast.LENGTH_LONG).show();
                    refreshPriceTextView(0);
                }
                else {
                    tmpMessage ="Nincs rendelendő tétel!";
                    }
                Toast.makeText(MenuActivity.this, tmpMessage, Toast.LENGTH_LONG).show();
                //recyclerview frissítése!
                //refreshPriceTextView(0);
            }
        });





        Log.v("myLog","Menuactivity Konstruktor kész");
    }

    //menu
    private void initBackButton() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.cart:
                Intent myIntent = new Intent(MenuActivity.this, CartActivity.class);
                startActivity(myIntent);
                return true;
            case R.id.logout:
                Toast.makeText(this, "Kilépés", Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //menu
    // vége
    private void initSpinner(){
        menuSpinner = findViewById(R.id.menuactivity_spinner);
        List<String> getSpinnerList = new ArrayList<>();
        getSpinnerList.add("Étel");
        getSpinnerList.add("Ital");
        getSpinnerList.add("Koksz");
        //  spinner (lenyitható lista) peldanyositasa, feltoltese egy ArrayList objektumból, viselkedes beallitas
        ArrayAdapter menuSpinnerArrayAdapter = new ArrayAdapter(MenuActivity.this, R.layout.spinner_item, getSpinnerList);
        menuSpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        menuSpinner.setAdapter(menuSpinnerArrayAdapter);
        menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                /*
                menuSpinner.setSelection(0);
                if (i!=0) {
                    Intent myIntent;
                    switch (i) {
                        case 1:
                            myIntent = new Intent(MainActivity.this, FoodActivity.class);
                            startActivity(myIntent);
                            break;
                        case 2:
                            myIntent = new Intent(MainActivity.this, DrinkActivity.class);
                            startActivity(myIntent);
                            break;
                        case 3:
                            myIntent = new Intent(MainActivity.this, CartActivity.class);
                            startActivity(myIntent);
                            break;
                    }
                }
                */
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }
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


    //actions
    public static void refreshPriceTextView(int x){
        tv_SummedPrice.setText(Integer.toString(x) + "Ft");
    }

}
    /*
    //saját metódusok
    @Override
    protected void onResume() {
        super.onResume();
        for(SingleMenuItem x : foodProcessor.getFoodsList()){
            x.resetOrderAmount(); //el és visszanavigálás után törli a korábbi értékeket
        }
        Log.i("MyLog","FoodActivity onResume");
    }
     */
