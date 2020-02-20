package com.t.p.gy.myrestaurantapp;


import android.app.Application;
import android.util.Log;

import java.util.ArrayList;


public class DataProcessor extends Application {
    private static final ArrayList<SingleMenuItem> drinks = new ArrayList<SingleMenuItem>();
    final private static ArrayList<SingleMenuItem> foods = new ArrayList<SingleMenuItem>();
    static final private ArrayList<String> spinnerList = new ArrayList<String>();
    private static ArrayList<SingleMenuItem> cart = new ArrayList<SingleMenuItem>();

    //drinks feltoltese adatbazisbol
    //food feltotltese adatbazisbol

    public DataProcessor(){
        if (spinnerList.isEmpty()) {
            spinnerList.add("MENÜ");
            spinnerList.add("Ételek");
            spinnerList.add("Italok");
            spinnerList.add("Cart");

            drinks.add(new SingleMenuItem(1, "Cola", "szensavas", 250, R.drawable.cola));
            drinks.add(new SingleMenuItem(2, "Fanta", "szensavas", 250, R.drawable.fanta));
            drinks.add(new SingleMenuItem(3, "Sprite", "szensavas", 250, R.drawable.sprite));
            drinks.add(new SingleMenuItem(4, "Hell", "energiaital", 300, R.drawable.hell));
            drinks.add(new SingleMenuItem(5, "Birra Moretti", "olasz lotty", 450, R.drawable.birramoretti));
            drinks.add(new SingleMenuItem(9, "Corona", "kukoricasor", 660, R.drawable.corona));
            drinks.add(new SingleMenuItem(11, "Kilkenny", "vegre egy sor", 730, R.drawable.kilkenny));
            drinks.add(new SingleMenuItem(13, "Stella Artois", "egynek jo", 550, R.drawable.stella));
            drinks.add(new SingleMenuItem(14, "Wizard", "ismeretlen", 890, R.drawable.wizard));

            foods.add(new SingleMenuItem(24, "Normál hamburger", "150gr marhahús pogácsa, paradicsom, uborka, sajt", 1600, R.drawable.hamburger));
            foods.add(new SingleMenuItem(25, "Extra hamburger", "300gr marhahús pogácsa, paradicsom, uborka, sajt", 2100, R.drawable.extrahamburger));
            foods.add(new SingleMenuItem(26, "Dupla hamburger", "2x300gr marhahús pogácsa, paradicsom, uborka, sajt", 3300, R.drawable.duplahamburger));
            foods.add(new SingleMenuItem(27, "Döner Kebab", "borjúhús, paradicsom, uborka, káposzta, öntet, házi pitában", 900, R.drawable.donerkebab));
            foods.add(new SingleMenuItem(28, "Dürüm", "borjúhús, paradicsom, uborka, káposzta, öntet, tortillalapban", 900, R.drawable.durum));
            foods.add(new SingleMenuItem(29, "Dürüm2", "csirkehús, paradicsom, uborka, káposzta, öntet, tortillalapban", 900, R.drawable.durum));
            foods.add(new SingleMenuItem(30, "Dürüm3", "borjúhús, hagyma, paradicsom, uborka, káposzta, öntet, tortillalapban", 900, R.drawable.durum));
            foods.add(new SingleMenuItem(31, "Dürüm4", "csirkehús, hagyma, paradicsom, uborka, káposzta, öntet, tortillalapban", 900, R.drawable.durum));
            foods.add(new SingleMenuItem(32, "Kakaós tekercs", "sima, egyszerű kakaóscsiga - yetiknek", 240, R.drawable.csiga));


            cart.add(drinks.get(0));
            cart.get(0).setCartAmount(3);
/*
            cart.add(foods.get(0));
            cart.add(foods.get(3));
            cart.add(foods.get(5));
            cart.add(drinks.get(1));
            cart.add(drinks.get(4));
            cart.add(drinks.get(5));

            cart.get(0).setCartAmount(1);
            cart.get(1).setCartAmount(3);
            cart.get(2).setCartAmount(3);
            cart.get(3).setCartAmount(2);
            cart.get(4).setCartAmount(1);
            cart.get(5).setCartAmount(2);
*/
        }
    }

    public ArrayList<String> getSpinnerList(){
        return spinnerList;
    }

    public ArrayList<SingleMenuItem> getDrinksList(){
        return drinks;
    }

    public ArrayList<SingleMenuItem> getFoodsList(){
        return foods;
    }

    public ArrayList<SingleMenuItem> getCart(){
        return cart;
    }

    public void addToCart(int itemId){
            cart.add(lookForIDinLists(itemId));
    }

    public void modifyCartItem(int itemID, int value){
        Log.i("mylogline", String.valueOf(itemID) + " " + String.valueOf(value));
        Log.i("mylogline:", String.valueOf(lookForIDinCart(itemID)));
        cart.get(cart.indexOf(lookForIDinCart(itemID))).setCartAmount(value);
    }

    public void deleteFromCart(int itemID){
        cart.get(cart.indexOf(lookForIDinCart(itemID))).setCartAmount(0);
        cart.remove(lookForIDinCart(itemID));
    }

    public int getActualCartPrice(){
        int tmp = 0;
        for(SingleMenuItem x : cart){
            tmp+=(x.getPrice()*x.getCartAmount());
        }
        return tmp;
    }

    public int refreshActualOrderPrice(){
        int sum=0;
        for(SingleMenuItem x : foods){
            if (x.getOrderAmount()>0) sum+=x.getOrderAmount()*x.getPrice();
        }
        return sum;
    }

    public void addSelectedItemsToCart() {
        for (SingleMenuItem x : foods) {
            if (x.getOrderAmount() > 0) {
                x.setCartAmount(x.getOrderAmount());
                x.resetOrderAmount();
                addToCart(x.getID());
            }
        }
    }

    private SingleMenuItem lookForIDinLists(int id){
        for (SingleMenuItem x : foods){
            if (x.getID() == id){
                return x;
            }
        }
        for (SingleMenuItem x : drinks){
            if (x.getID() == id){
                return x;
            }
        }
        return null;
    }

    private SingleMenuItem lookForIDinCart(int id){
        for (SingleMenuItem x : cart){
            if (x.getID() == id){
                return x;
            }
        }
        return null;
    }
}