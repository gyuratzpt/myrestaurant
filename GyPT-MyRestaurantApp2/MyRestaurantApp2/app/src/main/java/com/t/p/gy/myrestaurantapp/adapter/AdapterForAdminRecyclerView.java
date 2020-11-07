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
import com.t.p.gy.myrestaurantapp.data.SingleProductItem;
import com.t.p.gy.myrestaurantapp.other.AdminDialog;

import java.util.ArrayList;
import java.util.List;

public class AdapterForAdminRecyclerView extends RecyclerView.Adapter<AdapterForAdminRecyclerView.ViewHolder>{
    DataProcessor myDataProcessor = DataProcessor.getInstance();
    private Context context;
    private List<SingleProductItem> downloadedDataList;


    //egy listaelem elemei
    protected class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView   itemImage;
        private TextView    itemName,
                            itemDescription,
                            itemPrice;
        private Button      modifyButton,
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

    public AdapterForAdminRecyclerView(Context _context){
        context = _context;
    }

    @Override
//létrehozza az egyes sorokat
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_listitem_admin_layout, parent, false);
        Log.v("myLog", "Admin Recyclerview: onCreateViewHolder");
        //v.setBackgroundColor(v.getResources().getColor(R.color.MyCartColor));
        return new ViewHolder(v);
    }

    @Override
//visszaadja hány eleme van a listának
    public int getItemCount() {
        return myDataProcessor.getProductList(0).size();
    }

    @Override
//adatfeltöltés az egyes elemekhez
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SingleProductItem spi = myDataProcessor.getProductList(0).get(holder.getAdapterPosition());
        //holder.itemImage.setImageResource(spi.getImageResourceID());
        holder.itemImage.setImageBitmap(myDataProcessor.getImage("products", spi.getImageName()));
        holder.itemImage.setVisibility(View.VISIBLE);
        holder.itemName.setText(spi.getName());
        holder.itemDescription.setText(spi.getDetail());
        holder.itemPrice.setText(Integer.toString(spi.getPrice()));
        holder.deleteButton.setBackgroundColor(holder.deleteButton.getResources().getColor(R.color.MyWarningColor));
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("myLog", "Deletebutton pressed");
                Log.i("myLog", "Választott termék: "+ spi.getID());
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Biztos törlöd a " + spi.getName() + " tételt az adatbázisból?");
                //.setCancelable(false)
                builder.setPositiveButton("Igen", (dialog, id) -> {
                    myDataProcessor.deleteProductItem(spi.getID());
                    notifyItemRemoved(position);
                });
                builder.setNegativeButton("Mégsem", (dialog, id) -> {
                });
                AlertDialog alert = builder.create();
                alert.setTitle("Vigyázz, törlés művelet!");
                alert.show();
            }
        });
        holder.modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("myLog", "Modifybutton pressed");
                Log.i("myLog", "Választott termék ID-ja: "+ spi.getID());

                AdminDialog alert = new AdminDialog();
                alert.showDialog(context, "Termék módosítása","Módosít", spi.getID(), spi);
                Log.i("myLog", "Admindialog után...");

            }
        });
        //Log.i("myLog","Admin adapterForRecyclerView, konstruktor: OK");
    }

    public void updateItemInList(int _pos, String _name){

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