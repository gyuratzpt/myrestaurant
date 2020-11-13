package com.t.p.gy.myrestaurantapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.t.p.gy.myrestaurantapp.data.DataProcessor;
import com.t.p.gy.myrestaurantapp.data.User;

public class OrderInfoActivity extends AppCompatActivity {
    DataProcessor myDataProcessor = DataProcessor.getInstance();

    //UI
    EditText etName, etAddress, etPhoneNumber, etAdditional;
    Button backButton, sendOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);

        initUI();

        etName = (EditText) findViewById(R.id.order_info_name_input);
        etName.setText(myDataProcessor.getUserName());

        etAddress = (EditText) findViewById(R.id.order_info_address_input);
        etAddress.setText(myDataProcessor.getUserAddress());

        etPhoneNumber = (EditText) findViewById(R.id.order_info_phonenumber_input);
        etPhoneNumber.setText(myDataProcessor.getUserPhoneNumber());

        etAdditional = (EditText) findViewById(R.id.order_info_additionalinfos_input);
    }

    private void initUI(){
        backButton = (Button) findViewById(R.id.order_info_backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(OrderInfoActivity.this,
                        CartActivity.class);
                startActivity(myIntent);
            }
        });
        sendOrderButton = (Button) findViewById(R.id.order_info_sendbutton);
        sendOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myDataProcessor.getCart().size()>0) {
                    Log.i("myLog", "Kosár tartalma: " + myDataProcessor.getCart().toString());
                    Toast.makeText(OrderInfoActivity.this, "Rendelés elküldve!", Toast.LENGTH_LONG).show();
                    myDataProcessor.initOrder(etAdditional.getText().toString());
                    Handler h = new Handler();
                    Runnable r = () -> {
                        Log.i("myLog", "Kések");
                        myDataProcessor.fillOrder();
                        myDataProcessor.getCart().clear();
                    };
                    h.postDelayed(r, 250);
                }
                else Toast.makeText(OrderInfoActivity.this, "Üres rendelést nem lehet leadni!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
