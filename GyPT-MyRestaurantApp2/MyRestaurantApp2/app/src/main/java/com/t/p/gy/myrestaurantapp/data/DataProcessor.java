package com.t.p.gy.myrestaurantapp.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.t.p.gy.myrestaurantapp.CartActivity;
import com.t.p.gy.myrestaurantapp.LoginActivity;
import com.t.p.gy.myrestaurantapp.MainActivity;
import com.t.p.gy.myrestaurantapp.R;
import com.t.p.gy.myrestaurantapp.connection.NetworkConnector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DataProcessor {
    private static DataProcessor dataProcessorInstance;

    //user
    private User user;
    Gson gson = new GsonBuilder().setLenient().create();
    SharedPreferences settings;

    //listák
    private List<SingleProductItem> cart = new ArrayList<>();
    private List<SingleProductItem> productList;
    private List<String> categoriesList;

    //network
    private NetworkConnector netConn;

    private DataProcessor(){
        netConn = NetworkConnector.getInstance();
        productList = netConn.downloadAllProducts();
        categoriesList = netConn.downloadCategories();
        //categoriesList.add(0,"Összes tétel");
    }
    public static DataProcessor getInstance(){
        if (dataProcessorInstance == null){
            Log.i("myLog", "új DataProcessor születőben...");
            dataProcessorInstance = new DataProcessor();
        }
        return dataProcessorInstance;
    }

    //User
    public void initSP(Context _context){
        settings = PreferenceManager.getDefaultSharedPreferences(_context); //kell külön a context miatt!(?)
    }
    public boolean checkUserToken(){
        final String token = settings.getString("token", "not found");
        Log.i("myLog", "usertoken: " + token);
        final String userData = settings.getString("user", "not found");
        Log.i("myLog", "userData: " + userData);
        if(!userData.equals("not found")) user = gson.fromJson(settings.getString("user","{}"), User.class);
        return !token.equals("not found") || !userData.equals("not found");
    }
    public boolean isUserAdmin(){
        if(user.getIs_admin()==1) return true;
        else return false;
    }

    public String getUserName(){
        return user.getName();
    }
    public String getUserAddress(){
        return user.getAddress();
    }
    public String getUserPhoneNumber(){
        return user.getPhoneNumber();
    }

    public void loginUserV2(String _email, String _password){
        try {
            netConn.loginUserV2(settings, _email, _password );
        }catch (Exception e){
            Log.i("myLog", "login hiba: " + e);
            Log.i("myLog", settings.getString("user", "üres") + " " + e);
        }
    }
    public void registerUserV2(String _email, String _password, String _name, String _address, String _phone){
        try {
            netConn.registerUserV2(_email, _password, _name, _address, _phone);
        }catch (Exception e){
            Log.i("myLog", "registerUserV2 error, regisztráció hiba: " + e);
            Log.i("myLog", "registerUserV2 error, user data: " + settings.getString("user", "üres"));
        }
    }
    public void logout(){
        settings.edit().remove("token").apply();
        settings.edit().remove("user").apply();
        cart.clear();
    }











    //közös funkciók
/*
    public static Map getDrawableMap(){
        return drawableMap;
    }
    public void setDrawableMap(Map _drawableMap){
        this.drawableMap = _drawableMap;
    }
*/



    //admin funkciók
    //admin/database
    public void addItemToDatabase(int _category, String _name, String _desc, int _price, String _image){
        Log.i("myLog", "addItemToDatabase running...");
        netConn.createNewItem(_category, _name,  _desc,  _price,  _image);
    }
    public void getItemFromDatabase(int _id, EditText etCategory, EditText etName, EditText etDescription, EditText etPrice, EditText etImage){
        Log.i("myLog", "getItemFromDatabase running...");
        netConn.getItem(_id, etCategory, etName, etDescription, etPrice, etImage);
    }
    public void modifyDatabaseItem(int _id, int _category, String _name, String _desc, int _price, String _image){
        Log.i("myLog", "modifyDatabaseItem running...");
        netConn.modifyDatabaseItem(_id, _category, _name,  _desc,  _price,  _image);
    }
    public void deleteProductItem(int _prodId){
        netConn.deleteProduct(_prodId);
    }
    //admin/list
    public void addItemToList(SingleProductItem _spi){
        productList.add(_spi);
    }

    //admin/order
    public List<Order> getOrders_list(){
        return netConn.downloadOrders();
    }
    public void setOrderToCompleted(int _id){
        netConn.setOrderToCompleted(_id);
    }

    //user funkciók

    public List<SingleProductItem> getProductList(int _cat){
        if(_cat==0){
            return productList;
        }
        else{
            List<SingleProductItem> tmpList = new ArrayList<>();
            for(SingleProductItem x : productList){
                if(x.getCategory()==_cat){
                    tmpList.add(x);
                }
            }
            return tmpList;
        }

    }
    public List<String> getCategoriesList(){
        return categoriesList;
    }
    public List<String> getCustomizedCategoryList(String _pos0){
        List<String> tmpList = new ArrayList<>();
        tmpList.addAll(categoriesList);
        tmpList.add(0,_pos0);
        return tmpList;
    }
    public List<SingleProductItem> getFilteredProducts(int _catID){
        List<SingleProductItem> tmpList = new ArrayList<>();
        for(SingleProductItem x : productList){
         if(x.getCategory() == _catID) tmpList.add(x);
        }
        return tmpList;
    }
    public int getActualOrderPrice(){
        int sum = 0;
        for(SingleProductItem x : productList){
            Log.i("myLog", x.getName() + " "+ x.getCartAmount());
            sum += x.getOrderAmount()*x.getPrice();
        }
        return sum;
    }

    //kosár funkciók
    public List<SingleProductItem> getCart(){
        return cart;
    }
    public int getCartFullPrice(){
        int total = 0;
        for(SingleProductItem x : cart){
            Log.i("myLog", x.getName() + " "+ x.getCartAmount());
            total += x.getCartAmount()*x.getPrice();
        }
        return total;
    }
    public boolean addSelectedItemsToCart(Context _context){
        boolean notEmpty = false;
        for(SingleProductItem x : productList){
            if(x.getOrderAmount()>0){
                notEmpty = true;
                if(!cart.contains(x)) {
                    x.setCartAmount(x.getOrderAmount());
                    cart.add(x);
                    x.resetOrderAmount();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(_context);
                    builder.setMessage("Már van " + x.getCartAmount()+"db a kosárban ebből a tételből!\nA jelenleg megadott mennyiség: " + x.getOrderAmount()+"db.");
                    //.setCancelable(false)
                    builder.setPositiveButton("Felülír", (dialog, id) -> {
                        x.setCartAmount(x.getOrderAmount());
                        Toast.makeText(_context,"Tétel felülírva!", Toast.LENGTH_SHORT).show();
                        x.resetOrderAmount();
                    });
                    //bal oldali
                    builder.setNegativeButton("Hozzáad", (dialog, id) -> {
                        x.setCartAmount(x.getOrderAmount()+x.getCartAmount());
                        Toast.makeText(_context,"Tétel megnövelve!", Toast.LENGTH_SHORT).show();
                        x.resetOrderAmount();
                    });
                    //Creating dialog box
                    AlertDialog alert = builder.create();

                    alert.setTitle(x.getName());
                    alert.show();
                }
            }
        }
        return notEmpty;
    }
    public void deleteItemFromCart(SingleProductItem inputSPI, TextView _tv){
        cart.remove(inputSPI);
        refreshCartFinalPrice(_tv);
    }
    public void refreshCartFinalPrice(TextView _tv){
        _tv.setText(String.valueOf(getCartFullPrice())+"Ft");
    }
    public void refreshCartActivityView(){
        CartActivity.refreshPriceTextView(getCartFullPrice());
    }

    //order
    public void initOrder(String _note) {
        netConn.initOrder(user.getId(), _note);
    }
    public void fillOrder() {
        for(SingleProductItem x : cart) {
            netConn.fillOrder(x.getID(), x.getCartAmount());
        }
    }


    //BRIDGE metódusok NetworkConnector felé
    //közös
    public Bitmap getImage(String _dir, String _str){
        Bitmap bmp = netConn.getImage(_dir, _str);
        return bmp;
    }
    //LoginActivity




    public void initLogoutOption(Context _context){
        AlertDialog.Builder builder = new AlertDialog.Builder(_context);
        builder.setPositiveButton("Igen", (dialog, id) -> {
            logout();
            _context.startActivity(new Intent(_context, LoginActivity.class));
            Toast.makeText(_context, "Kilépés", Toast.LENGTH_LONG).show();
        });
        builder.setNegativeButton("Mégsem", (dialog, id) -> {
        });
        AlertDialog alert = builder.create();
        alert.setTitle("Biztos kilépsz az alkalmazásból?");
        alert.show();
    }
}