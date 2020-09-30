package com.t.p.gy.myrestaurantapp.connection;

import android.app.Application;
import android.util.Log;

import com.google.gson.JsonArray;
import com.t.p.gy.myrestaurantapp.AdminMaintenanceActivity;
import com.t.p.gy.myrestaurantapp.data.SingleProductItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static java.sql.Types.NULL;

public class NetworkConnector extends Application {
    private static NetworkConnector networkConnectorInstance;
    //private ArrayList<SingleMenuItem> downloadedDataSet = new ArrayList<SingleMenuItem>();
    ProductsBackend myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<SingleProductItem> productList;
    private List<String> productCategories;

    public NetworkConnector(){
        Log.i("myLog","AdminNetworkConnector start");
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(ProductsBackend.class);
        //downloadData();
        //downloadAllProducts();

        Log.i("myLog","AdminNetworkConnector vége");
    }

    public static NetworkConnector getInstance(){
        if (networkConnectorInstance == null){ //if there is no instance available... create new one
            networkConnectorInstance = new NetworkConnector();
        }
        Log.i("myLog", "AdminNetworkConnector singleton");

        return networkConnectorInstance;
    }

    //products

    private List<SingleProductItem> downloadAllProducts(){
        List<SingleProductItem> downloadedDataSet = new ArrayList<SingleProductItem>();
        compositeDisposable.add(myAPI.getAllProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300) {
                        //Log.i("myLog", "ANC downloadAllProducts: " + response.body().toString());
                        JsonArray inputJSONArray = response.body().getAsJsonArray("product");
                        for (int i = 0; i < inputJSONArray.size(); i++) {
                            Integer tmpInt = NULL;
                            boolean x = inputJSONArray.get(i).getAsJsonObject().get("picture").isJsonNull();
                            if (!x){
                                String tmpString = inputJSONArray.get(i).getAsJsonObject().get("picture").toString().replaceAll("\"", "");
                                tmpInt = Integer.parseInt(AdminMaintenanceActivity.getDrawableMap().get(tmpString).toString());
                                //tmpInt = Integer.parseInt(MenuActivity.getDrawableMap().get(tmpString).toString());


                            }
                            downloadedDataSet.add(new SingleProductItem(
                                    Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("id").toString().replaceAll("\"", "")),
                                    Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("categoryID").toString().replaceAll("\"", "")),
                                    inputJSONArray.get(i).getAsJsonObject().get("name").toString().replaceAll("\"", ""),
                                    inputJSONArray.get(i).getAsJsonObject().get("detail").toString().replaceAll("\"", ""),
                                    Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("price").toString().replaceAll("\"", "")),
                                    //Integer.parseInt(AdminMaintenanceActivity.getDrawableMap().get(inputJSONArray.get(i).getAsJsonObject().get("picture").toString().replaceAll("\"", "")).toString())
                                    tmpInt
                            ));
                        }
                    }
                    else {
                        Log.i("myLog", "AdminNetworkConnector error: " + response.code() + " " + response.errorBody().string());
                    }
                }));
        return downloadedDataSet;
    }

    public List<SingleProductItem> getDownloadedList(){
        return downloadAllProducts();
    }

    public void createNewItem(final Integer category, final String name, final String description, final Integer price,final String picture){
        Log.i("myLog", "createNewItem adatok: " + category + " " + name + " " + price);
        compositeDisposable.add((Disposable) myAPI.addProduct(category, name, description, price, picture)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.i("myLog", "CreateItem ok, code: " + response);
                }, throwable -> {
                    //Toast.makeText(this, "Drink added successfully!", Toast.LENGTH_SHORT).show();
                    Log.i("myLog", "Miért throwable????");
                })
        );
    }

    public boolean deleteProduct(final Integer id){
        try {
            Log.i("myLog", "deleteProduct try start... id: " + id);
            compositeDisposable.add(myAPI.deleteProduct(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response.code() >= 200 && response.code() < 300) {
                            Log.i("myLog", "DeleteProduct response code: " + response);
                            //Log.i("myLog", "DeleteProduct response code: " + response.code());
                            //Log.i("myLog", "DeleteProduct response body: " + response.body().toString());
                        } else {
                            Log.i("myLog", "Delete error, code: " + response);
                            Log.i("myLog", "Nem áll rendelés alatt??");

                            //Toast??: Toast.makeText(hogy kellelérni ay activity-t????, "" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }));
            return true;
        }catch (Exception e){
            Log.i("myLog", "Hiba törléskor: " + e);
            return false;
        }
    }

    public String downloadCategories(){
        StringBuilder tmpString = new StringBuilder("");

        try {
            Log.i("myLog", "downloadCategories try");
            compositeDisposable.add(myAPI.getCategories()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response.code() >= 200 && response.code() < 300) {
                            Log.i("myLog", "Response body: " + response.body().toString());
                            tmpString.append(response.body().toString());
                        } else {
                            Log.i("myLog", "Response error: " + response.code());
                            //Toast??: Toast.makeText(hogy kellelérni ay activity-t????, "" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }));
        }catch (Exception e){
            Log.i("myLog", "Hiba törléskor: " + e);
        }
        return tmpString.toString();
    }


    //orders

    public String downloadOrders(){
        String tmp = "xyz";
        StringBuilder tmpString = new StringBuilder();

        compositeDisposable.add(myAPI.getAllOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300) {
                        JsonArray inputJSONArray = response.body().getAsJsonArray("order");
                        for (int i = 0; i < inputJSONArray.size(); i++) {
                            tmpString.append(inputJSONArray.get(i).getAsJsonObject().get("orderID").toString().replaceAll("\"", "")
                                    + " " + inputJSONArray.get(i).getAsJsonObject().get("userID").toString().replaceAll("\"", "")
                                    + " " + inputJSONArray.get(i).getAsJsonObject().get("productID").toString().replaceAll("\"", "")
                                    + " " + inputJSONArray.get(i).getAsJsonObject().get("amount").toString().replaceAll("\"", "")
                                    + "\n"

                            );
                            Log.i("myLog", "tmpStr: " + tmpString);
                        }
                    }
                    else {
                        Log.i("myLog", "AdminOrders error: " + response.code() + " " + response.errorBody().string());
                    }
                })
        );

        Log.i("myLog", "végleges Stringbuilder: " + tmpString);
        return tmpString.toString();
    }





    //egyéb
    /*
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
    */
}