package com.t.p.gy.myrestaurantapp.connection;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.t.p.gy.myrestaurantapp.AdminMaintenanceActivity;
import com.t.p.gy.myrestaurantapp.data.DataProcessor;
import com.t.p.gy.myrestaurantapp.data.Order;
import com.t.p.gy.myrestaurantapp.data.SingleProductItem;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

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

    private List<SingleProductItem> tmpList = new ArrayList<>();
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

    //lekérések
        // teljes listák
    //JsonArray
    public List<SingleProductItem> downloadAllProducts(){
        List<SingleProductItem> downloadedDataSet = new ArrayList<SingleProductItem>();

        compositeDisposable.add(myAPI.getAllProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300) {
                        JsonArray inputJSONArray = response.body().getAsJsonArray("product");
                        for (int i = 0; i < inputJSONArray.size(); i++) {
                            downloadedDataSet.add(new SingleProductItem(
                                    Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("id").toString().replaceAll("\"", "")),
                                    Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("categoryID").toString().replaceAll("\"", "")),
                                    inputJSONArray.get(i).getAsJsonObject().get("name").toString().replaceAll("\"", ""),
                                    inputJSONArray.get(i).getAsJsonObject().get("detail").toString().replaceAll("\"", ""),
                                    Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("price").toString().replaceAll("\"", "")),
                                    getImageID(inputJSONArray.get(i).getAsJsonObject().get("picture"))

                            ));
                        }
                    }
                    else {
                        Log.i("myLog", "AdminNetworkConnector error: " + response.code() + " " + response.errorBody().string());
                    }
                }));
        return downloadedDataSet;
    }
    //JsonArray
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
                                Log.i("myLog", "downloadCategories response: " + catList.toString());
                            }
                        } else {
                            Log.i("myLog", "Response error: " + response.code());
                        }
                    }));
        }catch (Exception e){
            Log.i("myLog", "Hiba a letöltéskor: " + e);
        }
        Log.i("myLog", "downloadCategories listája: " + catList.toString());
        return catList;
    }
    //JsonArray
    public List<Order> downloadOrders(){
        if(!orderList.isEmpty()) {
            orderList.clear();
        }
        compositeDisposable.add(myAPI.getAllOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300) {
                        JsonArray inputJSONArray = response.body().getAsJsonArray("order");
                        for (int i = 0; i < inputJSONArray.size(); i++) {
                                int tmpOrderid = Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("orderID").toString().replaceAll("\"", ""));
                                String tmpCustomerName = inputJSONArray.get(i).getAsJsonObject().get("username").toString().replaceAll("\"", "");

                                String tmpCustomerAddress = inputJSONArray.get(i).getAsJsonObject().get("address").toString().replaceAll("\"", "");
                                String tmpCustomerPhoneNumber = inputJSONArray.get(i).getAsJsonObject().get("phonenumber").toString().replaceAll("\"", "");

                                String tmpItemName = inputJSONArray.get(i).getAsJsonObject().get("name").toString().replaceAll("\"", "");
                                int tmpAmount = Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("amount").toString().replaceAll("\"", ""));
                                int tmpPrice = Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("price").toString().replaceAll("\"", ""));

                                if (orderList.isEmpty() || !isCustomerHasOrder(tmpCustomerName)) {
                                    Log.i("myLog", "Üres lista: " + Boolean.toString(orderList.isEmpty()) + ", vagy nem létező felhasználó: " + Boolean.toString(!isCustomerHasOrder(tmpCustomerName)) + ", hozzáadás!");
                                    orderList.add(new Order(tmpCustomerName, tmpCustomerAddress, tmpCustomerPhoneNumber, "2020.02.22"));
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


        //szűrt listák
    //JsonArray
    //public List<SingleProductItem> downloadFilteredProducts(List<SingleProductItem> _list, int _cat){
    public void downloadFilteredProducts(List<SingleProductItem> _list, int _cat){
        tmpList.clear();
            compositeDisposable.add(myAPI.getFilteredProducts(_cat)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response.code() >= 200 && response.code() < 300) {
                            Log.i("myLog", "ANC getFilteredProducts: " + response.body().toString());
                            JsonArray inputJSONArray = response.body().getAsJsonArray("product");
                            for (int i = 0; i < inputJSONArray.size(); i++) {
                                _list.add(new SingleProductItem(
                                        Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("id").toString().replaceAll("\"", "")),
                                        Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("categoryID").toString().replaceAll("\"", "")),
                                        inputJSONArray.get(i).getAsJsonObject().get("name").toString().replaceAll("\"", ""),
                                        inputJSONArray.get(i).getAsJsonObject().get("detail").toString().replaceAll("\"", ""),
                                        Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("price").toString().replaceAll("\"", "")),
                                        getImageID(inputJSONArray.get(i).getAsJsonObject().get("picture"))
                                ));
                            }
                        }
                        else {
                            Log.i("myLog", "AdminNetworkConnector error: " + response.code() + " " + response.errorBody().string());
                        }
                    }));
            Log.i("myLog", "ANC filteredproducts: " + tmpList.toString());
            //return tmpList;
        }




        //egyedi adatok
    //JsonObject
    public void getItem(final int _id, EditText etCategory, EditText etName, EditText etDescription, EditText etPrice, EditText etImage){
            Log.i("myLog", "getItem adatok: " + _id);
            compositeDisposable.add((Disposable) myAPI.getProductByID(_id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        Log.i("myLog", "getItem response body: " + response.body().toString());
                        if (response.code() >= 200 && response.code() < 300) {

                            JsonObject inputJSONObject = response.body().getAsJsonObject("product");
                            etCategory.setText(inputJSONObject.get("categoryID").toString().replaceAll("\"", ""));
                            etName.setText(inputJSONObject.get("name").toString().replaceAll("\"", ""));
                            etDescription.setText(inputJSONObject.get("detail").toString().replaceAll("\"", ""));
                            etPrice.setText(inputJSONObject.get("price").toString().replaceAll("\"", ""));
                            etImage.setText(inputJSONObject.get("picture").toString().replaceAll("\"", ""));
                        }
                        else {
                            Log.i("myLog", "AdminNetworkConnector error: " + response.code() + " " + response.errorBody().string());
                        }
                    }));
        }

    public String getOneUserByEmail(String _email){
        compositeDisposable.add(myAPI.getOneUserByEmail(_email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300) {
                        Log.i("myLog", response.body().toString());
                    }
                    else {
                        Log.i("myLog", " error: " + response.code() + " " + response.errorBody().string());
                    }
                })
        );
        return "user download kész";
    }




    //törlések, zárások
    public boolean deleteProduct(final Integer id){
        try {
            Log.i("myLog", "deleteProduct try start... id: " + id);
            compositeDisposable.add(myAPI.deleteProduct(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response.code() >= 200 && response.code() < 300) {
                            Log.i("myLog", "DeleteProduct response code: " + response);
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




    //beírások
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



    //módosítások
    public void modifyDatabaseItem(final int id, final int category, final String name, final String description, final Integer price,final String picture){
        compositeDisposable.add(myAPI.updateProduct(id, category, name, description, price, picture)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.i("myLog", response.body().toString());
                    if (response.code() >= 200 && response.code() < 300){
                        Log.i("myLog", "ChangeProduct response code: " + response.toString());
                    } else {
                        Log.i("myLog", "ChangeProduct error, code: " + response.toString());
                    }
                }));
    }
    public void setOrderToCompleted(List<Integer> _orderIDs){
        for(Integer id : _orderIDs){
            //compositeDisposable.add(myAPI.finalizeOrder(id, 1)
            compositeDisposable.add(myAPI.finalizeOrder_Gettel(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        //Log.i("myLog", response.body().toString());
                        if (response.code() >= 200 && response.code() < 300){
                            Log.i("myLog", "setOrderToCompleted response code: " + response.toString());
                        } else {
                            Log.i("myLog", "setOrderToCompleted error, code: " + response.toString());
                        }
                    }));
        }
    }









    //kiváltani, felesleges
    public List<SingleProductItem> getDownloadedList(){
        return downloadAllProducts();
    }






    //segédfüggvények
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
    private int getImageID(JsonElement _imageName){
        Integer resID;
        boolean x = _imageName.isJsonNull();
        if (!x && DataProcessor.getDrawableMap().containsKey(_imageName.toString().replaceAll("\"", ""))){
            Log.i("myLog", "getImageID: " + _imageName.toString());
            resID = Integer.parseInt(DataProcessor.getDrawableMap().get(_imageName.toString().replaceAll("\"", "")).toString());
            String url = "http://10.0.2.2:3000/images/" + _imageName.toString().replaceAll("\"", "");

        }
        else resID = Integer.parseInt(DataProcessor.getDrawableMap().get("noimage").toString());
        return resID;
    }




    public Bitmap setImage(){
        Bitmap downloadedImage = null;
        try {
        SingleImageDownloadTask sIDTask = new SingleImageDownloadTask();
        downloadedImage = sIDTask.execute("https://picsum.photos/seed/picsum/600/600").get();
        }catch(Exception e){
            Log.i("GyPT", e.toString());
        }
        return downloadedImage;
    }
    public class SingleImageDownloadTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Log.i("GyPT: URL", urls[0]);
            Bitmap outputImage=null;
            URL url = null;
            HttpsURLConnection httpsURLConnection = null;

            try{
                url = new URL(urls[0]);
                httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.connect();
                InputStream downloadedData = httpsURLConnection.getInputStream();
                outputImage = BitmapFactory.decodeStream(downloadedData);

            }catch (Exception e) {
                Log.i("GyPT", e.toString());
            }

            return outputImage;
        }
    }
}