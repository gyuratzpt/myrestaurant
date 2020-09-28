package com.t.p.gy.myrestaurantapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.t.p.gy.myrestaurantapp.connection.NetworkConnector;
import com.t.p.gy.myrestaurantapp.connection.ProductsBackend;
import com.t.p.gy.myrestaurantapp.connection.RetrofitClient;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;

public class AdminOrdersActivity extends AppCompatActivity {
    NetworkConnector anc = new NetworkConnector();
    ProductsBackend myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    TextView tempTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders);
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(ProductsBackend.class);

        tempTV = (TextView) findViewById(R.id.admin_orders_temptv);
        String actualOrders = anc.downloadOrders();
        tempTV.setText(actualOrders);
    }


}