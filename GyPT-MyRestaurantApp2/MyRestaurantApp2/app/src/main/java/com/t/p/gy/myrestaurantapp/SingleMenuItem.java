package com.t.p.gy.myrestaurantapp;

import android.util.Log;

public class SingleMenuItem {


    private static final int NO_IMAGE_PROVIDED = -1;
    private int mImageResourceID = NO_IMAGE_PROVIDED;
    private final int mIDNumber;
    private String mName;
    private String mDetail;
    private int mPrice;
    private int mOrderAmount; //listview miatt, különben scrollozáskor elfeljtheti a korábbi értéket
    private int mCartAmount;


    public SingleMenuItem(int id, String name, String detail, int price, int imageResourceID) {
        mIDNumber = id;
        mName = name;
        mDetail = detail;
        mPrice = price;
        mImageResourceID = imageResourceID;
        mOrderAmount = 0;
        mCartAmount = 0;
    }

    public int getImageResourceID() {
        return mImageResourceID;
    }

    public int getID() {
        return mIDNumber;
    }

    public String getName() {
        return mName;
    }

    public String getDetail() {
        return mDetail;
    }

    public int getPrice() {
        return mPrice;
    }

    public int getOrderAmount(){
        return mOrderAmount;
    }

    public void setOrderAmount(boolean bool){
        Log.i("Bool is", String.valueOf(bool));
        if (bool) {
            Log.i("Infók:", this.mName + ": " + String.valueOf(this.mOrderAmount)+" Cart: " + String.valueOf(this.mCartAmount));
            mOrderAmount++;
            Log.i("Infók:", this.mName + ": " + String.valueOf(this.mOrderAmount)+" Cart: " + String.valueOf(this.mCartAmount));
        }
        else if (!bool && mOrderAmount>0){
            Log.i("Infók:", this.mName + ": " + String.valueOf(this.mOrderAmount)+" Cart: " + String.valueOf(this.mCartAmount));
            mOrderAmount--;
            Log.i("Infók:", this.mName + ": " + String.valueOf(this.mOrderAmount)+" Cart: " + String.valueOf(this.mCartAmount));
        }
    }

    public void resetOrderAmount(){
        mOrderAmount=0;
    }

    public int getCartAmount(){
        return mCartAmount;
    }

    public void setCartAmount(int x){if (x>=0)this.mCartAmount=x;}

    public boolean hasImage() {
        return mImageResourceID != NO_IMAGE_PROVIDED;
    }

    }