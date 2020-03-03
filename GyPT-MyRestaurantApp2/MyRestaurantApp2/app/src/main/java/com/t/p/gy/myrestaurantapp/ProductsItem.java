package com.t.p.gy.myrestaurantapp;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductsItem implements Parcelable{
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(detail);
        dest.writeInt(price);
    }

    private ProductsItem(Parcel in) {
        id = in.readInt();
        name = in.readString();
        detail = in.readString();
        price = in.readInt();


    }
    public static final Parcelable.Creator<ProductsItem> CREATOR = new Parcelable.Creator<ProductsItem>() {
        public ProductsItem createFromParcel(Parcel in) {
            return new ProductsItem(in);
        }

        public ProductsItem[] newArray(int size) {
            return new ProductsItem[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    private Integer id;
    private String name;
    private String detail;
    private Integer price;


}
