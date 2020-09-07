package com.t.p.gy.myrestaurantapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.t.p.gy.myrestaurantapp.adapter.ItemAdapter_v2;
import com.t.p.gy.myrestaurantapp.data.Cart;
import com.t.p.gy.myrestaurantapp.data.SingleMenuItem;

public class FoodActivity extends AppCompatActivity {
    static ListView listView;
    static FoodProcessor dp = new FoodProcessor();
    static TextView tv_SummedPrice;
    Cart myCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_menu_layout);
        initBackButton();

        Log.i("myLog", "Foods, activity: itemadapter_v2 start");
        final ItemAdapter_v2 itemAdapter_v2 = new ItemAdapter_v2(this, dp.getFoodsList(), R.color.MyCustomColorShade_4);
        listView = (ListView) findViewById(R.id.shared_menu_layout_listview);
        listView.setAdapter(itemAdapter_v2);
        Log.i("myLog", "Foods, activity: itemadapter_v2 finish");
        tv_SummedPrice = (TextView) findViewById(R.id.shared_menu_layout_price);
        tv_SummedPrice.setText(R.string.shared_menu_layout_price_text);
/*
A kiválasztott tételeket ebben az Activityben egyszerre lehet a kosárhoz adni!
 */
        Button button = (Button) findViewById(R.id.shared_menu_layout_tocart_button);
        button.setText(R.string.shared_menu_layout_tocart_button_text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dp.addSelectedItemsToCart();

                Toast.makeText(view.getContext(), "A tételek bekerültek a kosárba!", Toast.LENGTH_LONG).show();
                itemAdapter_v2.refreshlistview();

            }
        });
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
        for(SingleMenuItem x : dp.getFoodsList()){
            x.resetOrderAmount(); //el és visszanavigálás után törli a korábbi értékeket
        }
        Log.i("MyLog","MainActivity onResume");
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

    //saját metódusok
    public static void refreshSummedPrice(){
        tv_SummedPrice.setText(String.valueOf(dp.refreshActualOrderPrice()));
    }
}