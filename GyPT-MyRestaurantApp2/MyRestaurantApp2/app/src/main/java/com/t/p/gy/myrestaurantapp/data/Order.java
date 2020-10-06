package com.t.p.gy.myrestaurantapp.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Order {
    String customer;
    String orderTime;
    List<ShortOrderInfo> soiitems = new ArrayList<>();


    public Order(String customer, String orderTime) {
        this.customer = customer;
        this.orderTime = orderTime;
    }

    //getter
    public String getCustomer() {
        return customer;
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

    //setter
    public void addSOIItem(String _itemName, int _itemAmount, int _itemPrice){
        soiitems.add(new ShortOrderInfo(_itemName, _itemAmount, _itemPrice));
    }

    @Override
    public String toString() {
        StringBuilder tmpString = new StringBuilder();
        for(ShortOrderInfo soi : soiitems){
            tmpString.append("- " + soi.getName() + ", " + soi.getAmount() + "db. Részösszeg: " + soi.getAmount()*soi.getPrice() + "\n");
        }
        tmpString.append("Ár összesen: " + getFinalPrice() + "Ft");
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
