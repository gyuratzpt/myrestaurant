package com.t.p.gy.myrestaurantapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.t.p.gy.myrestaurantapp.AdminActivity;
import com.t.p.gy.myrestaurantapp.AdminActivity_old;
import com.t.p.gy.myrestaurantapp.R;
import com.t.p.gy.myrestaurantapp.connection.AdminNetworkConnector;
import com.t.p.gy.myrestaurantapp.connection.ProductsBackend;
import com.t.p.gy.myrestaurantapp.connection.RetrofitClient;
import com.t.p.gy.myrestaurantapp.data.SingleMenuItem;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class AdapterForAdminRecyclerView extends RecyclerView.Adapter<AdapterForAdminRecyclerView.ViewHolder>{
    AdminNetworkConnector anc = AdminNetworkConnector.getInstance();;

    //egy listaelem elemei
    protected class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView itemImage;
        private TextView itemName,
            itemDescription,
            itemPrice;
        private Button modifyButton,
            deleteButton;

        private ViewHolder(View itemView){
            super(itemView);
            itemImage = (ImageView) itemView.findViewById(R.id.admin_listitem_imageView) ;
            itemName = (TextView) itemView.findViewById(R.id.admin_listitem_name);
            itemDescription = (TextView) itemView.findViewById(R.id.admin_listitem_detail);
            itemPrice = (TextView) itemView.findViewById(R.id.admin_listitem_price);
            modifyButton = (Button) itemView.findViewById(R.id.admin_listitem_modifybutton);
            deleteButton = (Button) itemView.findViewById(R.id.admin_listitem_deletebutton);
        }
    }

    public AdapterForAdminRecyclerView(){}

    @Override
//létrehozza az egyes sorokat
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_listitem_layout, parent, false);
        Log.v("myLog", "Admin Recyclerview: onCreateViewHolder");
        //v.setBackgroundColor(v.getResources().getColor(R.color.MyCartColor));
        return new ViewHolder(v);
    }

    @Override
//visszaadja hány eleme van a listának
    public int getItemCount() {
        return anc.getDownloadedList().size();
    }

    @Override
//adatfeltöltés az egyes elemekhez
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SingleMenuItem smi = anc.getDownloadedList().get(position);
        /*
        holder.itemImage.setImageResource(smi.getImageResourceID());
        if(smi.hasImage()) {
            // Get the image resource ID from the current AndroidFlavor object and set the image to iconView
            holder.itemImage.setImageResource(smi.getImageResourceID());
            holder.itemImage.setVisibility(View.VISIBLE);
        }
        else {holder.itemImage.setVisibility(View.GONE);}
         */
        //holder.itemImage.setImageResource(R.drawable.cola);
        holder.itemImage.setImageResource(smi.getImageResourceID());
        holder.itemImage.setVisibility(View.VISIBLE);

        holder.itemName.setText(smi.getName());
        holder.itemDescription.setText(smi.getDetail());
        holder.itemPrice.setText(Integer.toString(smi.getPrice()));
        holder.deleteButton.setBackgroundColor(holder.deleteButton.getResources().getColor(R.color.MyWarningColor));
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("myLog", "Deletebutton pressed");
                Log.i("myLog", "Választott termék: "+ smi.getName());
                if (smi.getCategory().equals("drink")) {
                    if (anc.deleteFromDrinksTable(smi.getName())) {
                        Log.i("myLog", "if true lett!");
                    }
                }
                else if (smi.getCategory().equals("food")) {
                    if (anc.deleteFromFoodsTable(smi.getName())) {
                        Log.i("myLog", "if true lett!");
                    }
                }
            }
        });
        Log.i("myLog","Admin adapterForRecyclerView, konstruktor: OK");
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