package com.t.p.gy.myrestaurantapp.other;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.t.p.gy.myrestaurantapp.R;
import com.t.p.gy.myrestaurantapp.connection.NetworkConnector;

public class AdminDialog {
    public void showDialog(Activity activity, String _dialogTitle, String _buttonTitle){
        final Dialog adminDialog = new Dialog(activity);
        adminDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        adminDialog.setCancelable(false);
        adminDialog.setContentView(R.layout.dialog_additem);
        TextView tvTitle = (TextView)  adminDialog.findViewById(R.id.dialog_additem_title);
        tvTitle.setText(_dialogTitle);

        EditText etCategory = (EditText) adminDialog.findViewById(R.id.dialog_additem_category);
        EditText etName = (EditText) adminDialog.findViewById(R.id.dialog_additem_name);
        EditText etDescription = (EditText) adminDialog.findViewById(R.id.dialog_additem_description);
        EditText etPrice = (EditText) adminDialog.findViewById(R.id.dialog_additem_price);
        EditText etImage = (EditText) adminDialog.findViewById(R.id.dialog_additem_image);

        Button addButton = (Button) adminDialog.findViewById(R.id.dialog_additem_addbutton);
        Button cancelButton = (Button) adminDialog.findViewById(R.id.dialog_additem_cancelbutton);
        addButton.setText(_buttonTitle);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkConnector anc = new NetworkConnector();
                anc.createNewItem(
                        Integer.parseInt(etCategory.getText().toString()),
                        etName.getText().toString(),
                        etDescription.getText().toString(),
                        Integer.parseInt(etPrice.getText().toString()),
                        etImage.getText().toString()
                );
                adminDialog.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminDialog.dismiss();
            }
        });

        adminDialog.show();

    }
}
