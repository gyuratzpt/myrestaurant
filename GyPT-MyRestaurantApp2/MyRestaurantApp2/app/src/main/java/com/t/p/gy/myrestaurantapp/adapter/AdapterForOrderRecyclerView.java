package com.t.p.gy.myrestaurantapp.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.t.p.gy.myrestaurantapp.R;
import com.t.p.gy.myrestaurantapp.connection.NetworkConnector;
import com.t.p.gy.myrestaurantapp.data.DataProcessor;
import com.t.p.gy.myrestaurantapp.data.Order;
import com.t.p.gy.myrestaurantapp.data.SingleProductItem;
import com.t.p.gy.myrestaurantapp.other.AdminDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterForOrderRecyclerView extends RecyclerView.Adapter<AdapterForOrderRecyclerView.ViewHolder> {
    DataProcessor myDp = DataProcessor.getInstance();
    private Context context;
    private List<Order> ordersList;


    //egy listaelem elemei
    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView orderID,
                orderTime,
                customerName,
                customerAddress,
                customerPhoneNumber,
                itemList,
                note;
        private Button sentButton;


        private ViewHolder(View itemView) {
            super(itemView);
            orderID = (TextView) itemView.findViewById(R.id.orders_listitem_idnumber);
            orderTime = (TextView) itemView.findViewById(R.id.orders_listitem_time);
            customerName = (TextView) itemView.findViewById(R.id.orders_listitem_name);
            customerAddress = (TextView) itemView.findViewById(R.id.orders_listitem_address);
            customerPhoneNumber = (TextView) itemView.findViewById(R.id.orders_listitem_phonenumber);
            itemList = (TextView) itemView.findViewById(R.id.orders_listitem_items);
            note = (TextView) itemView.findViewById(R.id.orders_listitem_other);
            sentButton = (Button) itemView.findViewById(R.id.orders_listitem_sentbutton);
        }
    }

    public AdapterForOrderRecyclerView(Context _context) {
        context = _context;

        if (ordersList != null) Log.i("myLog", "Order adapter listája get előtt: " + ordersList.toString());
        ordersList = myDp.getOrders_list();
        Log.i("myLog", "Order adapter listája get után: " + ordersList.toString());
    }

    @Override
//létrehozza az egyes sorokat
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_listitem_order_layout, parent, false);
        Log.v("myLog", "Orders Recyclerview: onCreateViewHolder");
        //v.setBackgroundColor(v.getResources().getColor(R.color.MyCartColor));
        return new ViewHolder(v);
    }

    @Override
//visszaadja hány eleme van a listának
    public int getItemCount() {
        return ordersList.size();
    }

    @Override
//adatfeltöltés az egyes elemekhez
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Log.i("myLog", "Order adapter listája onBindViewHolder-ben: " + ordersList.toString());
        final Order o = ordersList.get(position);
        holder.orderID.setText(Integer.toString(o.getOrderID()));
        holder.orderTime.setText(o.getOrderTime());
        holder.customerName.setText(o.getCustomerName());
        holder.customerAddress.setText(o.getCustomerAddress());
        holder.customerPhoneNumber.setText(o.getCustomerPhoneNumber());
        holder.itemList.setText(o.toString());
        holder.note.setText(o.getOrderNote());


        holder.sentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("myLog", "SentButton pressed");

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setPositiveButton("Igen", (dialog, id) -> {
                    myDp.setOrderToCompleted(o.getOrderID());
                    Log.i("myLog", "Törölt elem: " + ordersList.get(holder.getLayoutPosition()).toString());
                    ordersList.remove(holder.getLayoutPosition());
                    notifyItemRemoved(holder.getLayoutPosition());
                });
                builder.setNegativeButton("Mégsem", (dialog, id) -> {
                });
                AlertDialog alert = builder.create();
                alert.setTitle("Biztos lezárod ezt a rendelést?");
                alert.show();
            }
        });

    }
}












































/* v2

package com.t.p.gy.myrestaurantapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdapterForRecyclerView extends RecyclerView.Adapter<AdapterForRecyclerView.ViewHolder>{
    DataProcessor dp = new DataProcessor();


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView recyclerviewItemName,
                recyclerviewItemAmountDecrease,
                recyclerviewItemAmount,
                recyclerviewItemAmountIncrease,
                recyclerviewItemPrice,
                recyclerviewDeleteButton;PP


        public ViewHolder(View itemView){
            super(itemView);
            recyclerviewItemName = (TextView) itemView.findViewById(R.id.recyclerview_v2_item_name);
            recyclerviewItemAmountDecrease = (TextView) itemView.findViewById(R.id.recyclerview_v2_item_amount_decrease);
            recyclerviewItemAmount = (TextView) itemView.findViewById(R.id.recyclerview_v2_item_amount);
            recyclerviewItemAmountIncrease = (TextView) itemView.findViewById(R.id.recyclerview_v2_item_amount_increase);
            recyclerviewItemPrice = (TextView) itemView.findViewById(R.id.recyclerview_v2_item_price);
            recyclerviewDeleteButton = (TextView) itemView.findViewById(R.id.recyclerview_v2_item_delete);
        }
    }

    public AdapterForRecyclerView(){}

    @Override
    //létrehozza az egyes sorokat
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_layout_v2, parent, false);
        Log.v("AdapterForRecyclerView4", "OK");

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Megnyomtál!",Toast.LENGTH_LONG).show();
            }
        });;
        return new ViewHolder(v);
    }

    @Override
    //adatfeltöltés az egyes elemekhez
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SingleMenuItem smi = dp.getCart().get(position);

        holder.recyclerviewItemName.setText(smi.getName());

        holder.recyclerviewItemAmountDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(smi.getCartAmount()>1){
                    smi.setCartAmount(smi.getCartAmount()-1);
                    CartActivity.refreshCartFinalPrice();
                    notifyDataSetChanged();
                }
                else Toast.makeText(view.getContext(),"Üres tétel nem lehet a kosárban, használd a törlés gombot!",Toast.LENGTH_LONG).show();
            }
        });

        holder.recyclerviewItemAmount.setText(Integer.toString(smi.getCartAmount()));

        holder.recyclerviewItemAmountIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    smi.setCartAmount(smi.getCartAmount()+1);
                    CartActivity.refreshCartFinalPrice();
                    notifyDataSetChanged();
            }
        });

        holder.recyclerviewItemPrice.setText(Integer.toString(smi.getPrice()*smi.getCartAmount()));

        holder.recyclerviewDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dp.deleteFromCart(smi.getID());
                CartActivity.refreshCartFinalPrice();
                notifyDataSetChanged();
                Toast.makeText(view.getContext(),"Tétel törölve",Toast.LENGTH_LONG).show();
            }
        });

        Log.v("AdapterForRecyclerView5", "OK");
    }

    @Override
    //visszaadja hány eleme van a listának
    public int getItemCount() {
        return dp.getCart().size();
    }

}

 */