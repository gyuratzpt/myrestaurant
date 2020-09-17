package com.t.p.gy.myrestaurantapp.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import com.t.p.gy.myrestaurantapp.FoodActivity;
import com.t.p.gy.myrestaurantapp.R;
import com.t.p.gy.myrestaurantapp.data.SingleMenuItem;

public class ItemAdapterForFoodActivity extends ArrayAdapter<SingleMenuItem> {
    private int mColorResourceID; //hatter szine

    public ItemAdapterForFoodActivity(Activity context, ArrayList<SingleMenuItem> inputList, int colorResourceID) {
        super(context, 0, inputList);
        mColorResourceID = colorResourceID;
        Log.i("myLog", "Foods, ItemAdapterForFoodActivity konstruktor");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.food_listitem_layout_v2, parent, false);
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
        minusButton.setTextSize(24);
        amountTextView.setText(String.valueOf(currentSingleMenuItem.getOrderAmount()));
        plusButton.setText("+");
        plusButton.setTextSize(24);

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

        View textContainer = listItemView.findViewById(R.id.food_menu_list_item_layout_v2);
        int color = ContextCompat.getColor(getContext(), mColorResourceID);
        textContainer.setBackgroundColor(color);

        return listItemView;
    }

    public void refreshlistview(){
        notifyDataSetChanged();
    }
}