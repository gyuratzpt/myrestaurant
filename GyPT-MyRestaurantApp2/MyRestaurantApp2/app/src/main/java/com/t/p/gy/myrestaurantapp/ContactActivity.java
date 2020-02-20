package com.t.p.gy.myrestaurantapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_v2);
        //vissza gomb
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Log.v("ContactActivity", "OK");
    }

    public void sendEmail(View view) {
        EditText nameField = (EditText) findViewById(R.id.emailEditText);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "App üzenet");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"gyuratzpt@gmail.com"});
        intent.putExtra(Intent.EXTRA_TEXT, nameField.getText().toString());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void startCall(View view) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:06308563576"));
        try {
            startActivity(callIntent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

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
}