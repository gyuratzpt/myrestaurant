package com.t.p.gy.myrestaurantapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.t.p.gy.myrestaurantapp.R;
import com.t.p.gy.myrestaurantapp.data.DataProcessor;
import com.t.p.gy.myrestaurantapp.data.SingleProductItem;


public class AdapterForCartRecyclerView extends RecyclerView.Adapter<AdapterForCartRecyclerView.ViewHolder>{
    DataProcessor myDataProcessor = DataProcessor.getInstance();
    private Context context;
    private TextView textView;

    protected class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView itemImage;
        private TextView itemName,
            itemDescription,
            itemAmountDecrease,
            itemAmount,
            itemAmountIncrease,
            itemPrice,
            itemDelete;


        private ViewHolder(View itemView){
            super(itemView);
            itemImage = (ImageView) itemView.findViewById(R.id.recyclerview_item_layout_v3_image) ;
            itemName = (TextView) itemView.findViewById(R.id.recyclerview_item_layout_v3_name);
            itemDescription = (TextView) itemView.findViewById(R.id.recyclerview_item_layout_v3_description);
            itemAmountDecrease = (TextView) itemView.findViewById(R.id.recyclerview_item_layout_v3_dec);
            itemAmount = (TextView) itemView.findViewById(R.id.recyclerview_item_layout_v3_amount);
            itemAmountIncrease = (TextView) itemView.findViewById(R.id.recyclerview_item_layout_v3_inc);
            itemPrice = (TextView) itemView.findViewById(R.id.recyclerview_item_layout_v3_price);
            itemDelete = (TextView) itemView.findViewById(R.id.recyclerview_item_layout_v3_delete);
        }
    }

    public AdapterForCartRecyclerView(Context _context, TextView _tv){
        context = _context;
        textView = _tv;
    }


    @Override
//létrehozza az egyes elemeket
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_listitem_cart_layout, parent, false);
        Log.v("myLog", "AdapterForRecyclerView4: OK");
        v.setBackgroundColor(v.getResources().getColor(R.color.MyCartColor));

        // törlés swipe-ra?!

        return new ViewHolder(v);
    }


    @Override
//adatfeltöltés az egyes elemekhez
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SingleProductItem spi = myDataProcessor.getCart().get(position);
/*
        if(spi.hasImage()) {
            holder.itemImage.setImageResource(spi.getImageResourceID());
        }
        else {holder.itemImage.setImageResource(Integer.parseInt(DataProcessor.getDrawableMap().get("noimage").toString()));}
*/
        holder.itemImage.setImageBitmap(myDataProcessor.getImage("products", spi.getImageName()));
        holder.itemImage.setVisibility(View.VISIBLE);
        holder.itemName.setText(spi.getName());
        holder.itemDescription.setText(spi.getDetail());
        holder.itemAmount.setText(Integer.toString(spi.getCartAmount()));
        holder.itemPrice.setText(Integer.toString(spi.getPrice()*spi.getCartAmount()));
        holder.itemDelete.setBackgroundColor(holder.itemDelete.getResources().getColor(R.color.MyWarningColor));


        holder.itemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDataProcessor.deleteItemFromCart(spi, textView);
                notifyDataSetChanged();
                Toast.makeText(view.getContext(),"Tétel törölve",Toast.LENGTH_LONG).show();
            }
        });

        holder.itemAmountDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spi.getCartAmount()>1){
                    spi.setCartAmount(spi.getCartAmount()-1);
                    //myCart.refreshCartFinalPrice(textView);
                    myDataProcessor.refreshCartActivityView();
                    notifyDataSetChanged();
                }
                else Toast.makeText(view.getContext(),"Üres tétel nem lehet a kosárban, használd a törlés gombot!",Toast.LENGTH_LONG).show();
            }
        });
        holder.itemAmountIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spi.setCartAmount(spi.getCartAmount()+1);
                myDataProcessor.refreshCartFinalPrice(textView);
                notifyDataSetChanged();
            }
        });



        Log.v("MyLog","AdapterForRecyclerView5 OK");
    }

    @Override
    //visszaadja hány eleme van a listának
    public int getItemCount() {
        return myDataProcessor.getCart().size();
    }


}