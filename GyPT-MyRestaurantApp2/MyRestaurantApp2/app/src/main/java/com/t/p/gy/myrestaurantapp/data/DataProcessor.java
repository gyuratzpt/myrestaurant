package com.t.p.gy.myrestaurantapp.data;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.t.p.gy.myrestaurantapp.CartActivity;
import com.t.p.gy.myrestaurantapp.connection.NetworkConnector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataProcessor {
    private NetworkConnector netConn;
    private static DataProcessor dataProcessorInstance;

    private List<SingleProductItem> productList;
    private List<SingleProductItem> cart = new ArrayList<>();
    private static Map<String, Integer> drawableMap = new HashMap<>();

    //private constructor.
    private DataProcessor(){
        netConn = NetworkConnector.getInstance();
        productList = netConn.getDownloadedList();
        //Drawable map feltöltése???
        //drawableMap.put("birramoretti", getApplicationContext().getResources().getIdentifier("birramoretti","drawable", getPackageName()));

        //rendezést ráér
    }
    public static DataProcessor getInstance(){
        if (dataProcessorInstance == null){ //if there is no instance available... create new one
            dataProcessorInstance = new DataProcessor();
        }
        Log.i("myLog", "DataProcessor singleton");
        return dataProcessorInstance;
    }



    //admin funkciók
    /*
    public void getOrders(TextView _tv){
        netConn.downloadOrders(_tv);
    }
    */

    public List<Order> getOrders_list(){
        return netConn.downloadOrders();
    }

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

    //user funkciók
    public static Map getDrawableMap(){
        return drawableMap;
    }
    public void setDrawableMap(Map _drawableMap){
        this.drawableMap = _drawableMap;
    }

    public List<SingleProductItem> getProductList(){
        return productList;
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

}