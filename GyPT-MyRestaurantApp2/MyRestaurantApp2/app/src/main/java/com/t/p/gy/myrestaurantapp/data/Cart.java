package com.t.p.gy.myrestaurantapp.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Cart {

        private static Cart cartInstance;
        private List<SingleMenuItem> cart = new ArrayList<SingleMenuItem>();

        private Cart(){}  //private constructor.

        public static Cart getInstance(){
            if (cartInstance == null){ //if there is no instance available... create new one
                cartInstance = new Cart();
            }
            Log.i("myLog", "Cart singleton");
            return cartInstance;
        }

        public void addToCart(SingleMenuItem inputSLI){
                cart.add(inputSLI);
            Log.i("myLog", "Cart tartalma:" + cart.toString());
            for(SingleMenuItem x : cart){
                Log.i("myLog", x.getName() + " "+ x.getCartAmount());
            }
        }

        public List<SingleMenuItem> getCart(){
            return cart;
        }
    }