package com.t.p.gy.myrestaurantapp.connection;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.t.p.gy.myrestaurantapp.LoginActivity;
import com.t.p.gy.myrestaurantapp.data.DataProcessor;
import com.t.p.gy.myrestaurantapp.data.Order;
import com.t.p.gy.myrestaurantapp.data.SingleProductItem;
import com.t.p.gy.myrestaurantapp.data.User;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class NetworkConnector extends Application {
    private static NetworkConnector networkConnectorInstance;
    private BackendAPI myAPI;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<Order> orderList = new ArrayList<>();
    private String ipAddress = "http://10.0.2.2:3000/";


    private int actualorderid;

    private NetworkConnector(){
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(BackendAPI.class);
        Log.i("myLog", "retrofit ip cím: " + retrofit.baseUrl().toString());
    }

    public static NetworkConnector getInstance(){
        if (networkConnectorInstance == null){
            Log.i("myLog", "új NetworkConnector születőben...");
            networkConnectorInstance = new NetworkConnector();
        }
        Log.i("myLog", "AdminNetworkConnector singleton");
        return networkConnectorInstance;
    }

    //**********************USER*********************//
    public void loginUserV2(SharedPreferences _settings, String _email, String _password){
        compositeDisposable.add(myAPI.login(_email, _password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(response -> {
                        if (response.code() >= 200 && response.code() < 300) {
                            Gson gson = new GsonBuilder().setLenient().create();
                            String token = response.body().get("token").getAsString();
                            User user = new User();
                            JWT jwt = new JWT(token);
                            user.setId(jwt.getClaim("id").asInt());
                            user.setEmail(jwt.getClaim("email").asString());
                            user.setIs_admin(jwt.getClaim("is_admin").asInt());
                            user.setName(jwt.getClaim("name").asString());
                            user.setAddress(jwt.getClaim("address").asString());
                            user.setPhoneNumber(jwt.getClaim("phonenumber").asString());
                            Log.i("myLog", user.toString());
                            _settings.edit().putString("token", token).apply();
                            _settings.edit().putString("user", gson.toJson(user)).apply();
                            /*
                            _settings.edit().putString("email", user.getEmail()).apply();
                            _settings.edit().putString("is_admin", Boolean.toString(user.getIs_admin())).apply();
                            _settings.edit().putString("name", user.getName()).apply();
                            */
                        }
                        else{
                            try{
                                Log.i("myLog", "login hiba: " + response.code() + " " + response.errorBody().string());
                            }catch (Exception e){
                                Log.i("myLog", "login exception: " + e);
                            }
                        }
                    }));
    }
    public void registerUserV2(String _email, String _password, String _username, String _address, String _phonenumber) {
        Log.i("myLog", "Kimenő adatok regisztrációhoz: " +
                        _email+ " "+
                        _password+ " "+
                        _username+ " "+
                        _address+ " "+
                        _phonenumber
        );
        try {
            compositeDisposable.add(myAPI.registration(_email, _password, _username, _address, _phonenumber)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response.code() >= 200 && response.code() < 300) {
                            Gson gson = new GsonBuilder().setLenient().create();
                            Log.i("myLog", "Registration successful");
                        } else {
                            Log.i("myLog", "Registration error: " + response.code() + " " + response.errorBody().string());
                            //Toast.makeText(LoginActivity.this, response.code() + " " + response.errorBody().string(), Toast.LENGTH_LONG).show();
                        }
                    }));
        }catch (Exception e){
            Log.i("myLog", "registerUserV2 error: " + e);
        }
    }
    //**********************----*********************//


    //**********************MENU*********************//
    public List<SingleProductItem> downloadAllProducts(){
        List<SingleProductItem> downloadedDataSet = new ArrayList<>();
        compositeDisposable.add(myAPI.getAllProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300) {
                        JsonArray inputJSONArray = response.body().getAsJsonArray("productlist");
                        for (int i = 0; i < inputJSONArray.size(); i++) {
                            downloadedDataSet.add(new SingleProductItem(
                                    Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("id").toString().replaceAll("\"", "")),
                                    Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("category_id").toString().replaceAll("\"", "")),
                                    inputJSONArray.get(i).getAsJsonObject().get("product_name").toString().replaceAll("\"", ""),
                                    inputJSONArray.get(i).getAsJsonObject().get("detail").toString().replaceAll("\"", ""),
                                    Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("price").toString().replaceAll("\"", "")),
                                    inputJSONArray.get(i).getAsJsonObject().get("picture").toString().replaceAll("\"", "")
                            ));
                        }
                    }
                    else {
                        Log.i("myLog", "AdminNetworkConnector error: " + response.code() + " " + response.errorBody().string());
                    }
                }));
        return downloadedDataSet;
    }
    public List<String> downloadCategories(){
        List<String> catList = new ArrayList<>();
        try {
            compositeDisposable.add(myAPI.getCategories()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response.code() >= 200 && response.code() < 300) {
                            JsonArray inputJSONArray = response.body().getAsJsonArray("category");
                            for (int i = 0; i < inputJSONArray.size(); i++) {
                                catList.add(inputJSONArray.get(i).getAsJsonObject().get("category_name").toString().replaceAll("\"", ""));
                            }
                        } else {
                            Log.i("myLog", "Response error: " + response.code());
                        }
                    }));
        }catch (Exception e){
            Log.i("myLog", "Hiba a letöltéskor: " + e);
        }
        return catList;
    }

    /*
    public void sendOrder(int _userId, SingleProductItem x){
        Log.i("myLog", "termék: " + x);
        //userID - productID - amount
        compositeDisposable.add(myAPI.writeOrder(_userId, x.getID(), x.getCartAmount())
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
*/

    public void initOrder(int _userid, String _note){
        actualorderid = 0;
        Log.i("myLog", "InitOrder running...");
        compositeDisposable.add(myAPI.initOrder(_userid, _note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300) {
                        Log.i("myLog", "InitOrder ok, response: " + response + ", response message: " + response.message());
                        JsonObject inputJSONObject = response.body().getAsJsonObject("result");
                        Log.i("myLog", "resp adata: " + inputJSONObject.toString());
                        int orderId = inputJSONObject.get("insertId").getAsInt();
                        Log.i("myLog", "Új order száma: " + orderId);
                        actualorderid = orderId;
                    } else {
                        Log.i("myLog", "InitOrder ok, response error: " + response.code() + " " + response.errorBody().string());
                    }
        }));
    }

    public void fillOrder(int _productid, int _amount) {
        Log.i("myLog", "fillOrder start, actualorder id értéke: " + actualorderid);
        Log.i("myLog", "fillOrder running...");
        compositeDisposable.add(myAPI.fillOrder(actualorderid, _productid, _amount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300) {
                        Log.i("myLog", "fillOrder ok, response: " + response + ", response message: " + response.message());
                    } else {
                        Log.i("myLog", "fillOrder ok, response error: " + response.code() + " " + response.errorBody().string());
                    }
                }));
    }



    public void initOrder_original(int _userid, String _note){
        compositeDisposable.add(myAPI.initOrder(_userid, _note)
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


    //**********************----*********************//


    //**********************ADMIN*********************//
    public void getItem(final int _id, EditText etCategory, EditText etName, EditText etDescription, EditText etPrice, EditText etImage){
        Log.i("myLog", "getItem adatok: " + _id);
        compositeDisposable.add((Disposable) myAPI.getProductByID(_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.i("myLog", "getItem response body: " + response.body().toString());
                    if (response.code() >= 200 && response.code() < 300) {

                        JsonObject inputJSONObject = response.body().getAsJsonObject("product");
                        etCategory.setText(inputJSONObject.get("category_id").toString().replaceAll("\"", ""));
                        etName.setText(inputJSONObject.get("product_name").toString().replaceAll("\"", ""));
                        etDescription.setText(inputJSONObject.get("detail").toString().replaceAll("\"", ""));
                        etPrice.setText(inputJSONObject.get("price").toString().replaceAll("\"", ""));
                        etImage.setText(inputJSONObject.get("picture").toString().replaceAll("\"", ""));
                    }
                    else {
                        Log.i("myLog", "AdminNetworkConnector error: " + response.code() + " " + response.errorBody().string());
                    }
                }));
    }
    public boolean deleteProduct(final int id){
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
                        }
                    }));
            return true;
        }catch (Exception e){
            Log.i("myLog", "Hiba törléskor: " + e);
            return false;
        }
    }
    public void setOrderToCompleted(int _id){
            compositeDisposable.add(myAPI.finalizeOrder(_id, 1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response.code() >= 200 && response.code() < 300){
                            Log.i("myLog", "setOrderToCompleted response code: " + response.toString());
                        } else {
                            Log.i("myLog", "setOrderToCompleted error, code: " + response.toString());
                        }
                    }));
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
    public void modifyDatabaseItem(final int id, final int category, final String name, final String description, final Integer price,final String picture){
        compositeDisposable.add(myAPI.updateProduct(id, category, name, description, price, picture)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.i("myLog", "modifyDatabaseItem: " + response.body().toString());
                    Log.i("myLog", "modifyDatabaseItem: " + response.message());
                    if (response.code() >= 200 && response.code() < 300){
                        Log.i("myLog", "ChangeProduct response code: " + response.toString());
                        Log.i("myLog", "ChangeProduct response code: " + response.message());
                    } else {
                        Log.i("myLog", "ChangeProduct error, code: " + response.toString());
                    }
                }));
    }



    public List<Order> downloadOrders(){
        if(!orderList.isEmpty()) {
            orderList.clear();
        }
        compositeDisposable.add(myAPI.getAllOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300) {
                        JsonArray inputJSONArray = response.body().getAsJsonArray("orderlist");
                        for (int i = 0; i < inputJSONArray.size(); i++) {
                            int tmpOrderID = Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("id").toString().replaceAll("\"", ""));
                            String tmpCustomerName = inputJSONArray.get(i).getAsJsonObject().get("user_name").toString().replaceAll("\"", "");
                            String tmpCustomerAddress = inputJSONArray.get(i).getAsJsonObject().get("address").toString().replaceAll("\"", "");
                            String tmpCustomerPhoneNumber = inputJSONArray.get(i).getAsJsonObject().get("phonenumber").toString().replaceAll("\"", "");
                            String tmpOrderNote = inputJSONArray.get(i).getAsJsonObject().get("note").toString().replaceAll("\"", "");
                            String tmpOrderTime = inputJSONArray.get(i).getAsJsonObject().get("order_date").toString().replaceAll("\"", "");



                            String tmpItemName = inputJSONArray.get(i).getAsJsonObject().get("product_name").toString().replaceAll("\"", "");
                            int tmpAmount = Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("amount").toString().replaceAll("\"", ""));
                            int tmpPrice = Integer.parseInt(inputJSONArray.get(i).getAsJsonObject().get("price").toString().replaceAll("\"", ""));


                            if (orderList.isEmpty() || !isOrderIDExist(tmpOrderID)) {
                                Log.i("myLog", "Üres lista: " + orderList.isEmpty() + ", vagy még nem létező megrendelésszám: " + Boolean.toString(!isOrderIDExist(tmpOrderID)) + ", listához adás!");
                                orderList.add(new Order(tmpOrderID, tmpCustomerName, tmpCustomerAddress, tmpCustomerPhoneNumber, tmpOrderNote,  tmpOrderTime));
                            }
                            orderList.get(getOrderPosition(tmpOrderID)).addSOIItem(tmpItemName, tmpAmount, tmpPrice);
                        }
                        Log.i("myLog", "Rendelések: " + orderList.toString());
                    }
                    else {
                        Log.i("myLog", "AdminOrders error: " + response.code() + " " + response.errorBody().string());
                    }
                })
        );
        Log.i("myLog", "végleges orderList: " + orderList);
        return orderList;
    }
    private boolean isOrderIDExist(int _id){
        for(Order x : orderList){
            if (x.getOrderID() == _id) return true;
        }
        return false;
    }

    private int getOrderPosition(int _id){
        int i = 0;
        while (orderList.get(i).getOrderID() != _id){
            i++;
        }
        return i;
    }
    //**********************-----*********************//




    public Bitmap getImage(String _dir, String _str){
        Bitmap downloadedImage = null;
        try {
            SingleImageDownloadTaskV2 sIDTask = new SingleImageDownloadTaskV2();
            downloadedImage = sIDTask.execute(_dir, _str).get();
        }catch(Exception e){
            Log.i("myLog", e.toString());
        }
        return downloadedImage;
    }
    public class SingleImageDownloadTaskV2 extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            //Log.i("myLog", "inputString: " + urls[0]);
            Bitmap outputImage = null;
            URL url = null;
            HttpURLConnection httpURLConnection = null;
            StringBuilder tmpString = new StringBuilder(ipAddress + "images/" + urls[0] + "/"+ urls[1] + ".jpg");
            try{
                url = new URL(tmpString.toString());
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream downloadedData = httpURLConnection.getInputStream();
                outputImage = BitmapFactory.decodeStream(downloadedData);
                Log.i("myLog", "image ok: " + tmpString);
            }catch (Exception e) {
            Log.i("myLog", "no image error: " + e.toString());
                String noImageURL = ipAddress + "images/noimage.jpg";
                try {
                    url = new URL(noImageURL);
                    Log.i("myLog", url.toString());
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.connect();
                    InputStream downloadedData = httpURLConnection.getInputStream();
                    outputImage = BitmapFactory.decodeStream(downloadedData);
                }catch (Exception exc){
                    Log.i("myLog", "error: " + exc.toString());
                }
            }
            return outputImage;
        }
    }
    //kell még?
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
}