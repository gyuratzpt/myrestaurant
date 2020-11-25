package com.t.p.gy.myrestaurantapp.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.t.p.gy.myrestaurantapp.R;
import com.t.p.gy.myrestaurantapp.data.DataProcessor;
import com.t.p.gy.myrestaurantapp.data.Order;

import java.util.List;

public class AdapterForOrderRecyclerView extends RecyclerView.Adapter<AdapterForOrderRecyclerView.ViewHolder> {
    DataProcessor myDataProcessor = DataProcessor.getInstance();
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
        ordersList = myDataProcessor.getOrdersList();
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
                    myDataProcessor.setOrderToCompleted(o.getOrderID());
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