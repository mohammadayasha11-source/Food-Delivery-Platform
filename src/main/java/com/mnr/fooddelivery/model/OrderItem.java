package com.mnr.fooddelivery.model;
public record OrderItem(MenuItem item,int quantity) {
    public OrderItem { if(quantity<1) throw new IllegalArgumentException("Quantity must be positive"); }
    public double subtotal(){return item.getPrice()*quantity;}
}
