package com.t.p.gy.myrestaurantapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.t.p.gy.myrestaurantapp.adapter.AdapterForOrderRecyclerView;
import com.t.p.gy.myrestaurantapp.connection.BackendAPI;
import com.t.p.gy.myrestaurantapp.connection.NetworkConnector;
import com.t.p.gy.myrestaurantapp.connection.RetrofitClient;
import com.t.p.gy.myrestaurantapp.data.DataProcessor;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;

import static android.support.v7.widget.RecyclerView.VERTICAL;

public class AdminOrdersActivity extends AppCompatActivity {
    RecyclerView.Adapter orderRecyclerViewAdapter;

    //UI
    RecyclerView orderRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders);

        orderRecyclerView = (RecyclerView) findViewById(R.id.admin_orders_recyclerview) ;
        orderRecyclerView.setHasFixedSize(true);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderRecyclerViewAdapter = new AdapterForOrderRecyclerView(this);
        orderRecyclerView.setAdapter(orderRecyclerViewAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(this, VERTICAL);
        orderRecyclerView.addItemDecoration(decoration);
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
                DataProcessor.getInstance().initLogoutOption(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //******************Action bar********************//
}