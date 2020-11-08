package com.t.p.gy.myrestaurantapp.other;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.t.p.gy.myrestaurantapp.R;
import com.t.p.gy.myrestaurantapp.adapter.AdapterForAdminRecyclerView;
import com.t.p.gy.myrestaurantapp.connection.NetworkConnector;
import com.t.p.gy.myrestaurantapp.data.DataProcessor;
import com.t.p.gy.myrestaurantapp.data.SingleProductItem;

public class AdminDialog {
    DataProcessor dp = DataProcessor.getInstance();

    public void showDialog_old(Activity activity, String _dialogTitle, String _buttonTitle) {
        final Dialog adminDialog = new Dialog(activity);
        adminDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        adminDialog.setCancelable(false);
        adminDialog.setContentView(R.layout.dialog_additem);
        TextView tvTitle = (TextView) adminDialog.findViewById(R.id.dialog_additem_title);
        tvTitle.setText(_dialogTitle);

        EditText etCategory = (EditText) adminDialog.findViewById(R.id.dialog_additem_category);
        EditText etName = (EditText) adminDialog.findViewById(R.id.dialog_additem_name);
        EditText etDescription = (EditText) adminDialog.findViewById(R.id.dialog_additem_description);
        EditText etPrice = (EditText) adminDialog.findViewById(R.id.dialog_additem_price);
        EditText etImage = (EditText) adminDialog.findViewById(R.id.dialog_additem_image);

        Button addButton = (Button) adminDialog.findViewById(R.id.dialog_additem_okbutton);
        Button cancelButton = (Button) adminDialog.findViewById(R.id.dialog_additem_cancelbutton);
        addButton.setText(_buttonTitle);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkConnector anc = NetworkConnector.getInstance();
                anc.createNewItem(
                        Integer.parseInt(etCategory.getText().toString()),
                        etName.getText().toString(),
                        etDescription.getText().toString(),
                        Integer.parseInt(etPrice.getText().toString()),
                        (etImage.getText().toString().length() > 0) ? "noimage" : etImage.getText().toString()
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


    public void showDialog(Context _context, String _dialogTitle, String _buttonTitle, Integer dbId, SingleProductItem spi) {
        final Dialog adminDialog = new Dialog(_context);
        adminDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        adminDialog.setCancelable(false);
        adminDialog.setContentView(R.layout.dialog_additem);
        TextView tvTitle = (TextView) adminDialog.findViewById(R.id.dialog_additem_title);
        tvTitle.setText(_dialogTitle);

        EditText etCategory = (EditText) adminDialog.findViewById(R.id.dialog_additem_category);
        EditText etName = (EditText) adminDialog.findViewById(R.id.dialog_additem_name);
        EditText etDescription = (EditText) adminDialog.findViewById(R.id.dialog_additem_description);
        EditText etPrice = (EditText) adminDialog.findViewById(R.id.dialog_additem_price);
        EditText etImage = (EditText) adminDialog.findViewById(R.id.dialog_additem_image);

        Button okButton = (Button) adminDialog.findViewById(R.id.dialog_additem_okbutton);
        Button cancelButton = (Button) adminDialog.findViewById(R.id.dialog_additem_cancelbutton);
        okButton.setText(_buttonTitle);
        if (_buttonTitle.equals("Módosít")) {
            Log.i("myLog", "Tétel id-ja: " + dbId);
            dp.getItemFromDatabase(dbId, etCategory, etName, etDescription, etPrice, etImage);
        }

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (_buttonTitle) {
                    case "Hozzáad":
                    /*
                        Log.i("myLog", "AdminDialog, Új termék...");
                        dp.addItemToDatabase(
                                Integer.parseInt(etCategory.getText().toString()),
                                etName.getText().toString(),
                                etDescription.getText().toString(),
                                Integer.parseInt(etPrice.getText().toString()),
                                (etImage.getText().toString().length() > 0) ? etImage.getText().toString() : null);
                        break;
                     */
                    Log.i("myLog", "Lista mérete hozzáadás előtt: " + dp.getProductList(0).size());
                    dp.addItemToList(
                            new SingleProductItem(
                            null,
                            Integer.parseInt(etCategory.getText().toString()),
                            etName.getText().toString(),
                            etDescription.getText().toString(),
                            Integer.parseInt(etPrice.getText().toString()),
                            etImage.getText().toString()
                            )
                    );
                    dp.addItemToDatabase(
                            Integer.parseInt(etCategory.getText().toString()),
                            etName.getText().toString(),
                            etDescription.getText().toString(),
                            Integer.parseInt(etPrice.getText().toString()),
                            (etImage.getText().toString().length() > 0) ? etImage.getText().toString() : null
                    );


                    Log.i("myLog", "Lista mérete hozzáadás után: " + dp.getProductList(0).size());

                    break;



                    case "Módosít":
                        Log.i("myLog", "AdminDialog, módosítás...");
                        Log.i("myLog", "AdminDialog, módosítás előtt categoy: " + spi.getCategory());
                        Log.i("myLog", "AdminDialog, input categoy: " + etCategory.getText().toString());
                        if(spi.getCategory() != Integer.parseInt(etCategory.getText().toString())){
                            spi.setCategory(Integer.parseInt(etCategory.getText().toString()));
                        }
                        Log.i("myLog", "AdminDialog, módosítás utáni categoy: " + spi.getCategory());



                        if(!spi.getName().equals(etName.getText().toString())){
                            spi.setName(etName.getText().toString());
                        }
                        if(!spi.getDetail().equals(etDescription.getText().toString())){
                            spi.setDetail(etDescription.getText().toString());
                        }
                        if(spi.getPrice() != Integer.parseInt(etPrice.getText().toString())) {
                            spi.setPrice(Integer.parseInt(etPrice.getText().toString()));
                        }
                        if(!spi.getImageName().equals(etImage.getText().toString())){
                            spi.setImageName(etDescription.getText().toString());
                        }

                        dp.modifyDatabaseItem(
                                dbId,
                                Integer.parseInt(etCategory.getText().toString()),
                                etName.getText().toString(),
                                etDescription.getText().toString(),
                                Integer.parseInt(etPrice.getText().toString()),
                                etImage.getText().toString());

                        /*
                        spi.setCategory(Integer.parseInt(etCategory.getText().toString()));
                        spi.setName(etName.getText().toString());
                        spi.setDetail(etDescription.getText().toString());
                        spi.setPrice(Integer.parseInt(etPrice.getText().toString()));
                        //etImage.getText().toString());
                        Log.i("myLog", "AdminDialog, módosítás után spi: " + spi.getName());
                        */
                        break;
                }
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

