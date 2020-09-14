package com.t.p.gy.myrestaurantapp.data;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.t.p.gy.myrestaurantapp.CartActivity;
import com.t.p.gy.myrestaurantapp.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private static Cart cartInstance;
    private List<SingleMenuItem> cart = new ArrayList<SingleMenuItem>();

    private Cart(){
    }  //private constructor.

    public static Cart getInstance(){
        if (cartInstance == null){ //if there is no instance available... create new one
            cartInstance = new Cart();
        }
        Log.i("myLog", "Cart singleton");
        return cartInstance;
    }

    public void addToCart(SingleMenuItem inputSLI){
        cart.add(inputSLI);
        Log.i("myLog", "Cart új tartalma:" + cart.toString());
        for(SingleMenuItem x : cart){
                Log.i("myLog", x.getName() + " "+ x.getCartAmount());
            }
    }

    public List<SingleMenuItem> getCart(){
            return cart;
        }

    public int getCartFullPrice(){
            int total = 0;
            for(SingleMenuItem x : cart){
                Log.i("myLog", x.getName() + " "+ x.getCartAmount());
                total += x.getCartAmount()*x.getPrice();
            }
            return total;
        }

    public void deleteItemFromCart(SingleMenuItem inputSMI, TextView _tv){
        cart.remove(inputSMI);
        refreshCartFinalPrice(_tv);
    }

    public void refreshCartFinalPrice(TextView _tv){
        _tv.setText(String.valueOf(getCartFullPrice())+"Ft");
    }

    public void refreshCartActivityView(){
        CartActivity.refreshPriceTextView(getCartFullPrice());
    }
}