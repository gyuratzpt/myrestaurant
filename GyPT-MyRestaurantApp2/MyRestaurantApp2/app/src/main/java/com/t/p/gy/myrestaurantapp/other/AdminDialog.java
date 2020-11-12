package com.t.p.gy.myrestaurantapp.other;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.t.p.gy.myrestaurantapp.MenuActivity;
import com.t.p.gy.myrestaurantapp.R;
import com.t.p.gy.myrestaurantapp.adapter.AdapterForMenuRecyclerView;
import com.t.p.gy.myrestaurantapp.connection.NetworkConnector;
import com.t.p.gy.myrestaurantapp.data.DataProcessor;
import com.t.p.gy.myrestaurantapp.data.SingleProductItem;

public class AdminDialog {
    DataProcessor dp = DataProcessor.getInstance();

    public void showDialog(Context _context, String _dialogTitle, String _buttonTitle, Integer dbId, SingleProductItem spi) {
        final Dialog adminDialog = new Dialog(_context);
        adminDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        adminDialog.setCancelable(false);
        adminDialog.setContentView(R.layout.dialog_maintenance);
        TextView tvTitle = (TextView) adminDialog.findViewById(R.id.dialog_additem_title);
        tvTitle.setText(_dialogTitle);

        EditText etCategory = (EditText) adminDialog.findViewById(R.id.dialog_additem_category);
        etCategory.setVisibility(View.GONE);
        EditText etName = (EditText) adminDialog.findViewById(R.id.dialog_additem_name);
        EditText etDescription = (EditText) adminDialog.findViewById(R.id.dialog_additem_description);
        EditText etPrice = (EditText) adminDialog.findViewById(R.id.dialog_additem_price);
        EditText etImage = (EditText) adminDialog.findViewById(R.id.dialog_additem_image);

        Spinner categorySpinner = (Spinner) adminDialog.findViewById(R.id.dialog_additem_categoryspinner);
        ArrayAdapter categorySpinnerArrayAdapter;
        categorySpinnerArrayAdapter = new ArrayAdapter(_context, R.layout.spinner_item, DataProcessor.getInstance().getCustomizedCategoryList("Válassz..."));
        categorySpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        categorySpinner.setAdapter(categorySpinnerArrayAdapter);


        Button okButton = (Button) adminDialog.findViewById(R.id.dialog_additem_okbutton);
        Button cancelButton = (Button) adminDialog.findViewById(R.id.dialog_additem_cancelbutton);
        okButton.setText(_buttonTitle);
        if (_buttonTitle.equals("Módosít")) {
            Log.i("myLog", "Tétel id-ja: " + dbId);
            dp.getItemFromDatabase(dbId, etCategory, etName, etDescription, etPrice, etImage);
        }

        categorySpinner.setSelection(0);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0) {
                    okButton.setClickable(false);
                    okButton.setBackgroundColor(_context.getResources().getColor(R.color.md_red_A700));

                }
                else{
                    okButton.setClickable(true);
                    okButton.setBackgroundColor(_context.getResources().getColor(R.color.md_green_A700));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        categorySpinner.setSelection(0);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (_buttonTitle) {
                    case "Hozzáad":
                    Log.i("myLog", "Lista mérete hozzáadás előtt: " + dp.getProductList(0).size());
                    dp.addItemToList(
                            new SingleProductItem(
                            null,
                            categorySpinner.getSelectedItemPosition(),
                            etName.getText().toString(),
                            etDescription.getText().toString(),
                            Integer.parseInt(etPrice.getText().toString()),
                            etImage.getText().toString()
                            )
                    );
                    dp.addItemToDatabase(
                            categorySpinner.getSelectedItemPosition(),
                            etName.getText().toString(),
                            etDescription.getText().toString(),
                            Integer.parseInt(etPrice.getText().toString()),
                            (etImage.getText().toString().length() > 0) ? etImage.getText().toString() : null
                    );

                    Log.i("myLog", "Lista mérete hozzáadás után: " + dp.getProductList(0).size());

                    break;
                    case "Módosít":
                        Log.i("myLog", "AdminDialog, módosítás...");
                        Log.i("myLog", "AdminDialog, módosítás előtt category: " + spi.getCategory());
                        Log.i("myLog", "AdminDialog, input category: " + categorySpinner.getSelectedItemPosition());
                        //Log.i("myLog", "AdminDialog, input category: " + etCategory.getText().toString());


                        if(spi.getCategory() != categorySpinner.getSelectedItemPosition()){
                            spi.setCategory(categorySpinner.getSelectedItemPosition());
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
                                spi.getCategory(),
                                spi.getName(),
                                spi.getDetail(),
                                spi.getPrice(),
                                spi.getImageName());

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

