package com.t.p.gy.myrestaurantapp;

import android.app.Application;
import android.util.Log;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import com.t.p.gy.myrestaurantapp.connection.ProductsBackend;
import com.t.p.gy.myrestaurantapp.connection.RetrofitClient;
import com.t.p.gy.myrestaurantapp.data.SingleMenuItem;
import com.t.p.gy.myrestaurantapp.data.Cart;

public class FoodProcessor extends Application {
    final private static ArrayList<SingleMenuItem> foods = new ArrayList<SingleMenuItem>();
    private static ArrayList<SingleMenuItem> cart = new ArrayList<SingleMenuItem>();

    ProductsBackend myAPI;
    //Gson gson = new GsonBuilder().setLenient().create();
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Cart myCart = Cart.getInstance();

    //food feltotltese adatbazisbol

    public FoodProcessor(){
        Log.i("myLog","Foodprocessor start");
        Retrofit retrofit = RetrofitClient.getInstance();

            myAPI = retrofit.create(ProductsBackend.class);
            compositeDisposable.add(myAPI.getFoods()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300) {
                        JsonArray allUsersJsonArray = response.body().getAsJsonArray("food");
                        Log.i("myLog", "Teszt, food input mérete: " + allUsersJsonArray.size());
                        Log.i("myLog", "Teszt, food input elemei: " + allUsersJsonArray.toString());

                        JsonObject jsonamount = allUsersJsonArray.get(0).getAsJsonObject();
                        System.out.println(jsonamount.get("price").toString().replaceAll("\"", "") + " HUF");

                        List<Integer> idList = new ArrayList<Integer>();
                        List<String> nameList = new ArrayList<>();
                        List<String> detailList = new ArrayList<>();
                        List<Integer> priceList = new ArrayList<>();
                        List<String> pictureList = new ArrayList<>();

                        for(int i = 0; i < allUsersJsonArray.size(); i++){
                            idList.add(Integer.parseInt(allUsersJsonArray.get(i).getAsJsonObject().get("id").toString().replaceAll("\"", "")));
                            nameList.add(allUsersJsonArray.get(i).getAsJsonObject().get("name").toString().replaceAll("\"", ""));
                            detailList.add(allUsersJsonArray.get(i).getAsJsonObject().get("detail").toString().replaceAll("\"", ""));
                            priceList.add(Integer.parseInt(allUsersJsonArray.get(i).getAsJsonObject().get("price").toString().replaceAll("\"", "")));
                            pictureList.add(allUsersJsonArray.get(i).getAsJsonObject().get("picture").toString().replaceAll("\"", ""));
                        }

                        Integer[] idArray = new Integer[idList.size()];
                        idArray = idList.toArray(idArray);

                        String[] nameArray = new String[nameList.size()];
                        nameArray = nameList.toArray(nameArray);

                        String[] detailArray = new String[detailList.size()];
                        detailArray = detailList.toArray(detailArray);

                        Integer[] priceArray = new Integer[priceList.size()];
                        priceArray = priceList.toArray(priceArray);

                        String[] pictureArray = new String[pictureList.size()];
                        pictureArray = pictureList.toArray(pictureArray);

                        int id;
                        String name;
                        String detail;
                        int price;
                        int picture;

                        for(int i = 0; i < allUsersJsonArray.size(); i++) {
                            id = idArray[i];
                            name = nameArray[i];
                            detail = detailArray[i];
                            price = priceArray[i];
                            picture = FoodActivity.listView.getResources().getIdentifier(pictureArray[i], "drawable", "com.t.p.gy.myrestaurantapp");

                            foods.add(new SingleMenuItem(id, name, detail, price, picture));
                        }
                    }
                    else {
                        System.out.println(response.code() + " " + response.errorBody().string());
                    }
                }));
        Log.i("myLog","Foodprocessor vége");
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

    private SingleMenuItem lookForIDinLists(int id){
        for (SingleMenuItem x : foods){
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

    //csak a foodban van,a más layout miatt
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
                //x.setCartAmount(x.getOrderAmount());
                x.resetOrderAmount();
                //addToCart(x.getID());
                myCart.addToCart(x);
            }
        }
    }
}


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