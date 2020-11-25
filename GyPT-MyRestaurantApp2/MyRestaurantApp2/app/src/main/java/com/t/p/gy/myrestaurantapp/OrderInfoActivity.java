package com.t.p.gy.myrestaurantapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
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
    Button sendOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);

        initUI();

    }

    private void initUI(){
        etName = (EditText) findViewById(R.id.order_info_name_input);
        etName.setText(myDataProcessor.getUserName());
        etAddress = (EditText) findViewById(R.id.order_info_address_input);
        etAddress.setText(myDataProcessor.getUserAddress());
        etPhoneNumber = (EditText) findViewById(R.id.order_info_phonenumber_input);
        etPhoneNumber.setText(myDataProcessor.getUserPhoneNumber());
        etAdditional = (EditText) findViewById(R.id.order_info_additionalinfos_input);

        sendOrderButton = (Button) findViewById(R.id.order_info_sendbutton);
        sendOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i("myLog", "Kosár tartalma: " + myDataProcessor.getCart().toString());
                if(!etName.getText().toString().equals(myDataProcessor.getUserName()) ||
                        !etAddress.getText().toString().equals(myDataProcessor.getUserAddress()) ||
                        !etPhoneNumber.getText().toString().equals(myDataProcessor.getUserPhoneNumber())
                ){
                    //Toast.makeText(OrderInfoActivity.this, "Egy vagy több adat megváltozott! Mentsem a módosításokat a jövőre nézve?", Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderInfoActivity.this);
                    builder.setPositiveButton("Igen, szeretném a jövőben is ezeket használni!", (dialog, id) -> {
                        if(!etName.getText().toString().equals(myDataProcessor.getUserName())) myDataProcessor.setUserName(etName.getText().toString());
                        if(!etAddress.getText().toString().equals(myDataProcessor.getUserAddress())) myDataProcessor.setUserAddress(etAddress.getText().toString());
                        if(!etPhoneNumber.getText().toString().equals(myDataProcessor.getUserPhoneNumber())) myDataProcessor.setUserPhoneNumber(etPhoneNumber.getText().toString());
                        myDataProcessor.updateUser();
                        sendFinishedOrder();
                    });
                    builder.setNegativeButton("Nem, ez csak erre a rendelésre vonatkozik!", (dialog, id) -> {
                        StringBuilder tmpStr = new StringBuilder();
                        if(etAdditional.getText().toString().length()>0) tmpStr.append(etAdditional.getText().toString());
                        if(!etName.getText().toString().equals(myDataProcessor.getUserName())) tmpStr.append("\nIdeiglenes név: " + etName.getText().toString());
                        if(!etAddress.getText().toString().equals(myDataProcessor.getUserAddress())) tmpStr.append("\nIdeiglenes cím: " + etAddress.getText().toString());
                        if(!etPhoneNumber.getText().toString().equals(myDataProcessor.getUserPhoneNumber())) tmpStr.append("\nIdeiglenes telefonszám: " + etPhoneNumber.getText().toString());
                        etAdditional.setText(tmpStr.toString());
                        sendFinishedOrder();
                    });
                    AlertDialog alert = builder.create();
                    alert.setTitle("Egy vagy több adat megváltozott! Mentsem a módosításokat?");
                    alert.show();
                }
                else sendFinishedOrder();
            }
        });
    }

    private void sendFinishedOrder(){
        try {
            Log.i("myLog", "sendOrder try...");
            myDataProcessor.initOrder(etAdditional.getText().toString());
        }catch (Exception e){
            Toast.makeText(OrderInfoActivity.this, "Rendelés létrehozása sikertelen: " + e, Toast.LENGTH_LONG).show();
        }
            Handler h = new Handler();
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        myDataProcessor.fillOrder();
                        myDataProcessor.getCart().clear();
                        Toast.makeText(OrderInfoActivity.this, "Rendelés elküldve!", Toast.LENGTH_LONG).show();
                        sendOrderButton.setClickable(false);
                    } catch (Exception e) {
                        Toast.makeText(OrderInfoActivity.this, "initOrder meghiusult! Hiba oka: " + e, Toast.LENGTH_LONG).show();
                    }
                }
            };
        }
}
