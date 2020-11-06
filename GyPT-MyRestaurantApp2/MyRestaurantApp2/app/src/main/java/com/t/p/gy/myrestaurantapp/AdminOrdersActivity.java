package com.t.p.gy.myrestaurantapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.t.p.gy.myrestaurantapp.adapter.AdapterForOrderRecyclerView;
import com.t.p.gy.myrestaurantapp.connection.BackendAPI;
import com.t.p.gy.myrestaurantapp.connection.NetworkConnector;
import com.t.p.gy.myrestaurantapp.connection.RetrofitClient;
import com.t.p.gy.myrestaurantapp.data.DataProcessor;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;

import static android.support.v7.widget.RecyclerView.VERTICAL;

public class AdminOrdersActivity extends AppCompatActivity {
    NetworkConnector anc = NetworkConnector.getInstance();
    DataProcessor dp = DataProcessor.getInstance();
    BackendAPI myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView orderRecyclerView;
    private RecyclerView.Adapter orderRecyclerViewAdapter;
    TextView tempTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders);
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(BackendAPI.class);

        tempTV = (TextView) findViewById(R.id.admin_orders_temptv);
        //dp.getOrders(tempTV);
        tempTV.setVisibility(View.GONE);

        orderRecyclerView = (RecyclerView) findViewById(R.id.admin_orders_recyclerview) ;
        orderRecyclerView.setHasFixedSize(true);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderRecyclerViewAdapter = new AdapterForOrderRecyclerView(this);
        orderRecyclerView.setAdapter(orderRecyclerViewAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(this, VERTICAL);
        orderRecyclerView.addItemDecoration(decoration);
    }
}