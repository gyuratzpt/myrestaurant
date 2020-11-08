package com.t.p.gy.myrestaurantapp.data;

import android.util.Log;

public class SingleProductItem {

    private final Integer idNumber;
    private int category;
    private String name;
    private String detail;
    private int price;
    private String imageName;

    private int mOrderAmount; //listview miatt, különben scrollozáskor elfeljtheti a korábbi értéket
    private int mCartAmount;

    public SingleProductItem(Integer _id, int _cat, String _name, String _detail, int _price, String _imageName) {
        idNumber = _id;
        category = _cat;
        name = _name;
        detail = _detail;
        price = _price;
        imageName = _imageName;
        mOrderAmount = 0;
        mCartAmount = 0;
    }

    //GETTER-ek
    public Integer getCategory() {
        return category;
    }
    public String getName() {
        return name;
    }
    public String getDetail() {
        return detail;
    }
    public String getImageName(){return imageName;}
    public int getID() {
        return idNumber;
    }


    public int getPrice() {
        return price;
    }
    public int getOrderAmount(){
        return mOrderAmount;
    }
    public int getCartAmount(){
        return mCartAmount;
    }


    //SETTER-ek

    public void setCategory(int mCategory) {
        this.category = mCategory;
    }
    public void setName(String mName) {
        this.name = mName;
    }
    public void setDetail(String mDetail) {
        this.detail = mDetail;
    }
    public void setPrice(int mPrice) {
        this.price = mPrice;
    }
    public void setImageName(String mName) {this.imageName = mName;}
    public void setOrderAmount(boolean bool){
        Log.i("Bool is", String.valueOf(bool));
        if (bool) {
            Log.i("Előtte:", this.name + ": " + String.valueOf(this.mOrderAmount)+" Cart: " + String.valueOf(this.mCartAmount));
            mOrderAmount++;
            Log.i("Utána:", this.name + ": " + String.valueOf(this.mOrderAmount)+" Cart: " + String.valueOf(this.mCartAmount));
        }
        else if (!bool && mOrderAmount>0){
            Log.i("Előtte:", this.name + ": " + String.valueOf(this.mOrderAmount)+" Cart: " + String.valueOf(this.mCartAmount));
            mOrderAmount--;
            Log.i("Utána:", this.name + ": " + String.valueOf(this.mOrderAmount)+" Cart: " + String.valueOf(this.mCartAmount));
        }
    }
    public void setCartAmount(int x){if (x>=0)this.mCartAmount=x;}

    //egyéb
    public void resetOrderAmount(){
        mOrderAmount=0;
    }



}

