package com.t.p.gy.myrestaurantapp.data;
import java.util.Date;

public class User{

    public User(){}

    private Integer id;
    private String email;
    private int is_admin;
    private String name;
    private String address;
    private String phoneNumber;
    private Date updated_at;

    public Integer getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public int getIs_admin() {
        return is_admin;
    }
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }



    public void setId(Integer id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setIs_admin(int is_admin) {
        this.is_admin = is_admin;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    public Date getUpdated_at() {
        return updated_at;
    }
    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
