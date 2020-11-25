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
    RecyclerView.Adapter menuRecyclerViewAdapter;

    //UI elemek
    RecyclerView menuRecyclerView;
    Spinner menuSpinner;
    TextView placeholder;
    Button addToCartbutton;
    static TextView tv_SummedPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initUI();

        menuRecyclerView = (RecyclerView) findViewById(R.id.menuactivity_recyclerview) ;
        menuRecyclerView.setHasFixedSize(true);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        menuRecyclerViewAdapter = new AdapterForMenuRecyclerView(getApplicationContext(), tv_SummedPrice);
        DividerItemDecoration decoration = new DividerItemDecoration(this, VERTICAL);
        menuRecyclerView.addItemDecoration(decoration);
        menuRecyclerView.setAdapter(menuRecyclerViewAdapter);

    }

    //*******************************************//
                        //action bar / menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.cart:
                Intent myIntent = new Intent(MenuActivity.this, CartActivity.class);
                startActivity(myIntent);
                return true;
            case R.id.logout:
                myDataProcessor.initLogoutOption(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
                        //menu vége
    //*******************************************//

    private void initUI(){
        menuSpinner = (Spinner) findViewById(R.id.menuactivity_spinner);
        tv_SummedPrice = (TextView) findViewById(R.id.menuactivity_price);
        placeholder = (TextView) findViewById(R.id.menuactivity_title);
        addToCartbutton = (Button) findViewById(R.id.menuactivity_addtocartbutton);

        tv_SummedPrice.setText(R.string.menuactivity_price_text);
        addToCartbutton.setText(R.string.menuactivity_addtocart_button_text);
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
        //spinner
        ArrayAdapter menuSpinnerArrayAdapter;
        menuSpinnerArrayAdapter = new ArrayAdapter(MenuActivity.this, R.layout.spinner_item, myDataProcessor.getCustomizedCategoryList("Összes tétel"));
        menuSpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        menuSpinner.setAdapter(menuSpinnerArrayAdapter);

        //viselkedés
        menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0) {
                    placeholder.setText(adapterView.getSelectedItem().toString());
                    //menuRecyclerViewAdapter = new AdapterForMenuRecyclerView(tv_SummedPrice, myDataProcessor.getFilteredProducts(adapterView.getSelectedItemPosition()));
                    menuRecyclerViewAdapter = new AdapterForMenuRecyclerView(getApplicationContext(), tv_SummedPrice, adapterView.getSelectedItemPosition());

                }
                else{
                    placeholder.setText("Teljes választék");
                    //menuRecyclerViewAdapter = new AdapterForMenuRecyclerView(tv_SummedPrice, myDataProcessor.getProductList());
                    menuRecyclerViewAdapter = new AdapterForMenuRecyclerView(getApplicationContext(), tv_SummedPrice);

                }
                menuRecyclerView.swapAdapter(menuRecyclerViewAdapter, true);
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