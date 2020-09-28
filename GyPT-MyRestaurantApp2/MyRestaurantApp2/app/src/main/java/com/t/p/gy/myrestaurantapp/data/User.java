package com.t.p.gy.myrestaurantapp.data;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceManager;

import java.util.Date;

public class User implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(email);
        dest.writeLong(updated_at.getTime());
        dest.writeInt(is_admin);
    }

    public User() {}

    private User(Parcel in) {
        id = in.readInt();
        email = in.readString();
        updated_at = new Date(in.readLong());
        is_admin = in.readInt();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public void printAttributesToConsole(){
        System.out.println("ID: " + id);
        System.out.println("Email: " + email);
        System.out.println("Updated at: " + updated_at);
        System.out.println("Is admin: " + is_admin);
    }

    public static void storeTokenIfChanged(Activity callingActivity, String storedToken, String newToken){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(callingActivity);
        if (!storedToken.equals(newToken)){
            String splitToken = newToken.split(" ")[1];
            settings.edit().putString("token", splitToken).apply();
        }
    }

    private Integer id;
    private String email;
    private Date updated_at;
    private int is_admin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public int getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(int is_admin) {
        this.is_admin = is_admin;
    }

}
