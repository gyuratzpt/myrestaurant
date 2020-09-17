package com.t.p.gy.myrestaurantapp.data;

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
    private String mCategory;


    public SingleMenuItem(int _id, String _name, String _detail, int _price, int _imageResourceID, String _cat) {
        mIDNumber = _id;
        mName = _name;
        mDetail = _detail;
        mPrice = _price;
        mImageResourceID = _imageResourceID;
        mOrderAmount = 0;
        mCartAmount = 0;
        mCategory = _cat;
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

    public String getCategory() {
        return mCategory;
    }

}

