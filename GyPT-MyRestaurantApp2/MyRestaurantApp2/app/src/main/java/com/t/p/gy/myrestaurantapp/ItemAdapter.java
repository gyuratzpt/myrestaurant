package com.t.p.gy.myrestaurantapp;

        import android.app.Activity;
        import android.content.DialogInterface;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.AlertDialog;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.Collection;

public class ItemAdapter extends ArrayAdapter<SingleMenuItem> {
    private int mColorResourceID; //hatter szine
    DataProcessor dp = new DataProcessor();

    public ItemAdapter(Activity context, ArrayList<SingleMenuItem> inputList, int colorResourceID) {
        super(context, 0, inputList);
        mColorResourceID = colorResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.drink_menu_list_item_layout, parent, false);
        }

        final SingleMenuItem currentSingleMenuItem = getItem(position);

        //elemek deklarálása
        ImageView iconView = (ImageView) listItemView.findViewById(R.id.itemImage);
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.item_name);
        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.item_detail);
        TextView priceTextView = (TextView) listItemView.findViewById(R.id.item_price);
        final TextView amountTextView = (TextView) listItemView.findViewById(R.id.item_amount);
        TextView plusButton = (TextView) listItemView.findViewById(R.id.item_amount_inc);
        TextView minusButton = (TextView) listItemView.findViewById(R.id.item_amount_dec);
        TextView addItemToCartTextView = (TextView) listItemView.findViewById(R.id.add_item_to_cart);

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
                notifyDataSetChanged();
            }
        });


        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    currentSingleMenuItem.setOrderAmount(false);
                    notifyDataSetChanged();
            }
        });

        addItemToCartTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                                                                                                                                            Log.i("mylogline", "currentsinglemenuitem order: "+currentSingleMenuItem.getOrderAmount()+", cart: "+currentSingleMenuItem.getCartAmount());
                if (currentSingleMenuItem.getOrderAmount() == 0){
                    Toast.makeText(view.getContext(), "Üres tételt nem lehet a kosárba tenni!", Toast.LENGTH_LONG).show();}
                else{
                    if (currentSingleMenuItem.getCartAmount()==0){
                        currentSingleMenuItem.setCartAmount(currentSingleMenuItem.getOrderAmount());
                        dp.addToCart(currentSingleMenuItem.getID());
                        Toast.makeText(view.getContext(), "A tétel a kosárba került!", Toast.LENGTH_LONG).show();
                        currentSingleMenuItem.resetOrderAmount();
                        notifyDataSetChanged();
                    }
                    else{
                        //Toast.makeText(view.getContext(), "Már van ilyen tétel a kosárban, ott tudod módosítani a mennyiséget!", Toast.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Már van " + currentSingleMenuItem.getCartAmount()+"db a kosárban ebből a tételből!" );
                                //.setCancelable(false)
                        builder.setPositiveButton("Felülír", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dp.modifyCartItem(currentSingleMenuItem.getID(), currentSingleMenuItem.getOrderAmount());
                                        Toast.makeText(getContext(),"Tétel felülírva!", Toast.LENGTH_SHORT).show();
                                        currentSingleMenuItem.resetOrderAmount();
                                        notifyDataSetChanged();
                                    }
                                });

                        builder.setNegativeButton("Hozzáad", new DialogInterface.OnClickListener() {//bal oldali
                                    public void onClick(DialogInterface dialog, int id) {
                                        dp.modifyCartItem(currentSingleMenuItem.getID(), currentSingleMenuItem.getOrderAmount()+currentSingleMenuItem.getCartAmount());
                                        Toast.makeText(getContext(),"Tétel hozzáadva", Toast.LENGTH_SHORT).show();
                                        currentSingleMenuItem.resetOrderAmount();
                                        notifyDataSetChanged();
                                    }
                                });







                        //Creating dialog box
                        AlertDialog alert = builder.create();
                        //Setting the title manually
                        alert.setTitle(currentSingleMenuItem.getName());
                        alert.show();
                    }
                }
            }
        });

        View textContainer = listItemView.findViewById(R.id.drink_menu_list_item_layout);
        int color = ContextCompat.getColor(getContext(), mColorResourceID);
        textContainer.setBackgroundColor(color);

        return listItemView;
    }
}
