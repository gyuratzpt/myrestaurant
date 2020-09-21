package com.t.p.gy.myrestaurantapp.connection;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.t.p.gy.myrestaurantapp.AdminActivity_old;
import com.t.p.gy.myrestaurantapp.AdminMaintenanceActivity;
import com.t.p.gy.myrestaurantapp.data.SingleMenuItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AdminNetworkConnector extends Application {
    private static AdminNetworkConnector adminNetworkConnectorInstance;
    //private ArrayList<SingleMenuItem> downloadedDataSet = new ArrayList<SingleMenuItem>();
    ProductsBackend myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public AdminNetworkConnector(){
        Log.i("myLog","AdminNetworkConnector start");
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(ProductsBackend.class);
        downloadData();

        Log.i("myLog","AdminNetworkConnector vége");
    }

    public static AdminNetworkConnector getInstance(){
        if (adminNetworkConnectorInstance == null){ //if there is no instance available... create new one
            adminNetworkConnectorInstance = new AdminNetworkConnector();
        }
        Log.i("myLog", "AdminNetworkConnector singleton");

        return adminNetworkConnectorInstance;
    }

    private List<SingleMenuItem> downloadData(){
        List<SingleMenuItem> downloadedDataSet = new ArrayList<SingleMenuItem>();
        compositeDisposable.add(myAPI.getDrinks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300) {
                        JsonArray inputJSONArray = response.body().getAsJsonArray("drink");
                        for (int i = 0; i < inputJSONArray.size(); i++) {
                            downloadedDataSet.add(new SingleMenuItem(
                                    Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("id").toString().replaceAll("\"", "")),
                                    inputJSONArray.get(i).getAsJsonObject().get("name").toString().replaceAll("\"", ""),
                                    inputJSONArray.get(i).getAsJsonObject().get("detail").toString().replaceAll("\"", ""),
                                    Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("price").toString().replaceAll("\"", "")),
                                    Integer.parseInt(AdminMaintenanceActivity.getDrawableMap().get(inputJSONArray.get(i).getAsJsonObject().get("picture").toString().replaceAll("\"", "")).toString()),
                                    //macskaköröm hiba!!!
                                    "drink"
                            ));
                        }
                    }
                    else {
                        Log.i("myLog", "AdminNetworkConnector error: " + response.code() + " " + response.errorBody().string());
                    }
                }));
        compositeDisposable.add(myAPI.getFoods()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300) {
                        //Log.i("myLog", "AdminNetworkConnector response: " + response.toString());
                        //Log.i("myLog", "AdminNetworkConnector response: " + response.body().toString());
                        JsonArray inputJSONArray = response.body().getAsJsonArray("food");
                        for (int i = 0; i < inputJSONArray.size(); i++) {
                            downloadedDataSet.add(new SingleMenuItem(
                                    Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("id").toString().replaceAll("\"", "")),
                                    inputJSONArray.get(i).getAsJsonObject().get("name").toString().replaceAll("\"", ""),
                                    inputJSONArray.get(i).getAsJsonObject().get("detail").toString().replaceAll("\"", ""),
                                    Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("price").toString().replaceAll("\"", "")),
                                    Integer.parseInt(AdminMaintenanceActivity.getDrawableMap().get(inputJSONArray.get(i).getAsJsonObject().get("picture").toString().replaceAll("\"", "")).toString()),
                                    "food"
                            ));
                        }
                    }
                    else {
                        Log.i("myLog", "AdminNetworkConnector error: " + response.code() + " " + response.errorBody().string());
                    }
                }));
        return downloadedDataSet;
    }

    public List<SingleMenuItem> getDownloadedList(){
        return downloadData();
    }

    public boolean deleteFromDrinksTable(final String inputName){
        Log.i("myLog", "deleteFromDrinksTable start...");
        try {
            Log.i("myLog", "deleteFromDrinksTable try start... inputname: " + inputName);
            Log.i("myLog", "deleteFromDrinksTable try start... inputname: " + inputName +" "+ inputName.length());
            compositeDisposable.add(myAPI.deleteDrinks(inputName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response.code() >= 200 && response.code() < 300) {
                            Log.i("myLog", "Response code: " + response);
                            Log.i("myLog", "Response code: " + response.code());
                            Log.i("myLog", "Response body: " + response.body().toString());
                        } else {
                            //Toast??: Toast.makeText(hogy kellelérni ay activity-t????, "" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }));
            return true;
        }catch (Exception e){
            Log.i("myLog", "Hiba törléskor: " + e);
            return false;
        }
    }

    public boolean deleteFromFoodsTable(final String inputName){
        Log.i("myLog", "deleteFromFoodsTable start...");
        try {
            Log.i("myLog", "deleteFromFoodsTable try start... inputname: " + inputName);
            Log.i("myLog", "deleteFromFoodsTable try start... inputname: " + inputName +" "+ inputName.length());
            compositeDisposable.add(myAPI.deleteFoods(inputName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response.code() >= 200 && response.code() < 300) {
                            Log.i("myLog", "Response code: " + response);
                            Log.i("myLog", "Response code: " + response.code());
                            Log.i("myLog", "Response body: " + response.body().toString());
                        } else {
                            //Toast??: Toast.makeText(hogy kellelérni ay activity-t????, "" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }));
            return true;
        }catch (Exception e){
            Log.i("myLog", "Hiba törléskor: " + e);
            return false;
        }
    }

    public void createDrinkItem(final String name, final String description, final Integer price,final String picture){

        compositeDisposable.add((Disposable) myAPI.addDrinks(name, description, price, picture)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                }, throwable -> {
                    //Toast.makeText(this, "Drink added successfully!", Toast.LENGTH_SHORT).show();
                })
        );
    }

    public void createFoodItem(final String name, final String description, final Integer price,final String picture){
        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(AdminActivity.this);

        compositeDisposable.add(myAPI.addFoods(name, description, price, picture)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300){
                        Toast.makeText(this, "Food added successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }



}