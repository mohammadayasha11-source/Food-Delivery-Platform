package com.mnr.fooddelivery.model;

import java.time.LocalDateTime;
import java.util.*;

public class FoodOrder {
    private final String id; private final Customer customer; private final Restaurant restaurant;
    private final List<OrderItem> items; private final double total; private final LocalDateTime createdAt;
    private volatile OrderStatus status=OrderStatus.PLACED; private DeliveryPartner partner;
    public FoodOrder(String id,Customer customer,Restaurant restaurant,List<OrderItem> items,double total){
        this.id=id;this.customer=customer;this.restaurant=restaurant;this.items=List.copyOf(items);this.total=total;this.createdAt=LocalDateTime.now();
    }
    public String getId(){return id;} public Customer getCustomer(){return customer;} public Restaurant getRestaurant(){return restaurant;}
    public List<OrderItem> getItems(){return items;} public double getTotal(){return total;} public LocalDateTime getCreatedAt(){return createdAt;}
    public OrderStatus getStatus(){return status;} public synchronized void setStatus(OrderStatus s){status=s;}
    public DeliveryPartner getPartner(){return partner;} public synchronized void setPartner(DeliveryPartner p){partner=p;}
}
