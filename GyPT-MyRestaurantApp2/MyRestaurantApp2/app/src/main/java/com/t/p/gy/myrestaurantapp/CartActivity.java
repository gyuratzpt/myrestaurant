package com.t.p.gy.myrestaurantapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
        import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
import android.widget.Toast;

import com.t.p.gy.myrestaurantapp.adapter.AdapterForCartRecyclerView;
        import com.t.p.gy.myrestaurantapp.data.DataProcessor;

import static android.support.v7.widget.RecyclerView.VERTICAL;

public class CartActivity extends AppCompatActivity{
    DataProcessor myDataProcessor = DataProcessor.getInstance();
    RecyclerView.Adapter cartRecyclerViewAdapter;

    //UI
    RecyclerView cartRecyclerView;
    TextView tv_price;
    static TextView tv_price_new;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initUI();

        cartRecyclerView = (RecyclerView) findViewById(R.id.cart_layout_recyclerview) ;
        cartRecyclerView.setHasFixedSize(true);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartRecyclerViewAdapter = new AdapterForCartRecyclerView(getApplicationContext(), tv_price);
        DividerItemDecoration decoration = new DividerItemDecoration(this, VERTICAL);
        cartRecyclerView.addItemDecoration(decoration);
        cartRecyclerView.setAdapter(cartRecyclerViewAdapter);
        Log.v("MyLog","CartActivity Konstruktor kész");
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
                Toast.makeText(this, "Ez már a kosár!", Toast.LENGTH_LONG).show();
                return true;
            case R.id.logout:
                myDataProcessor.initLogoutOption(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //******************Action bar********************//

    private void initUI(){
        tv_price = (TextView) findViewById(R.id.cart_layout_price);
        tv_price_new = (TextView) findViewById(R.id.cart_layout_price);
        tv_price.setText(String.valueOf(myDataProcessor.getCartFullPrice())+"Ft");

        Button nextPage = (Button) findViewById(R.id.cart_layout_button);
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myDataProcessor.getCart().size()>0) {
                Intent myIntent = new Intent(CartActivity.this,
                        OrderInfoActivity.class);
                startActivity(myIntent);

                } else Toast.makeText(CartActivity.this, "Üres rendelést nem lehet leadni!", Toast.LENGTH_LONG).show();
            }
        });
    }


    public static void refreshPriceTextView(int x){
        tv_price_new.setText(Integer.toString(x));
    }

}
