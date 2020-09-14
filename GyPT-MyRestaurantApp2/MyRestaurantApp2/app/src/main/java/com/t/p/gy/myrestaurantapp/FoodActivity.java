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

import com.t.p.gy.myrestaurantapp.adapter.ItemAdapterForFoodActivity;
import com.t.p.gy.myrestaurantapp.data.SingleMenuItem;

public class FoodActivity extends AppCompatActivity {
    static ListView listView;
    static FoodProcessor foodProcessor = new FoodProcessor();
    static TextView tv_SummedPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        initBackButton();

        Log.i("myLog", "Foodactivity: itemadapter_v2 start");
        final ItemAdapterForFoodActivity itemAdapterforFoodActivity = new ItemAdapterForFoodActivity(this, foodProcessor.getFoodsList(), R.color.MyCustomColorShade_4);
        listView = (ListView) findViewById(R.id.shared_menu_layout_listview);
        listView.setAdapter(itemAdapterforFoodActivity);
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
                foodProcessor.addSelectedItemsToCart();
                Toast.makeText(view.getContext(), "A tételek bekerültek a kosárba!", Toast.LENGTH_LONG).show();
                itemAdapterforFoodActivity.refreshlistview();
            }
        });
    }

    //vissza gomb
    private void initBackButton() {

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
        for(SingleMenuItem x : foodProcessor.getFoodsList()){
            x.resetOrderAmount(); //el és visszanavigálás után törli a korábbi értékeket
        }
        Log.i("MyLog","FoodActivity onResume");
    }

    public static void refreshSummedPrice(){
        tv_SummedPrice.setText(String.valueOf(foodProcessor.refreshActualOrderPrice()));
    }

}