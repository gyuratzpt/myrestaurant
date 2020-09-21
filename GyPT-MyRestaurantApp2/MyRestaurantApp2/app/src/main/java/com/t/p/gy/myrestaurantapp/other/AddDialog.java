package com.t.p.gy.myrestaurantapp.other;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.t.p.gy.myrestaurantapp.R;
import com.t.p.gy.myrestaurantapp.adapter.AdapterForAdminRecyclerView;
import com.t.p.gy.myrestaurantapp.connection.AdminNetworkConnector;

public class AddDialog {
    public void showDialog(Activity activity){
        final Dialog addDialog = new Dialog(activity);
        addDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addDialog.setCancelable(false);
        addDialog.setContentView(R.layout.dialog_additem);

        EditText etName = (EditText) addDialog.findViewById(R.id.dialog_additem_name);
        EditText etDescription = (EditText) addDialog.findViewById(R.id.dialog_additem_description);
        EditText etPrice = (EditText) addDialog.findViewById(R.id.dialog_additem_price);
        EditText etImage = (EditText) addDialog.findViewById(R.id.dialog_additem_image);

        Button addButton = (Button) addDialog.findViewById(R.id.dialog_additem_addbutton);
        Button cancelButton = (Button) addDialog.findViewById(R.id.dialog_additem_cancelbutton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminNetworkConnector anc = new AdminNetworkConnector();
                anc.createDrinkItem(etName.getText().toString(),
                        etDescription.getText().toString(),
                        Integer.parseInt(etPrice.getText().toString()),
                        etImage.getText().toString()
                );
                addDialog.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog.dismiss();
            }
        });

        addDialog.show();

    }
}
