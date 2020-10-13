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

import java.sql.Time;
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
    private ProductsBackend myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<Order> orderList = new ArrayList<>();

    public NetworkConnector(){
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(ProductsBackend.class);
    }
    public static NetworkConnector getInstance(){
        if (networkConnectorInstance == null){ //if there is no instance available... create new one
            networkConnectorInstance = new NetworkConnector();
        }
        Log.i("myLog", "AdminNetworkConnector singleton");

        return networkConnectorInstance;
    }







    //products

    public List<SingleProductItem> downloadAllProducts(){
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
                                Log.i("myLog",inputJSONArray.get(i).getAsJsonObject().toString());
                                tmpInt = Integer.parseInt(DataProcessor.getDrawableMap().get(tmpString).toString());
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

    //public List<SingleProductItem> downloadFilteredProducts(List<SingleProductItem> _inputList, int _cat){
    public void downloadFilteredProducts(List<SingleProductItem> _inputList, int _cat){

        compositeDisposable.add(myAPI.getFilteredProducts(_cat)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300) {
                        Log.i("myLog", "ANC getFilteredProducts: " + response.body().toString());
                        JsonArray inputJSONArray = response.body().getAsJsonArray("product");
                        for (int i = 0; i < inputJSONArray.size(); i++) {
                            //Log.i("myLog", "ANC downloadAllProducts: " + inputJSONArray.get(i).toString());
                            Integer tmpInt = NULL;
                            boolean x = inputJSONArray.get(i).getAsJsonObject().get("picture").isJsonNull();
                            if (!x){
                                String tmpString = inputJSONArray.get(i).getAsJsonObject().get("picture").toString().replaceAll("\"", "");
                                tmpInt = Integer.parseInt(DataProcessor.getDrawableMap().get(tmpString).toString());
                            }
                            _inputList.add(new SingleProductItem(
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
        Log.i("myLog", "ANC filteredproducts: " + _inputList.toString());
        //return _inputList;
    }


    public List<SingleProductItem> getDownloadedList(){
        return downloadAllProducts();
    }
    //public List<SingleProductItem> getDownloadedList(List<SingleProductItem> _inputListint, int _cat){
    public void getDownloadedList(List<SingleProductItem> _inputList, int _cat){
        //Log.i("myLog", "NetworkConnector / getDownloadedList: " + _inputList.toString() + _cat);
        downloadFilteredProducts(_inputList, _cat);
        //return downloadFilteredProducts(_cat);
    }
    public List<String> downloadCategories(){
        List<String> catList = new ArrayList<>();
        try {
            Log.i("myLog", "downloadCategories try");
            compositeDisposable.add(myAPI.getCategories()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response.code() >= 200 && response.code() < 300) {
                            JsonArray inputJSONArray = response.body().getAsJsonArray("category");
                            for (int i = 0; i < inputJSONArray.size(); i++) {
                                Log.i("myLog", inputJSONArray.get(i).getAsJsonObject().get("name").toString());
                                catList.add(inputJSONArray.get(i).getAsJsonObject().get("name").toString().replaceAll("\"", ""));
                            }
                        } else {
                            Log.i("myLog", "Response error: " + response.code());
                            //Toast??: Toast.makeText(hogy kellelérni ay activity-t????, "" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }));
        }catch (Exception e){
            Log.i("myLog", "Hiba törléskor: " + e);
        }
        Log.i("myLog", catList.toString());
        return catList;
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
        Log.i("myLog", "createNewItem adatok: " + category + " " + name + " " + price + " " + picture);
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
    public void getItem(final int _id, EditText etCategory, EditText etName, EditText etDescription, EditText etPrice, EditText etImage){
        Log.i("myLog", "getItem adatok: " + _id);
        compositeDisposable.add((Disposable) myAPI.getProductByID(_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.i("myLog", response.body().toString());
                    if (response.code() >= 200 && response.code() < 300) {
                        JsonArray inputJSONArray = response.body().getAsJsonArray("product");
                        for (int i = 0; i < inputJSONArray.size(); i++) {
                            etCategory.setText(inputJSONArray.get(i).getAsJsonObject().get("categoryID").toString().replaceAll("\"", ""));
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
                    Log.i("myLog", response.body().toString());
                    if (response.code() >= 200 && response.code() < 300){
                        Log.i("myLog", "ChangeProduct response code: " + response.body().toString());
                    } else {
                        Log.i("myLog", "ChangeProduct error, code: " + response.toString());
                    }
                }));
    }






    //orders

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
                            int tmpOrderid = Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("orderID").toString().replaceAll("\"", ""));
                            String tmpCustomerName = inputJSONArray.get(i).getAsJsonObject().get("username").toString().replaceAll("\"", "");
                            String tmpItemName = inputJSONArray.get(i).getAsJsonObject().get("name").toString().replaceAll("\"", "");
                            int tmpAmount = Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("amount").toString().replaceAll("\"", ""));
                            int tmpPrice = Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("price").toString().replaceAll("\"", ""));

                            if (orderList.isEmpty() || !isCustomerHasOrder(tmpCustomerName)){
                                Log.i("myLog", "Üres lista: " + Boolean.toString(orderList.isEmpty()) + ", vagy nem létező felhasználó: " + Boolean.toString(!isCustomerHasOrder(tmpCustomerName)) + ", hozzáadás!");
                                orderList.add(new Order(tmpCustomerName, "2020.02.22"));
                            }
                            orderList.get(getOrderPosition(tmpCustomerName)).addSOIItem(tmpOrderid, tmpItemName, tmpAmount, tmpPrice);
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
    public void sendOrder(SingleProductItem x){
        Log.i("myLog", "termék: " + x);
        //userID - productID - amount
        compositeDisposable.add(myAPI.writeOrder(1, x.getID(), x.getCartAmount())
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
    public void setOrderToCompleted(List<Integer> _orderIDs){
        for(Integer id : _orderIDs){
            boolean status = true;
            compositeDisposable.add(myAPI.finalizeOrder(id, status)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        //Log.i("myLog", response.body().toString());
                        if (response.code() >= 200 && response.code() < 300){
                            Log.i("myLog", "ChangeProduct response code: " + response.body().toString());
                        } else {
                            Log.i("myLog", "ChangeProduct error, code: " + response.toString());
                        }
                    }));
        }
    }























    //egyéb
    public String getOneUserByEmail(String _email){
        compositeDisposable.add(myAPI.getOneUserByEmail(_email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300) {
                        Log.i("myLog", response.body().toString());
                        //JsonArray inputJSONArray = response.body().getAsJsonArray("user");
                    }
                    else {
                        Log.i("myLog", " error: " + response.code() + " " + response.errorBody().string());
                    }
                })
        );
        return "user download kész";
    }
}