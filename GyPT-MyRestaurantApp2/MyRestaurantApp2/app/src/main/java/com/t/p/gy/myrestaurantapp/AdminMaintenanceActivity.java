package com.t.p.gy.myrestaurantapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.t.p.gy.myrestaurantapp.adapter.AdapterForAdminRecyclerView;
import com.t.p.gy.myrestaurantapp.connection.NetworkConnector;
import com.t.p.gy.myrestaurantapp.other.AdminDialog;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.VERTICAL;

public class AdminMaintenanceActivity extends AppCompatActivity{
    NetworkConnector anc = NetworkConnector.getInstance();
    private RecyclerView adminRecyclerView;
    private RecyclerView.Adapter adminRecyclerViewAdapter;
    //static Map<String, Integer> drawableMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintenance);
        Log.v("MyLog", "Admin oncreate start...");
        initUI();

        adminRecyclerView = (RecyclerView) findViewById(R.id.adminactivity_recyclerview) ;
        adminRecyclerView.setHasFixedSize(true);
        adminRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adminRecyclerViewAdapter = new AdapterForAdminRecyclerView(this);
        adminRecyclerView.setAdapter(adminRecyclerViewAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(this, VERTICAL);
        adminRecyclerView.addItemDecoration(decoration);
        Log.i("myLog", "Admin oncreate vége...");
    }

    private void initUI(){
        Button addButton = (Button) findViewById(R.id.adminactivity_addbutton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initAddButton();
            }
        });
        Button filterButton = (Button) findViewById(R.id.adminactivity_filterbutton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> l = new ArrayList<>();
                Log.i("myLog", "Button interface-el: " + l);
            }
        });


        Button searchButton = (Button) findViewById(R.id.adminactivity_searchbutton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("myLog", "SearchButton: " + anc.getOneUserByEmail("aaa@aaa.aa"));
            }
        });
        initBackButton();

    }

    //vissza gomb
    private void initBackButton() {
        //vissza gomb
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //vissza gomb vége

    private void initAddButton(){
        AdminDialog alert = new AdminDialog();
        alert.showDialog(this, "Új termék hozzáadása","Hozzáad", null, null);
        //adaptert értesíteni valahogy
        //előtte frissíteni a listát is (ez csak az adatbázisba dolgozik jelenleg)!!
        adminRecyclerViewAdapter.notifyItemInserted(adminRecyclerViewAdapter.getItemCount());
    }

}