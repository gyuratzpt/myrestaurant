package com.t.p.gy.myrestaurantapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class AdminActivity extends AppCompatActivity{
    static final private ArrayList<String> adminSpinnerList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_layout);

        //vissza gomb
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Log.v("MyLog","Gallery OK");

        //editText-ek példányosítása
        EditText eTName = (EditText) findViewById(R.id.admin_edittextName);
        eTName.setText("teszt -T- étel");
        EditText eTDescription = (EditText) findViewById(R.id.admin_edittextDescription);
        eTDescription.setText("teszt, teszt, teszt");
        EditText eTPrice = (EditText) findViewById(R.id.admin_edittextPrice);
        eTPrice.setText("500");
        //add gomb példányosítása
        Button addButton = (Button) findViewById(R.id.admin_button_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmpName = eTName.getText().toString();
                String tmpDescription = eTDescription.getText().toString();
                int tmpPrice = Integer.valueOf(eTPrice.getText().toString()); //input ellenőrzést később megcsinálni!!
                Toast.makeText(view.getContext(), "Add button pressed!", Toast.LENGTH_LONG).show();


            }
        });

        //  spinner (lenyitható lista) peldanyositasa, feltoltese egy ArrayList objektumból, viselkedes beallitas
        Spinner adminSpinner  = findViewById(R.id.admin_spinner);

        if (adminSpinnerList.isEmpty()) {
            adminSpinnerList.add("Choose category");
            adminSpinnerList.add("Foods");
            adminSpinnerList.add("Drinks");
        }

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(AdminActivity.this, R.layout.spinner_item, adminSpinnerList);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        adminSpinner.setAdapter(spinnerArrayAdapter);
        adminSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adminSpinner.setSelection(0);
                if (i!=0) {
                    switch (i) {
                        case 1:
                        //Foods kiválasztása esetén mi történjen
                        Toast.makeText(view.getContext(), "Foods kiválasztva!", Toast.LENGTH_LONG).show();
                            eTName.setText("testFood");
                            eTDescription.setText("foodteszt, foodteszt, foodteszt");
                            eTPrice.setText("1");
                            break;
                        case 2:
                        //Drinks kiválasztása esetén mi történjen
                        Toast.makeText(view.getContext(), "Drinks kiválasztva!", Toast.LENGTH_LONG).show();
                            eTName.setText("testDrink");
                            eTDescription.setText("drinkteszt, drinkteszt, drinkteszt");
                            eTPrice.setText("100");
                            break;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
//spinner vege




    }



    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
