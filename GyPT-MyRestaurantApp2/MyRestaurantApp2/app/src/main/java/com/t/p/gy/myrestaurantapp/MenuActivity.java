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
import com.t.p.gy.myrestaurantapp.connection.NetworkConnector;
import com.t.p.gy.myrestaurantapp.data.DataProcessor;
import com.t.p.gy.myrestaurantapp.data.SingleProductItem;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initBackButton();
        initSpinnerValues();

        Log.i("myLog", "Menuactivity: start");
        Button addToCartbutton;


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

        initSpinnerBehavior();

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

        Log.i("myLog","Menuactivity Konstruktor kész");
    }

    //*******************************************//
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
                        //menu vége
    //*******************************************//



    private void initSpinnerValues(){
        menuSpinner = findViewById(R.id.menuactivity_spinner);
        //NetworkConnector nc = new NetworkConnector();
        List<String> testList = new ArrayList<>();
        //List<String> testList;
        //testList = nc.downloadCategories();
        testList.add("food2");
        testList.add("drink2");
        testList.add("drug2");

        ArrayAdapter menuSpinnerArrayAdapter = new ArrayAdapter(MenuActivity.this, R.layout.spinner_item, testList);
        menuSpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        menuSpinner.setAdapter(menuSpinnerArrayAdapter);

    }

    private void initSpinnerBehavior() {
        menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Log.i("myLog", "Selected spinner value: " + adapterView.getSelectedItem());
                List<SingleProductItem> actualProductList = new ArrayList<SingleProductItem>();
                switch (adapterView.getSelectedItem().toString()){
                    case "food2":
                        //actualProductList = myDataProcessor.getProductList(actualProductList, 1);
                        myDataProcessor.getProductList(actualProductList, 1);
                        break;
                    case "drink2":
                        //actualProductList = myDataProcessor.getProductList(actualProductList, 2);
                        myDataProcessor.getProductList(actualProductList, 2);
                        break;
                    case "drug2":
                        //actualProductList = myDataProcessor.getProductList(actualProductList, 3);
                        myDataProcessor.getProductList(actualProductList, 3);
                        break;
                }

                menuRecyclerViewAdapter = new AdapterForMenuRecyclerView(tv_SummedPrice, actualProductList);
                menuRecyclerView.swapAdapter(menuRecyclerViewAdapter,true);



            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
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
