package com.t.p.gy.myrestaurantapp.data;

import java.util.ArrayList;
import java.util.List;


public class Order {
    int orderID;
    String customerName;
    String customerAddress;
    String customerPhoneNumber;
    String orderNote;
    String orderTime;
    List<ShortOrderInfo> orderItems = new ArrayList<>();
    boolean isCompleted = false;


    public Order(int _orderID,
                 String _customer,
                 String _customerAddress,
                 String _customerPhoneNumber,
                 String _orderNote,
                 String _orderTime) {
        this.orderID = _orderID;
        this.customerName = _customer;
        this.customerAddress = _customerAddress;
        this.customerPhoneNumber= _customerPhoneNumber;
        this.orderNote = _orderNote;
        this.orderTime = _orderTime;
    }

    //getter

    public int getOrderID(){return orderID;}
    public String getCustomerName() {
        return customerName;
    }
    public String getCustomerAddress() {
        return customerAddress;
    }
    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }
    public String getOrderNote() {
        return orderNote;
    }
    public String getOrderTime() {
        return orderTime;
    }
    public int getFinalPrice(){
        int total = 0;
        for (ShortOrderInfo soi : orderItems){
            total += soi.getAmount()*soi.getPrice();
        }
        return total;
    }

    //setter
    public void addSOIItem(String _itemName, int _itemAmount, int _itemPrice){
        orderItems.add(new ShortOrderInfo(_itemName, _itemAmount, _itemPrice));
    }

    @Override
    public String toString() {
        StringBuilder tmpString = new StringBuilder();
        for(ShortOrderInfo soi : orderItems){
            tmpString.append("- " + soi.getName() + ", " + soi.getAmount() + "db. Részösszeg: " + soi.getAmount()*soi.getPrice() + "\n");
        }
        tmpString.append("\n  Ár összesen: " + getFinalPrice() + "Ft");
        return tmpString.toString();
    }

    private class ShortOrderInfo{
        String name;
        int amount;
        int price;


        ShortOrderInfo(String _itemName, int _itemAmount, int _itemPrice){
            this.name = _itemName;
            this.amount = _itemAmount;
            this.price = _itemPrice;
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
