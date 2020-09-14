package com.t.p.gy.myrestaurantapp.connection;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.t.p.gy.myrestaurantapp.AdminActivity;
import com.t.p.gy.myrestaurantapp.R;
import com.t.p.gy.myrestaurantapp.connection.ProductsBackend;
import com.t.p.gy.myrestaurantapp.connection.RetrofitClient;
import com.t.p.gy.myrestaurantapp.data.Cart;
import com.t.p.gy.myrestaurantapp.data.SingleMenuItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AdminNetworkConnector extends Application {
    private static AdminNetworkConnector adminNetworkConnectorInstance;
    private ArrayList<SingleMenuItem> downloadedDataSet = new ArrayList<SingleMenuItem>();
    ProductsBackend myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public AdminNetworkConnector(){
        Log.i("myLog","AdminNetworkConnector start");
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(ProductsBackend.class);
        downloadData();

        /*
        compositeDisposable.add(myAPI.getDrinks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300) {
                        Log.i("myLog", "AdminNetworkConnector response: " + response.toString());
                        Log.i("myLog", "AdminNetworkConnector response: " + response.body().toString());
                        JsonArray inputJSONArray = response.body().getAsJsonArray("drink");
                        //Log.i("myLog", "AdminNetworkConnector inputJSONArray mérete: " + inputJSONArray.size());
                        //Log.i("myLog", "AdminNetworkConnector inputJSONArray elemei: " + inputJSONArray.toString());
                        //Log.i("myLog", "AdminNetworkConnector next: " + inputJSONArray.get(0).getAsJsonObject().toString());
                        //Log.i("myLog", "AdminNetworkConnector next: " + inputJSONArray.get(0).getAsJsonObject().get("id"));

                        for (int i = 0; i < inputJSONArray.size(); i++) {
                            downloadedDataSet.add(new SingleMenuItem(
                                    Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("id").toString()),
                                    inputJSONArray.get(i).getAsJsonObject().get("name").toString(),
                                    inputJSONArray.get(i).getAsJsonObject().get("detail").toString(),
                                    Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("price").toString()),
                                    //inputJSONArray.get(0).getAsJsonObject().get("picture").toString()
                                    //picture = FoodActivity.listView.getResources().getIdentifier(pictureArray[i], "drawable", "com.t.p.gy.myrestaurantapp");
                                    R.drawable.kilkenny
                            ));
                        }
                    }
                    else {
                        Log.i("myLog", "AdminNetworkConnector error: " + response.code() + " " + response.errorBody().string());
                    }
                }));
        */
        Log.i("myLog","AdminNetworkConnector vége");
    }

    public static AdminNetworkConnector getInstance(){
        if (adminNetworkConnectorInstance == null){ //if there is no instance available... create new one
            adminNetworkConnectorInstance = new AdminNetworkConnector();
        }
        Log.i("myLog", "AdminNetworkConnector singleton");

        return adminNetworkConnectorInstance;
    }

    private void downloadData(){
        compositeDisposable.add(myAPI.getDrinks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300) {
                        Log.i("myLog", "AdminNetworkConnector response: " + response.toString());
                        Log.i("myLog", "AdminNetworkConnector response: " + response.body().toString());
                        JsonArray inputJSONArray = response.body().getAsJsonArray("drink");
                        //Log.i("myLog", "AdminNetworkConnector inputJSONArray mérete: " + inputJSONArray.size());
                        //Log.i("myLog", "AdminNetworkConnector inputJSONArray elemei: " + inputJSONArray.toString());
                        //Log.i("myLog", "AdminNetworkConnector next: " + inputJSONArray.get(0).getAsJsonObject().toString());
                        //Log.i("myLog", "AdminNetworkConnector next: " + inputJSONArray.get(0).getAsJsonObject().get("id"));

                        for (int i = 0; i < inputJSONArray.size(); i++) {
                            downloadedDataSet.add(new SingleMenuItem(
                                    Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("id").toString()),
                                    inputJSONArray.get(i).getAsJsonObject().get("name").toString(),
                                    inputJSONArray.get(i).getAsJsonObject().get("detail").toString(),
                                    Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("price").toString()),
                                    //inputJSONArray.get(0).getAsJsonObject().get("picture").toString()
                                    //picture = FoodActivity.listView.getResources().getIdentifier(pictureArray[i], "drawable", "com.t.p.gy.myrestaurantapp");
                                    R.drawable.kilkenny
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
                        Log.i("myLog", "AdminNetworkConnector response: " + response.toString());
                        Log.i("myLog", "AdminNetworkConnector response: " + response.body().toString());
                        JsonArray inputJSONArray = response.body().getAsJsonArray("food");
                        for (int i = 0; i < inputJSONArray.size(); i++) {
                            downloadedDataSet.add(new SingleMenuItem(
                                    Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("id").toString()),
                                    inputJSONArray.get(i).getAsJsonObject().get("name").toString(),
                                    inputJSONArray.get(i).getAsJsonObject().get("detail").toString(),
                                    Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("price").toString()),
                                    //inputJSONArray.get(0).getAsJsonObject().get("picture").toString()
                                    //picture = FoodActivity.listView.getResources().getIdentifier(pictureArray[i], "drawable", "com.t.p.gy.myrestaurantapp");
                                    R.drawable.csiga
                            ));
                        }
                    }
                    else {
                        Log.i("myLog", "AdminNetworkConnector error: " + response.code() + " " + response.errorBody().string());
                    }
                }));
    }

    public ArrayList<SingleMenuItem> getDownloadedList(){
        return downloadedDataSet;
    }

    public void deleteFromDrinksTable(final String inputName){
        Log.i("myLog", "deleteFromDrinksTable start...");
        try {
            Log.i("myLog", "deleteFromDrinksTable try start... inputname: " + inputName);
            compositeDisposable.add(myAPI.deleteDrinks("foxi")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response.code() >= 200 && response.code() < 300) {
                            Log.i("myLog", "Response code: " + response);
                            Log.i("myLog", "Response code: " + response.code());
                            Log.i("myLog", "Response body: " + response.body().toString());
                            //Toast??
                        } else {
                            //Toast??: Toast.makeText(hogy kellelérni ay activity-t????, "" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }));
        }catch (Exception e){
            Log.i("myLog", "Hiba törléskor: " + e);
        }
    }
}