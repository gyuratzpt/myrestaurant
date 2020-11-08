package com.t.p.gy.myrestaurantapp;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.t.p.gy.myrestaurantapp.adapter.AdapterForAdminRecyclerView;
import com.t.p.gy.myrestaurantapp.connection.NetworkConnector;
import com.t.p.gy.myrestaurantapp.data.DataProcessor;
import com.t.p.gy.myrestaurantapp.other.AdminDialog;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.VERTICAL;

public class AdminMaintenanceActivity extends AppCompatActivity{
    private RecyclerView.Adapter adminRecyclerViewAdapter;

    //UI
    private RecyclerView adminRecyclerView;

    //static Map<String, Integer> drawableMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintenance);

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
                Toast.makeText(this, "A kosár itt nem elérhető", Toast.LENGTH_LONG).show();
                return true;
            case R.id.logout:
                Toast.makeText(this, "Kilépés", Toast.LENGTH_LONG).show();
                DataProcessor myDataProcessor = DataProcessor.getInstance();
                myDataProcessor.logout();
                startActivity(new Intent(AdminMaintenanceActivity.this, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //******************Action bar********************//

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
            }
        });
    }

    private void initAddButton(){
        AdminDialog alert = new AdminDialog();
        int beforeSize = DataProcessor.getInstance().getProductList(0).size();
        alert.showDialog(this, "Új termék hozzáadása","Hozzáad", null, null);
        //adaptert értesíteni valahogy
        if(beforeSize < DataProcessor.getInstance().getProductList(0).size()){
            adminRecyclerViewAdapter.notifyItemInserted(adminRecyclerViewAdapter.getItemCount());
        }
    }
}