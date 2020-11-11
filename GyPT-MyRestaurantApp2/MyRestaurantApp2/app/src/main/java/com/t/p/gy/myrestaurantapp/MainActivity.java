package com.t.p.gy.myrestaurantapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.t.p.gy.myrestaurantapp.data.DataProcessor;

public class MainActivity extends AppCompatActivity {
    DataProcessor myDataProcessor = DataProcessor.getInstance();;

    //UI
    ImageView logo_imageView, actual_story_imageView, menu_imageView, gallery_imageView, contact_imageView;
    Button adminMaintenanceButton, adminOrdersButton;

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_v2);

        initUI();
        if (myDataProcessor.isUserAdmin()) initAdmin();

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
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                return true;
            case R.id.logout:
                myDataProcessor.initLogoutOption(this);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //******************----------********************//

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private void initUI(){
        logo_imageView = findViewById(R.id.logo);
        actual_story_imageView = findViewById(R.id.actual_story);
        menu_imageView = findViewById(R.id.menu);
        gallery_imageView = findViewById(R.id.gallery);
        contact_imageView = findViewById(R.id.contact);

        menu_imageView.setImageResource(R.drawable.menu);
        gallery_imageView.setImageResource(R.drawable.gallery);
        contact_imageView.setImageResource(R.drawable.contact);

        logo_imageView.setImageBitmap(myDataProcessor.getImage("UI", "logo"));
        actual_story_imageView.setImageBitmap(myDataProcessor.getImage("stories", "actual_story"));

        adminMaintenanceButton = findViewById(R.id.admin_maintenancebutton);
        adminOrdersButton = findViewById(R.id.admin_ordersbutton);

        adminMaintenanceButton.setVisibility(View.GONE);
        adminOrdersButton.setVisibility(View.GONE);

        //  MENU megnyitasa
        menu_imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MainActivity.this,
                        MenuActivity.class);
                startActivity(myIntent);
            }
        });

        //  GALLERY megnyitasa
        gallery_imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MainActivity.this,
                        GalleryActivity.class);
                startActivity(myIntent);
            }
        });

        //  CONTACT megnyitasa
        contact_imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MainActivity.this,
                        ContactActivity.class);
                startActivity(myIntent);
            }
        });
    }
    private void initAdmin(){
            adminMaintenanceButton.setVisibility(View.VISIBLE);
            adminOrdersButton.setVisibility(View.VISIBLE);
            adminMaintenanceButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    // Start Admin
                    Intent myIntent = new Intent(MainActivity.this,
                            AdminMaintenanceActivity.class);
                    startActivity(myIntent);
                }
            });
            adminOrdersButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    // Start Admin
                    Intent myIntent = new Intent(MainActivity.this,
                            AdminOrdersActivity.class);
                    startActivity(myIntent);
                }
            });
    }
}