package com.t.p.gy.myrestaurantapp;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.t.p.gy.myrestaurantapp.adapter.ItemAdapter;
import com.t.p.gy.myrestaurantapp.adapter.ItemAdapterForDrinkActivity;
import com.t.p.gy.myrestaurantapp.data.SingleMenuItem;

public class DrinkActivity extends AppCompatActivity {
    static ListView listView;
    static DrinkProcessor_v3 drinkProcessor = new DrinkProcessor_v3();
    static TextView tv_SummedPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_v3);
        initBackButton();

        Log.i("myLog", "Drinkactivity_v3, activity: itemadapter start");
        final ItemAdapterForDrinkActivity itemAdapterForDrinkActivity = new ItemAdapterForDrinkActivity(this, drinkProcessor.getDrinksList(), R.color.MyCustomColorShade_5);
        listView = (ListView) findViewById(R.id.drinkactivitylayout_listview);
        listView.setAdapter(itemAdapterForDrinkActivity);
        Log.i("myLog", "Drinks, activity: itemadapter finish");
        tv_SummedPrice = (TextView) findViewById(R.id.drinkactivitylayout_price);
        tv_SummedPrice.setText(R.string.shared_menu_layout_price_text);

        Button button = (Button) findViewById(R.id.drinkactivitylayout_addtocartbutton);
        button.setText(R.string.shared_menu_layout_tocart_button_text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drinkProcessor.addSelectedItemsToCart();
                Toast.makeText(view.getContext(), "A tételek bekerültek a kosárba!", Toast.LENGTH_LONG).show();
                itemAdapterForDrinkActivity.refreshlistview();
                refreshSummedPrice();
            }
        });

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

    //saját metódusok
    @Override
    protected void onResume() {
        super.onResume();
        for(SingleMenuItem x : drinkProcessor.getDrinksList()){
            x.resetOrderAmount(); //el és visszanavigálás után törli a korábbi értékeket
        }
        Log.i("MyLog", "DrinkActivity: onResume");
    }

    public static void refreshSummedPrice(){
        tv_SummedPrice.setText(String.valueOf(drinkProcessor.refreshActualOrderPrice()));
    }
}