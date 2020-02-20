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

public class FoodActivity extends AppCompatActivity {
    static ListView listView;
    static TextView tv_SummedPrice;
    static DataProcessor dp = new DataProcessor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_menu_layout);
        //vissza gomb
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final ItemAdapter_v2 itemAdapter_v2 = new ItemAdapter_v2(this, dp.getFoodsList(), R.color.MyCustomColorShade_4);
        listView = (ListView) findViewById(R.id.shared_menu_layout_listview);
        listView.setAdapter(itemAdapter_v2);


        tv_SummedPrice = (TextView) findViewById(R.id.shared_menu_layout_price);
        tv_SummedPrice.setText(R.string.shared_menu_layout_price_text);

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

    @Override
    protected void onResume() {
        super.onResume();
        for(SingleMenuItem x : dp.getFoodsList()){
            x.resetOrderAmount(); //el és visszanavigálás után törli a korábbi értékeket
        }
        Log.i("MainActivity", "onResume");
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

    public static void refreshSummedPrice(){
        tv_SummedPrice.setText(String.valueOf(dp.refreshActualOrderPrice()));
    }


}
