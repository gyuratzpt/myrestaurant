package com.t.p.gy.myrestaurantapp;

import android.app.Application;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.t.p.gy.myrestaurantapp.connection.ProductsBackend;
import com.t.p.gy.myrestaurantapp.connection.RetrofitClient;
import com.t.p.gy.myrestaurantapp.data.SingleMenuItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class DrinkProcessor_v2 extends Application {
    final private static ArrayList<SingleMenuItem> drinks = new ArrayList<SingleMenuItem>();
    ProductsBackend myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public DrinkProcessor_v2(){
        Log.i("myLog","Drinkprocessor_v2 start");
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(ProductsBackend.class);


            compositeDisposable.add(myAPI.getDrinks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300) {
                        Log.i("myLog", "DrinkProcessor_v2 response: " + response.toString());
                        Log.i("myLog", "DrinkProcessor_v2 response: " + response.body().toString());
                        JsonArray allUsersJsonArray = response.body().getAsJsonArray("drink");
                        Log.i("myLog", "Teszt, drinks input mérete: " + allUsersJsonArray.size());
                        Log.i("myLog", "Teszt, drinks input elemei: " + allUsersJsonArray.toString());

                        JsonObject jsonamount = allUsersJsonArray.get(0).getAsJsonObject();
                        System.out.println(jsonamount.get("price").toString().replaceAll("\"", "") + " HUF");

                        List<Integer> idList = new ArrayList<Integer>();
                        List<String> nameList = new ArrayList<>();
                        List<String> detailList = new ArrayList<>();
                        List<Integer> priceList = new ArrayList<>();
                        List<String> pictureList = new ArrayList<>();

                        for(int i = 0; i < allUsersJsonArray.size(); i++){
                            idList.add(Integer.parseInt(allUsersJsonArray.get(i).getAsJsonObject().get("id").toString().replaceAll("\"", "")));
                            nameList.add(allUsersJsonArray.get(i).getAsJsonObject().get("name").toString().replaceAll("\"", ""));
                            detailList.add(allUsersJsonArray.get(i).getAsJsonObject().get("detail").toString().replaceAll("\"", ""));
                            priceList.add(Integer.parseInt(allUsersJsonArray.get(i).getAsJsonObject().get("price").toString().replaceAll("\"", "")));
                            pictureList.add(allUsersJsonArray.get(i).getAsJsonObject().get("picture").toString().replaceAll("\"", ""));
                        }

                        Integer[] idArray = new Integer[idList.size()];
                        idArray = idList.toArray(idArray);

                        String[] nameArray = new String[nameList.size()];
                        nameArray = nameList.toArray(nameArray);

                        String[] detailArray = new String[detailList.size()];
                        detailArray = detailList.toArray(detailArray);

                        Integer[] priceArray = new Integer[priceList.size()];
                        priceArray = priceList.toArray(priceArray);

                        String[] pictureArray = new String[pictureList.size()];
                        pictureArray = pictureList.toArray(pictureArray);

                        int id;
                        String name;
                        String detail;
                        int price;
                        int picture;

                        for(int i = 0; i < allUsersJsonArray.size(); i++) {
                            id = idArray[i];
                            name = nameArray[i];
                            detail = detailArray[i];
                            price = priceArray[i];
                            picture = DrinkActivity_old.listView.getResources().getIdentifier(pictureArray[i], "drawable", "com.t.p.gy.myrestaurantapp");

                            drinks.add(new SingleMenuItem(id, name, detail, price, picture,"drink"));
                        }
                    }
                    else {
                        System.out.println(response.code() + " " + response.errorBody().string());
                    }
                }));
        Log.i("myLog","Drinkprocessor vége");
        Log.i("myLog","Drinkprocessor tartalma: " + drinks.toString());
    }

    public ArrayList<SingleMenuItem> getDrinksList(){
        return drinks;
    }
}


