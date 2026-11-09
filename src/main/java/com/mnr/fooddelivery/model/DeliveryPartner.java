package com.mnr.fooddelivery.model;

public class DeliveryPartner extends User {
    private boolean available = true;
    public DeliveryPartner(String id, String name, String phone) { super(id, name, phone); }
    public synchronized boolean isAvailable() { return available; }
    public synchronized void setAvailable(boolean available) { this.available = available; }
}
