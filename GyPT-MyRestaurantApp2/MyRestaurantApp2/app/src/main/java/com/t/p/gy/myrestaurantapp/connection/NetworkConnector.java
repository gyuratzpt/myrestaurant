package com.t.p.gy.myrestaurantapp.connection;

import android.app.Application;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.t.p.gy.myrestaurantapp.AdminMaintenanceActivity;
import com.t.p.gy.myrestaurantapp.data.DataProcessor;
import com.t.p.gy.myrestaurantapp.data.Order;
import com.t.p.gy.myrestaurantapp.data.SingleProductItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static java.sql.Types.NULL;

public class NetworkConnector extends Application {
    private static NetworkConnector networkConnectorInstance;
    private List<SingleProductItem> productList;
    private List<String> productCategories;
    private List<Order> orderList = new ArrayList<>();
    //DataProcessor myDp = DataProcessor.getInstance();
    //private ArrayList<SingleMenuItem> downloadedDataSet = new ArrayList<SingleMenuItem>();
    ProductsBackend myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();


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
                            //Log.i("myLog", "ANC downloadAllProducts: " + inputJSONArray.get(i).toString());
                            Integer tmpInt = NULL;
                            boolean x = inputJSONArray.get(i).getAsJsonObject().get("picture").isJsonNull();
                            if (!x){
                                String tmpString = inputJSONArray.get(i).getAsJsonObject().get("picture").toString().replaceAll("\"", "");
                                //Log.i("myLog", "ANC downloadAllProducts: " + DataProcessor.getDrawableMap().toString());
                                tmpInt = Integer.parseInt(DataProcessor.getDrawableMap().get(tmpString).toString());
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
    public void getItem(final Integer _id, EditText etCategory, EditText etName, EditText etDescription, EditText etPrice, EditText etImage){
        Log.i("myLog", "getItem adatok: " + _id);
        compositeDisposable.add((Disposable) myAPI.getProductByID(_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300) {
                        JsonArray inputJSONArray = response.body().getAsJsonArray("product");
                        for (int i = 0; i < inputJSONArray.size(); i++) {
                            etCategory.setText(inputJSONArray.get(i).getAsJsonObject().get("category").toString().replaceAll("\"", ""));
                            etName.setText(inputJSONArray.get(i).getAsJsonObject().get("name").toString().replaceAll("\"", ""));
                            etDescription.setText(inputJSONArray.get(i).getAsJsonObject().get("detail").toString().replaceAll("\"", ""));
                            etPrice .setText(inputJSONArray.get(i).getAsJsonObject().get("price").toString().replaceAll("\"", ""));
                            etImage.setText(inputJSONArray.get(i).getAsJsonObject().get("picture").toString().replaceAll("\"", ""));
                           }
                        }
                    else {
                        Log.i("myLog", "AdminNetworkConnector error: " + response.code() + " " + response.errorBody().string());
                    }
                }));
    }
    public void modifyDatabaseItem(final int id, final int category, final String name, final String description, final Integer price,final String picture){
        compositeDisposable.add(myAPI.updateProduct(id, category, name, description, price, picture)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300){
                        Log.i("myLog", "ChangeProduct response code: " + response.body().toString());
                    } else {
                        Log.i("myLog", "ChangeProduct error, code: " + response.toString());
                    }
                }));
    }


    //orders
/*
    public String downloadOrders(TextView _tv){
        StringBuilder tmpString = new StringBuilder();

        compositeDisposable.add(myAPI.getAllOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300) {
                        Log.i("myLog", response.body().toString());
                        Log.i("myLog", response.body().getAsJsonArray("order").get(0).toString());
                        JsonArray inputJSONArray = response.body().getAsJsonArray("order");
                        for (int i = 0; i < inputJSONArray.size(); i++) {
                            String tmpCustomerName = inputJSONArray.get(i).getAsJsonObject().get("email").toString().replaceAll("\"", "");
                            String tmpItemName = inputJSONArray.get(i).getAsJsonObject().get("name").toString().replaceAll("\"", "");
                            int tmpAmount = Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("amount").toString().replaceAll("\"", ""));
                            int tmpPrice = Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("price").toString().replaceAll("\"", ""));

                            if (orderList.isEmpty() || !isCustomerHasOrder(tmpCustomerName)){
                                Log.i("myLog", "Üres lista: " + Boolean.toString(orderList.isEmpty()) + ", vagy nem létező felhasználó: " + Boolean.toString(!isCustomerHasOrder(tmpCustomerName)) + ", hozzáadás!");
                                orderList.add(new Order(tmpCustomerName,"2020.02.22"));
                            }
                            orderList.get(getOrderPosition(tmpCustomerName)).addItem(tmpItemName, tmpAmount);


                            tmpString.append(inputJSONArray.get(i).getAsJsonObject().get("orderID").toString().replaceAll("\"", "")
                                    + " " + inputJSONArray.get(i).getAsJsonObject().get("email").toString().replaceAll("\"", "")
                                    + " " + inputJSONArray.get(i).getAsJsonObject().get("name").toString().replaceAll("\"", "")
                                    + " " + inputJSONArray.get(i).getAsJsonObject().get("amount").toString().replaceAll("\"", "")
                                    + "\n"
                            );

                        }
                        for(Order x : orderList){
                            tmpString.append("Megrendelő:\n" + x.getCustomer() + ".\nRendelt tételek:\n");
                            for (Object y : x.getOrderList()){
                                tmpString.append("\t- " + y + " " + x.getAmount((String) y) + "\n");
                            }
                            tmpString.append("Teljes vételár: " + x.getPrice());
                        }
                        _tv.setTextSize(16);
                        _tv.setText(tmpString.toString());
                        //_tv.setText(orderList.toString());
                    }
                    else {
                        Log.i("myLog", "AdminOrders error: " + response.code() + " " + response.errorBody().string());
                    }
                })
        );
        Log.i("myLog", "végleges orderList: " + orderList);


        return orderList.toString();
    }
*/

    public List<Order> downloadOrders(){
        if(!orderList.isEmpty()) {
            orderList.clear();
        }
        compositeDisposable.add(myAPI.getAllOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300) {
                        Log.i("myLog", response.body().toString());
                        Log.i("myLog", response.body().getAsJsonArray("order").get(0).toString());
                        JsonArray inputJSONArray = response.body().getAsJsonArray("order");
                        for (int i = 0; i < inputJSONArray.size(); i++) {
                            String tmpCustomerName = inputJSONArray.get(i).getAsJsonObject().get("email").toString().replaceAll("\"", "");
                            String tmpItemName = inputJSONArray.get(i).getAsJsonObject().get("name").toString().replaceAll("\"", "");
                            int tmpAmount = Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("amount").toString().replaceAll("\"", ""));
                            int tmpPrice = Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("price").toString().replaceAll("\"", ""));

                            if (orderList.isEmpty() || !isCustomerHasOrder(tmpCustomerName)){
                                Log.i("myLog", "Üres lista: " + Boolean.toString(orderList.isEmpty()) + ", vagy nem létező felhasználó: " + Boolean.toString(!isCustomerHasOrder(tmpCustomerName)) + ", hozzáadás!");
                                orderList.add(new Order(tmpCustomerName,"2020.02.22"));
                            }
                            orderList.get(getOrderPosition(tmpCustomerName)).addSOIItem(tmpItemName, tmpAmount, tmpPrice);
                        }
                    }
                    else {
                        Log.i("myLog", "AdminOrders error: " + response.code() + " " + response.errorBody().string());
                    }
                })
        );
        Log.i("myLog", "végleges orderList: " + orderList);
        return orderList;
    }
    private boolean isCustomerHasOrder(String _name){
        Log.i("myLog", "isCustomerHasOrder: " + _name);
        for(Order x : orderList){
            if (x.getCustomer().equals(_name)) return true;
        }
        return false;
    }
    private int getOrderPosition(String _name){
        Log.i("myLog", "getOrderID: " + _name);
        int i = 0;
        while (!orderList.get(i).getCustomer().equals(_name)){
            i++;
        }
        Log.i("myLog", "getOrderID: " + i);
        return i;
    }

    //egyéb
    public String getOneUserByEmail(String _email){
        compositeDisposable.add(myAPI.getOneUserByEmail(_email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300) {
                        Log.i("myLog", response.body().toString());
                        //JsonArray inputJSONArray = response.body().getAsJsonArray("order");
                    }
                    else {
                        Log.i("myLog", " error: " + response.code() + " " + response.errorBody().string());
                    }
                })
        );
        return "user download kész";
    }
}