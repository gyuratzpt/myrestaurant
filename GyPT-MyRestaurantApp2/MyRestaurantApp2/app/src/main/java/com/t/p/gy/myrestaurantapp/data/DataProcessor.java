package com.t.p.gy.myrestaurantapp.data;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.t.p.gy.myrestaurantapp.CartActivity;
import com.t.p.gy.myrestaurantapp.MenuActivity;
import com.t.p.gy.myrestaurantapp.connection.NetworkConnector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataProcessor {
    NetworkConnector anc;
    private static DataProcessor dataProcessorInstance;

    private List<SingleProductItem> productList = new ArrayList<>();
    private List<SingleProductItem> cart = new ArrayList<>();
    private Map<String, Integer> drawableMap;

    //private constructor.
    private DataProcessor(){
        anc = NetworkConnector.getInstance();
        productList = anc.getDownloadedList();
        //Drawable map feltöltése???
        //drawableMap.put("birramoretti", getApplicationContext().getResources().getIdentifier("birramoretti","drawable", getPackageName()));

        //rendezést ráér
    }
    public static DataProcessor getInstance(){
        if (dataProcessorInstance == null){ //if there is no instance available... create new one
            dataProcessorInstance = new DataProcessor();
        }
        Log.i("myLog", "Cart singleton");
        return dataProcessorInstance;
    }

    //kosár funkciók
    public void addToCart(SingleProductItem inputSPI){
        cart.add(inputSPI);
        Log.i("myLog", "Cart új tartalma:" + cart.toString());
        for(SingleProductItem x : cart){
                Log.i("myLog", x.getName() + " "+ x.getCartAmount());
            }
    }
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


    //menu funkciók
    public Map getDrawableMap(){
        return drawableMap;
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
    private void modifyCartItem(){

    }
}