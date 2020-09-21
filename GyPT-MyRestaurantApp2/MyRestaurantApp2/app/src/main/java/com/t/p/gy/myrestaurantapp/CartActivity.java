package com.t.p.gy.myrestaurantapp;


import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.ActionBar;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

        import com.t.p.gy.myrestaurantapp.adapter.AdapterForCartRecyclerView;
        import com.t.p.gy.myrestaurantapp.data.Cart;

public class CartActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private TextView tv_price;
    static TextView tv_price_new;
    Cart myCart = Cart.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initBackButton();

        Button nextPage = (Button) findViewById(R.id.cart_layout_button);
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(CartActivity.this,
                        OrderInfoActivity.class);
                startActivity(myIntent);
            }
        });

        tv_price = (TextView) findViewById(R.id.cart_layout_price);
        tv_price_new = (TextView) findViewById(R.id.cart_layout_price);
        tv_price.setText(String.valueOf(myCart.getCartFullPrice())+"Ft");

        recyclerView = (RecyclerView) findViewById(R.id.cart_layout_recyclerview) ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new AdapterForCartRecyclerView(tv_price);
        recyclerView.setAdapter(recyclerViewAdapter);
        Log.v("MyLog","CartActivity Konstruktor kész");
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

    public static void refreshPriceTextView(int x){
        tv_price_new.setText(Integer.toString(x));
    }

}
