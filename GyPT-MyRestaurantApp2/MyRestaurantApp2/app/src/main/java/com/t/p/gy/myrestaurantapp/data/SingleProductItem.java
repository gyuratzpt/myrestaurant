package com.t.p.gy.myrestaurantapp.data;

import android.util.Log;

public class SingleProductItem {

    private final Integer idNumber;
    private int category;
    private String name;
    private String detail;
    private int price;
    private String imageName;

    private int orderAmount; //listview miatt, különben scrollozáskor elfeljtheti a korábbi értéket
    private int cartAmount;

    public SingleProductItem(Integer _id, int _cat, String _name, String _detail, int _price, String _imageName) {
        idNumber = _id;
        category = _cat;
        name = _name;
        detail = _detail;
        price = _price;
        imageName = _imageName;
        orderAmount = 0;
        cartAmount = 0;
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
        return orderAmount;
    }
    public int getCartAmount(){
        return cartAmount;
    }


    //SETTER-ek

    public void setCategory(int _category) {
        this.category = _category;
    }
    public void setName(String _name) {
        this.name = _name;
    }
    public void setDetail(String _detail) {
        this.detail = _detail;
    }
    public void setPrice(int _price) {
        this.price = _price;
    }
    public void setImageName(String _imgName) {this.imageName = _imgName;}
    public void setOrderAmount(boolean bool){
        Log.i("Bool is", String.valueOf(bool));
        if (bool) {
            Log.i("Előtte:", this.name + ": " + String.valueOf(this.orderAmount)+" Cart: " + String.valueOf(this.cartAmount));
            orderAmount++;
            Log.i("Utána:", this.name + ": " + String.valueOf(this.orderAmount)+" Cart: " + String.valueOf(this.cartAmount));
        }
        else if (!bool && orderAmount >0){
            Log.i("Előtte:", this.name + ": " + String.valueOf(this.orderAmount)+" Cart: " + String.valueOf(this.cartAmount));
            orderAmount--;
            Log.i("Utána:", this.name + ": " + String.valueOf(this.orderAmount)+" Cart: " + String.valueOf(this.cartAmount));
        }
    }
    public void setCartAmount(int x){if (x>=0)this.cartAmount =x;}
    //egyéb
    public void resetOrderAmount(){
        orderAmount =0;
    }

}

