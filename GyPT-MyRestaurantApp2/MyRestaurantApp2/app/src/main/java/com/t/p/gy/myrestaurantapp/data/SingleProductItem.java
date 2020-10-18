package com.t.p.gy.myrestaurantapp.data;

import android.util.Log;

public class SingleProductItem {
    private static final int NO_IMAGE_PROVIDED = -1;

    //private final String mCategory;
    private final int mIDNumber;
    private int mCategory;
    private String mName;
    private String mDetail;
    private int mPrice;
    private int mImageResourceID = NO_IMAGE_PROVIDED;

    private int mOrderAmount; //listview miatt, különben scrollozáskor elfeljtheti a korábbi értéket
    private int mCartAmount;

    public SingleProductItem(int _id, int _cat, String _name, String _detail, int _price, int _imageResourceID) {
        mIDNumber = _id;
        mCategory = _cat;
        mName = _name;
        mDetail = _detail;
        mPrice = _price;
        mImageResourceID = _imageResourceID;
        mOrderAmount = 0;
        mCartAmount = 0;
    }

    //GETTER-ek
    public int getCategory() {
        return mCategory;
    }
    public String getName() {
        return mName;
    }
    public String getDetail() {
        return mDetail;
    }
    public boolean hasImage() {
        return mImageResourceID != NO_IMAGE_PROVIDED;
    }
    public int getImageResourceID() {
        return mImageResourceID;
    }
    public int getID() {
        return mIDNumber;
    }

    public int getPrice() {
        return mPrice;
    }
    public int getOrderAmount(){
        return mOrderAmount;
    }
    public int getCartAmount(){
        return mCartAmount;
    }


    //SETTER-ek

    public void setCategory(int mCategory) {
        this.mCategory = mCategory;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setDetail(String mDetail) {
        this.mDetail = mDetail;
    }

    public void setPrice(int mPrice) {
        this.mPrice = mPrice;
    }

    public void setOrderAmount(boolean bool){
        Log.i("Bool is", String.valueOf(bool));
        if (bool) {
            Log.i("Előtte:", this.mName + ": " + String.valueOf(this.mOrderAmount)+" Cart: " + String.valueOf(this.mCartAmount));
            mOrderAmount++;
            Log.i("Utána:", this.mName + ": " + String.valueOf(this.mOrderAmount)+" Cart: " + String.valueOf(this.mCartAmount));
        }
        else if (!bool && mOrderAmount>0){
            Log.i("Előtte:", this.mName + ": " + String.valueOf(this.mOrderAmount)+" Cart: " + String.valueOf(this.mCartAmount));
            mOrderAmount--;
            Log.i("Utána:", this.mName + ": " + String.valueOf(this.mOrderAmount)+" Cart: " + String.valueOf(this.mCartAmount));
        }
    }
    public void setCartAmount(int x){if (x>=0)this.mCartAmount=x;}

    //egyéb
    public void resetOrderAmount(){
        mOrderAmount=0;
    }



}

