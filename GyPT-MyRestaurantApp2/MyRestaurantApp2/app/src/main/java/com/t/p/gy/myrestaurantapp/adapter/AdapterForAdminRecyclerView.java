package com.t.p.gy.myrestaurantapp.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
        private CheckBox    isOnSaleCheckBox;
        private ViewHolder(View itemView){
            super(itemView);
            itemImage = (ImageView) itemView.findViewById(R.id.admin_listitem_imageView) ;
            itemName = (TextView) itemView.findViewById(R.id.admin_listitem_name);
            itemDescription = (TextView) itemView.findViewById(R.id.admin_listitem_detail);
            itemPrice = (TextView) itemView.findViewById(R.id.admin_listitem_price);
            modifyButton = (Button) itemView.findViewById(R.id.admin_listitem_modifybutton);
            deleteButton = (Button) itemView.findViewById(R.id.admin_listitem_deletebutton);
            isOnSaleCheckBox = (CheckBox) itemView.findViewById(R.id.admin_listitem_isonsalecheckbox);
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
        holder.isOnSaleCheckBox.setVisibility(View.GONE);
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
                    myDataProcessor.getProductList(0).remove(spi);
                    myDataProcessor.deleteProductItem(spi.getID());
                    notifyItemRemoved(holder.getLayoutPosition());
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
                notifyItemChanged(holder.getLayoutPosition(), null);
            }
        });
        //Log.i("myLog","Admin adapterForRecyclerView, konstruktor: OK");
    }

    public void updateItemInList(int _pos, String _name){

    }
}

