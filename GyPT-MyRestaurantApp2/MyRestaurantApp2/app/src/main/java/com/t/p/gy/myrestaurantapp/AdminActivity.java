package com.t.p.gy.myrestaurantapp;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AdminActivity extends AppCompatActivity{

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
        EditText eTDescription = (EditText) findViewById(R.id.admin_edittextDescription);
        EditText eTPrice = (EditText) findViewById(R.id.admin_edittextPrice);

        //add gomb példányosítása
        Button addButton = (Button) findViewById(R.id.admin_button_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmpName = eTName.getText().toString();
                String tmpDescription = eTDescription.getText().toString();
                int tmpPrice = Integer.valueOf(eTPrice.getText().toString()); //input ellenőrzést később megcsinálni!!


            }
        });

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
