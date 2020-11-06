package com.t.p.gy.myrestaurantapp.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Order {
    String customer;
    String customerAddress;
    String customerPhoneNumber;
    String orderTime;
    List<ShortOrderInfo> soiitems = new ArrayList<>();


    public Order(String _customer, String _customerAddress, String _customerPhoneNumber, String _orderTime) {
        this.customer = _customer;
        this.customerAddress = _customerAddress;
        this.customerPhoneNumber= _customerPhoneNumber;
        this.orderTime = _orderTime;
    }

    //getter

    public String getCustomer() {
        return customer;
    }
    public String getCustomerAddress() {
        return customerAddress;
    }
    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }
    public String getOrderTime() {
        return orderTime;
    }
    public int getFinalPrice(){
        int total = 0;
        for (ShortOrderInfo soi : soiitems){
            total += soi.getAmount()*soi.getPrice();
        }
        return total;
    }
    public List<Integer> getOrderIDs(){
        List<Integer> ordersList = new ArrayList<>();
        for (ShortOrderInfo soi : soiitems){
            ordersList.add(soi.getOrderid());
        }
        return ordersList;
    }
    //setter
    public void addSOIItem(int _orderid, String _itemName, int _itemAmount, int _itemPrice){
        soiitems.add(new ShortOrderInfo(_orderid, _itemName, _itemAmount, _itemPrice));
    }

    @Override
    public String toString() {
        StringBuilder tmpString = new StringBuilder();
        for(ShortOrderInfo soi : soiitems){
            tmpString.append("- " + soi.getOrderid() + ": "+ soi.getName() + ", " + soi.getAmount() + "db. Részösszeg: " + soi.getAmount()*soi.getPrice() + "\n");
        }
        tmpString.append("Ár összesen: " + getFinalPrice() + "Ft");
        return tmpString.toString();
    }

    private class ShortOrderInfo{
        int orderid;
        String name;
        int amount;
        int price;
        boolean isCompleted;

        ShortOrderInfo(int _orderid, String _itemName, int _itemAmount, int _itemPrice){
            this.orderid = _orderid;
            this.name = _itemName;
            this.amount = _itemAmount;
            this.price = _itemPrice;
            this.isCompleted = false;
        }

        public int getOrderid() {
            return orderid;
        }
        public String getName() {
            return name;
        }
        public int getAmount() {
            return amount;
        }
        public int getPrice() {
            return price;
        }
    }
}
