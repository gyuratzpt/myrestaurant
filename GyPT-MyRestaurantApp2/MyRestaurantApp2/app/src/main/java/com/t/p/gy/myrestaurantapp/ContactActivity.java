package com.t.p.gy.myrestaurantapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.t.p.gy.myrestaurantapp.data.DataProcessor;


public class ContactActivity extends AppCompatActivity {
    DataProcessor myDataProcessor = DataProcessor.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Log.v("MyLog","ContactActivity OK");
    }

    public void sendEmail(View view) {
        EditText messageField = (EditText) findViewById(R.id.emailEditText);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "App üzenet");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"gyuratzpt@gmail.com"});
        intent.putExtra(Intent.EXTRA_TEXT, messageField.getText().toString());

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

    //******************Action bar********************//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart:
                Intent myIntent = new Intent(ContactActivity.this, CartActivity.class);
                startActivity(myIntent);
                return true;
            case R.id.logout:
                Toast.makeText(this, "Kilépés", Toast.LENGTH_LONG).show();
                myDataProcessor.logout();
                startActivity(new Intent(ContactActivity.this, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //******************Action bar********************//
}