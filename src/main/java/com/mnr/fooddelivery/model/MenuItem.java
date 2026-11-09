package com.mnr.fooddelivery.model;

public class MenuItem {
    private final String id, name; private final double price;
    public MenuItem(String id, String name, double price) {
        if (price < 0) throw new IllegalArgumentException("Price cannot be negative");
        this.id=id; this.name=name; this.price=price;
    }
    public String getId(){return id;} public String getName(){return name;} public double getPrice(){return price;}
}
