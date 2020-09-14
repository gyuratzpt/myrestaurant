package com.t.p.gy.myrestaurantapp;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.t.p.gy.myrestaurantapp.connection.ProductsBackend;
import com.t.p.gy.myrestaurantapp.connection.RetrofitClient;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AdminActivity_old extends AppCompatActivity{
    static final private ArrayList<String> adminSpinnerList = new ArrayList<String>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ProductsBackend myAPI;
    Gson gson = new GsonBuilder().setLenient()
            .create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_layout);

        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(ProductsBackend.class);

        //vissza gomb
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Log.v("MyLog", "Gallery OK");

        //editText-ek példányosítása
        EditText eTName = (EditText) findViewById(R.id.admin_input_Name);
        EditText eTDescription = (EditText) findViewById(R.id.admin_input_Desc);
        EditText eTPrice = (EditText) findViewById(R.id.admin_input_Price);
        EditText eTPic = (EditText) findViewById(R.id.admin_input_Image);
        //add gomb példányosítása
        Button addButton = (Button) findViewById(R.id.admin_button_add);
        Button changeButton = (Button) findViewById(R.id.admin_button_change);
        Button deleteButton = (Button) findViewById(R.id.admin_button_delete);
        Button testButton = findViewById(R.id.main_testbutton);
        initSpinner(eTName, eTDescription, eTPrice, eTPic, addButton, changeButton, deleteButton);


        //metódus teszteléshez ideiglenes gomb
        testButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                /*
                compositeDisposable.add(myAPI.getFoodKebab()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            Log.i("myLog", "response: " + response.toString());
                            if (response.code() >= 200 && response.code() < 300) {
                                Log.i("myLog", "tesztmetódus: " + response.body().toString());
                                JsonArray allUsersJsonArray = response.body().getAsJsonArray("foodnames");
                                //Log.i("myLog", "Teszt, getFoodKebab input mérete: " + allUsersJsonArray.size());
                                //Log.i("myLog", "Teszt, getFoodKebab input elemei: " + allUsersJsonArray.toString());
                                JsonObject jsonamount = allUsersJsonArray.get(0).getAsJsonObject();
                                //Log.i("myLog", "Teszt, jsonamount input mérete: " + jsonamount.size());
                                //Log.i("myLog", "Teszt, jsonamount input elemei: " + jsonamount.toString());

                            } else {
                                Log.i("myLog", "HIBA!!!" + response.code() + " " + response.errorBody().string());
                            }
                        }));

                 */
            }

        });
    }

    private void initSpinner(EditText eTName, EditText eTDescription, EditText eTPrice, EditText eTPic, Button addButton, Button changeButton, Button deleteButton) {
        Spinner adminSpinner = findViewById(R.id.admin_spinner);

        if (adminSpinnerList.isEmpty()) {
            adminSpinnerList.add("Choose category");
            adminSpinnerList.add("Foods");
            adminSpinnerList.add("Drinks");
        }

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(AdminActivity_old.this, R.layout.spinner_item, adminSpinnerList);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        adminSpinner.setAdapter(spinnerArrayAdapter);
        adminSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    switch (i) {
                        case 1:
                            Toast.makeText(view.getContext(), "Foods kiválasztva!", Toast.LENGTH_LONG).show();
                            changeButton.setOnClickListener(v -> {
                                String tmpName = eTName.getText().toString();
                                String tmpDescription = eTDescription.getText().toString();
                                int tmpPrice = Integer.valueOf(eTPrice.getText().toString());
                                String tmpPic = eTPic.getText().toString();
                                areFieldsFilled(eTName, eTDescription, eTPrice);
                                changeFoodItem(tmpName, tmpDescription, tmpPrice, tmpPic);
                            });
                            addButton.setOnClickListener(v -> {
                                String tmpName = eTName.getText().toString();
                                String tmpDescription = eTDescription.getText().toString();
                                int tmpPrice = Integer.valueOf(eTPrice.getText().toString());
                                String tmpPic = eTPic.getText().toString();
                                areFieldsFilled(eTName, eTDescription, eTPrice);
                                createFoodItem(tmpName, tmpDescription, tmpPrice, tmpPic);
                            });
                            deleteButton.setOnClickListener(v -> {
                                String tmpName = eTName.getText().toString();
                                deleteFoodItem(tmpName);
                            });

                            break;
                        case 2:
                            Toast.makeText(view.getContext(), "Drinks kiválasztva!", Toast.LENGTH_LONG).show();
                            changeButton.setOnClickListener(v -> {
                                String tmpName = eTName.getText().toString();
                                String tmpDescription = eTDescription.getText().toString();
                                int tmpPrice = Integer.valueOf(eTPrice.getText().toString());
                                String tmpPic = eTPic.getText().toString();
                                areFieldsFilled(eTName, eTDescription, eTPrice);
                                changeDrinkItem(tmpName, tmpDescription, tmpPrice, tmpPic);
                            });
                            addButton.setOnClickListener(v -> {
                                String tmpName = eTName.getText().toString();
                                String tmpDescription = eTDescription.getText().toString();
                                int tmpPrice = Integer.valueOf(eTPrice.getText().toString());
                                String tmpPic = eTPic.getText().toString();
                                areFieldsFilled(eTName, eTDescription, eTPrice);
                                createDrinkItem(tmpName, tmpDescription, tmpPrice, tmpPic);
                            });
                            deleteButton.setOnClickListener(v -> {
                                String tmpName = eTName.getText().toString();
                                deleteDrinkItem(tmpName);
                            });
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private boolean areFieldsFilled(EditText editText1, EditText editText2, EditText editText3){
        if (editText1.getText().toString().equals("") || editText2.getText().toString().equals("") || editText3.getText().toString().equals("")) {
            Toast.makeText(AdminActivity_old.this, "Fields cannot be empty!", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createDrinkItem(final String name, final String description, final Integer price,final String picture){

        compositeDisposable.add((Disposable) myAPI.addDrinks(name, description, price, picture)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                }, throwable -> {
                    Toast.makeText(AdminActivity_old.this, "Drink added successfully!", Toast.LENGTH_SHORT).show();
                })
        );
    }

    private void createFoodItem(final String name, final String description, final Integer price,final String picture){
        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(AdminActivity.this);

        compositeDisposable.add(myAPI.addFoods(name, description, price, picture)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300){
                        Toast.makeText(AdminActivity_old.this, "Food added successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AdminActivity_old.this, "" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void changeDrinkItem(final String name, final String description, final Integer price,final String picture){
        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(AdminActivity.this);

        compositeDisposable.add(myAPI.updateDrinks(name, name, description, price, picture)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300){
                        Toast.makeText(AdminActivity_old.this, "Drink change successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AdminActivity_old.this, "" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void changeFoodItem(final String name, final String description, final Integer price,final String picture){
        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(AdminActivity.this);

        compositeDisposable.add(myAPI.updateFoods(name, name, description, price, picture)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300){
                        Toast.makeText(AdminActivity_old.this, "Food change successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AdminActivity_old.this, "" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void deleteDrinkItem(final String name){
        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(AdminActivity.this);

        compositeDisposable.add(myAPI.deleteDrinks(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300){
                        Toast.makeText(AdminActivity_old.this, "Drink delete successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AdminActivity_old.this, "" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void deleteFoodItem(final String name){
        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(AdminActivity.this);

        compositeDisposable.add(myAPI.deleteFoods(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300){
                        Toast.makeText(AdminActivity_old.this, "Food delete successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AdminActivity_old.this, "" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }
}
