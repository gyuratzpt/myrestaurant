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

    public Integer getID() {
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



    public void setID(Integer _id) {
        this.id = _id;
    }
    public void setEmail(String _email) {
        this.email = _email;
    }
    public void setIs_admin(int _is_admin) {
        this.is_admin = _is_admin;
    }
    public void setName(String _name) {
        this.name = _name;
    }
    public void setAddress(String _address) {
        this.address = _address;
    }
    public void setPhoneNumber(String _phoneNumber) {
        this.phoneNumber = _phoneNumber;
    }



    public Date getUpdated_at() {
        return updated_at;
    }
    public void setUpdated_at(Date _updated_at) {
        this.updated_at = _updated_at;
    }
}
