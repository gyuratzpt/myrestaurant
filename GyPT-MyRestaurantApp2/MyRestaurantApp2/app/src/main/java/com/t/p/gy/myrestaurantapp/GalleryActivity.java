package com.t.p.gy.myrestaurantapp;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Gallery;

import com.t.p.gy.myrestaurantapp.data.DataProcessor;

public class GalleryActivity extends AppCompatActivity {
    DataProcessor myDataProcessor = DataProcessor.getInstance();
    private Integer[] images={
            R.drawable.rest0,
            R.drawable.rest1,
            R.drawable.rest2,
            R.drawable.rest3,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Gallery imgGalleryActivity = (Gallery) findViewById(R.id.innerGallery);

        imgGalleryActivity.setAdapter(new ImageAdapter(this));
        imgGalleryActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Toast.makeText(getApplicationContext(), "Image " + arg2, Toast.LENGTH_SHORT).show();
            }
        });

        Log.v("MyLog","Gallery OK");
    }

    public class ImageAdapter extends BaseAdapter {
        private Context context;
//        int imageBackground;

        public ImageAdapter(Context context) {

            this.context = context;
        }

        @Override
        public int getCount() {

            return images.length;
        }

        @Override
        public Object getItem(int arg0) {

            return arg0;
        }

        @Override
        public long getItemId(int arg0) {

            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {

            ImageView imageView = new ImageView(context);
            imageView.setImageResource(images[arg0]);
            return imageView;
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
                Intent myIntent = new Intent(GalleryActivity.this, CartActivity.class);
                startActivity(myIntent);
                return true;
            case R.id.logout:
                myDataProcessor.initLogoutOption(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //******************Action bar********************//



}
