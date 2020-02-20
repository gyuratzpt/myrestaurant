package com.t.p.gy.myrestaurantapp;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ItemAdapter_v2 extends ArrayAdapter<SingleMenuItem> {
    private int mColorResourceID; //hatter szine
    DataProcessor dp = new DataProcessor();

    public ItemAdapter_v2(Activity context, ArrayList<SingleMenuItem> inputList, int colorResourceID) {
        super(context, 0, inputList);
        mColorResourceID = colorResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.food_menu_list_item_layout_v2, parent, false);
        }

        final SingleMenuItem currentSingleMenuItem = getItem(position);

        //elemek deklarálása
        ImageView iconView = (ImageView) listItemView.findViewById(R.id.listview_item_layout_image);
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.listview_item_layout_name);
        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.listview_item_layout_description);
        TextView priceTextView = (TextView) listItemView.findViewById(R.id.listview_item_layout_price);
        final TextView amountTextView = (TextView) listItemView.findViewById(R.id.listview_item_layout_amount);
        TextView plusButton = (TextView) listItemView.findViewById(R.id.listview_item_layout_inc);
        TextView minusButton = (TextView) listItemView.findViewById(R.id.listview_item_layout_dec);

        if (currentSingleMenuItem.hasImage()) {
            // Get the image resource ID from the current AndroidFlavor object and set the image to iconView
            iconView.setImageResource(currentSingleMenuItem.getImageResourceID());
            iconView.setVisibility(View.VISIBLE);
        } else {
            iconView.setVisibility(View.GONE);
        }
        nameTextView.setText(currentSingleMenuItem.getName());
        descriptionTextView.setText(currentSingleMenuItem.getDetail());
        priceTextView.setText(String.valueOf(currentSingleMenuItem.getPrice())+"Ft");
        minusButton.setText("-");
        amountTextView.setText(String.valueOf(currentSingleMenuItem.getOrderAmount()));
        plusButton.setText("+");

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentSingleMenuItem.setOrderAmount(true);
                amountTextView.setText(String.valueOf(currentSingleMenuItem.getOrderAmount()));
                FoodActivity.refreshSummedPrice();
            }
        });


        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentSingleMenuItem.setOrderAmount(false);
                amountTextView.setText(String.valueOf(currentSingleMenuItem.getOrderAmount()));
                FoodActivity.refreshSummedPrice();
            }
        });
/*
        addItemToCartTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (currentSingleMenuItem.getCartAmount() > 0) Toast.makeText(view.getContext(),"Már van ilyen tétel a kosárban!", Toast.LENGTH_LONG).show();
                //else
                if ((Integer.valueOf(amountTextView.getText().toString())) == 0)
                    Toast.makeText(view.getContext(), "0 darabot nem lehet a kosárba tenni", Toast.LENGTH_LONG).show();
                else {

                rákérdezés hozzáadásra/felülirásra
                    if(currentSingleMenuItem.getCartAmount()==0) addItemToCartTextView.setText("Kosárba tesz");
                    else addItemToCartTextView.setText("Módosít");

                    felugró ablakban jelenleg mennyi van
                    ehhez hozzáadni, vagy helyette
                    törlés

                    if (dp.addToCart(currentSingleMenuItem.getID())) {
                        currentSingleMenuItem.setCartAmount(currentSingleMenuItem.getOrderAmount());
                        amountTextView.setText(String.valueOf(currentSingleMenuItem.getOrderAmount()));
                        Toast.makeText(view.getContext(), "A tétel a kosárba került!", Toast.LENGTH_LONG).show();
                    } else{

                        Toast.makeText(view.getContext(), "Már van ilyen tétel a kosárban!", Toast.LENGTH_LONG).show();
                    }
                    currentSingleMenuItem.resetOrderAmount();
                }
            }
        });
*/


        View textContainer = listItemView.findViewById(R.id.food_menu_list_item_layout_v2);
        int color = ContextCompat.getColor(getContext(), mColorResourceID);
        textContainer.setBackgroundColor(color);

        return listItemView;
    }

    public void refreshlistview(){
        notifyDataSetChanged();
    }
}