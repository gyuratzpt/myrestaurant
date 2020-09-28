package com.t.p.gy.myrestaurantapp.data;

import android.util.Log;
import android.widget.TextView;

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
    public void addSelectedItemsToCart(){
        for(SingleProductItem x : productList){
            if(x.getOrderAmount()>0){
                x.setCartAmount(x.getOrderAmount());
                cart.add(x);
                x.resetOrderAmount();
                MenuActivity.refreshPriceTextView(0);
            }
        }
    }
}