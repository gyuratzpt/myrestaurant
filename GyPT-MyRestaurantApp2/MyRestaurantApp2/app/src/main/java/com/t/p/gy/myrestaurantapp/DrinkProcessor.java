package com.t.p.gy.myrestaurantapp;


import android.app.Application;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class DrinkProcessor extends Application {
    private static final ArrayList<SingleMenuItem> drinks = new ArrayList<SingleMenuItem>();
    static final private ArrayList<String> spinnerList = new ArrayList<String>();
    private static ArrayList<SingleMenuItem> cart = new ArrayList<SingleMenuItem>();

    ProductsBackend myAPI;
    Gson gson = new GsonBuilder().setLenient().create();
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    //drinks feltoltese adatbazisbol
    //food feltotltese adatbazisbol

    public DrinkProcessor(){

        Retrofit retrofit = RetrofitClient.getInstance();

        if (spinnerList.isEmpty()) {
            spinnerList.add("MENÜ");
            spinnerList.add("Ételek");
            spinnerList.add("Italok");
            spinnerList.add("Cart");

            myAPI = retrofit.create(ProductsBackend.class);
            compositeDisposable.add(myAPI.getDrinks()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response.code() >= 200 && response.code() < 300) {
                            JsonArray allUsersJsonArray = response.body().getAsJsonArray("drink");
                            JsonObject jsonamount = allUsersJsonArray.get(0).getAsJsonObject();
                            System.out.println(jsonamount.get("price").toString().replaceAll("\"", "") + " HUF");


                            List<Integer> idList = new ArrayList<Integer>();
                            List<String> nameList = new ArrayList<>();
                            List<String> detailList = new ArrayList<>();
                            List<Integer> priceList = new ArrayList<>();

                            for(int i = 0; i < allUsersJsonArray.size(); i++){
                                idList.add(Integer.parseInt(allUsersJsonArray.get(i).getAsJsonObject().get("id").toString().replaceAll("\"", "")));
                                nameList.add(allUsersJsonArray.get(i).getAsJsonObject().get("name").toString().replaceAll("\"", ""));
                                detailList.add(allUsersJsonArray.get(i).getAsJsonObject().get("detail").toString().replaceAll("\"", ""));
                                priceList.add(Integer.parseInt(allUsersJsonArray.get(i).getAsJsonObject().get("price").toString().replaceAll("\"", "")));
                            }


                            Integer[] idArray = new Integer[idList.size()];
                            idArray = idList.toArray(idArray);

                            String[] nameArray = new String[nameList.size()];
                            nameArray = nameList.toArray(nameArray);

                            String[] detailArray = new String[detailList.size()];
                            detailArray = detailList.toArray(detailArray);

                            Integer[] priceArray = new Integer[priceList.size()];
                            priceArray = priceList.toArray(priceArray);

                            int id;
                            String name;
                            String detail;
                            int price;
                            int image;

                            for(int i = 0; i < allUsersJsonArray.size(); i++) {
                                id = idArray[i];
                                name = nameArray[i];
                                detail = detailArray[i];
                                price = priceArray[i];
                                drinks.add(new SingleMenuItem(id, name, detail, price,  R.drawable.cola));
                            }
                        }
                        else {
                            System.out.println(response.code() + " " + response.errorBody().string());
                        }                }));

                        /*
            drinks.add(new SingleMenuItem(2, "Fanta", "szensavas", 250, R.drawable.fanta));
            drinks.add(new SingleMenuItem(3, "Sprite", "szensavas", 250, R.drawable.sprite));
            drinks.add(new SingleMenuItem(4, "Hell", "energiaital", 300, R.drawable.hell));
            drinks.add(new SingleMenuItem(5, "Birra Moretti", "olasz lotty", 450, R.drawable.birramoretti));
            drinks.add(new SingleMenuItem(9, "Corona", "kukoricasor", 660, R.drawable.corona));
            drinks.add(new SingleMenuItem(11, "Kilkenny", "vegre egy sor", 730, R.drawable.kilkenny));
            drinks.add(new SingleMenuItem(13, "Stella Artois", "egynek jo", 550, R.drawable.stella));
            drinks.add(new SingleMenuItem(14, "Wizard", "ismeretlen", 890, R.drawable.wizard));
             */
            /*
            foods.add(new SingleMenuItem(24, "Normál hamburger", "150gr marhahús pogácsa, paradicsom, uborka, sajt", 1600, R.drawable.hamburger));
            foods.add(new SingleMenuItem(25, "Extra hamburger", "300gr marhahús pogácsa, paradicsom, uborka, sajt", 2100, R.drawable.extrahamburger));
            foods.add(new SingleMenuItem(26, "Dupla hamburger", "2x300gr marhahús pogácsa, paradicsom, uborka, sajt", 3300, R.drawable.duplahamburger));
            foods.add(new SingleMenuItem(27, "Döner Kebab", "borjúhús, paradicsom, uborka, káposzta, öntet, házi pitában", 900, R.drawable.donerkebab));
            foods.add(new SingleMenuItem(28, "Dürüm", "borjúhús, paradicsom, uborka, káposzta, öntet, tortillalapban", 900, R.drawable.durum));
            foods.add(new SingleMenuItem(29, "Dürüm2", "csirkehús, paradicsom, uborka, káposzta, öntet, tortillalapban", 900, R.drawable.durum));
            foods.add(new SingleMenuItem(30, "Dürüm3", "borjúhús, hagyma, paradicsom, uborka, káposzta, öntet, tortillalapban", 900, R.drawable.durum));
            foods.add(new SingleMenuItem(31, "Dürüm4", "csirkehús, hagyma, paradicsom, uborka, káposzta, öntet, tortillalapban", 900, R.drawable.durum));
            foods.add(new SingleMenuItem(32, "Kakaós tekercs", "sima, egyszerű kakaóscsiga - yetiknek", 240, R.drawable.csiga));
            */

        }
    }

    public ArrayList<String> getSpinnerList(){
        return spinnerList;
    }

    public ArrayList<SingleMenuItem> getDrinksList(){
        return drinks;
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

    private SingleMenuItem lookForIDinLists(int id){

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