package com.t.p.gy.myrestaurantapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.t.p.gy.myrestaurantapp.MenuActivity;
import com.t.p.gy.myrestaurantapp.R;
import com.t.p.gy.myrestaurantapp.data.DataProcessor;
import com.t.p.gy.myrestaurantapp.data.SingleProductItem;


public class AdapterForMenuRecyclerView extends RecyclerView.Adapter<AdapterForMenuRecyclerView.ViewHolder>{
    DataProcessor myDataProcessor = DataProcessor.getInstance();
    private TextView textView;

    protected class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView itemImage;
        private TextView itemName,
            itemDescription,
            itemAmountDecrease,
            itemAmount,
            itemAmountIncrease,
            itemPrice;

        private ViewHolder(View itemView){
            super(itemView);
            itemImage = (ImageView) itemView.findViewById(R.id.menu_listitem_image) ;
            itemName = (TextView) itemView.findViewById(R.id.menu_listitem_name);
            itemDescription = (TextView) itemView.findViewById(R.id.menu_listitem_description);
            itemAmountDecrease = (TextView) itemView.findViewById(R.id.menu_listitem_dec);
            itemAmount = (TextView) itemView.findViewById(R.id.menu_listitem_amount);
            itemAmountIncrease = (TextView) itemView.findViewById(R.id.menu_listitem_inc);
            itemPrice = (TextView) itemView.findViewById(R.id.menu_listitem_price);
        }
    }

    public AdapterForMenuRecyclerView(TextView _tv){
        textView = _tv;
    }

    @Override
//létrehozza az egyes sorokat
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_menu_layout, parent, false);
        Log.v("myLog", "Menu recyclerview onCreateViewHolder: OK");
        v.setBackgroundColor(v.getResources().getColor(R.color.MyCartColor));
        // törlés swipe-ra?!
        return new ViewHolder(v);
    }

    @Override
//adatfeltöltés az egyes elemekhez
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SingleProductItem spi = myDataProcessor.getProductList().get(position);

        holder.itemImage.setImageResource(spi.getImageResourceID());
        if(spi.hasImage()) {
            // Get the image resource ID from the current AndroidFlavor object and set the image to iconView
            holder.itemImage.setImageResource(spi.getImageResourceID());
            holder.itemImage.setVisibility(View.VISIBLE);
        }
        else {holder.itemImage.setVisibility(View.GONE);}

        holder.itemName.setText(spi.getName());
        holder.itemDescription.setText(spi.getDetail());
        holder.itemAmount.setText(Integer.toString(spi.getCartAmount()));
        holder.itemPrice.setText(Integer.toString(spi.getPrice()));
        holder.itemAmountDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spi.getOrderAmount() > 0) {
                    spi.setOrderAmount(false);
                    holder.itemAmount.setText(String.valueOf(spi.getOrderAmount()));
                    MenuActivity.refreshPriceTextView(myDataProcessor.getActualOrderPrice());
                }
            }
        });
        holder.itemAmountIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spi.setOrderAmount(true);
                holder.itemAmount.setText(String.valueOf(spi.getOrderAmount()));
                MenuActivity.refreshPriceTextView(myDataProcessor.getActualOrderPrice());
            }
        });

        Log.v("myLog","Menu recyclerview onBindViewHolder OK, position: " + position);
    }

    @Override
    //visszaadja hány eleme van a listának
    public int getItemCount() {
        return myDataProcessor.getProductList().size();
    }

}