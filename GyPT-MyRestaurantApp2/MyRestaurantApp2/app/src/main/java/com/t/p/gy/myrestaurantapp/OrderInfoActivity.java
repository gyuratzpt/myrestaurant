package com.t.p.gy.myrestaurantapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.t.p.gy.myrestaurantapp.data.DataProcessor;
import com.t.p.gy.myrestaurantapp.data.User;

public class OrderInfoActivity extends AppCompatActivity {
    DataProcessor myDataProcessor = DataProcessor.getInstance();
    Gson gson = new GsonBuilder().setLenient().create();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(OrderInfoActivity.this);
        final User user = gson.fromJson(settings.getString("user","{}"), User.class);
        EditText etName = (EditText) findViewById(R.id.order_info_name_input);
        etName.setText(String.valueOf(user.getId()));
        EditText etAdditional = (EditText) findViewById(R.id.order_info_additionalinfos_input);
        etAdditional.setText(user.getEmail());


        Button backButton = (Button) findViewById(R.id.order_info_backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(OrderInfoActivity.this,
                        CartActivity.class);
                startActivity(myIntent);
            }
        });

        Button sendOrderButton = (Button) findViewById(R.id.order_info_sendbutton);
        sendOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDataProcessor.sendOrder();
            }
        });



    }
}
