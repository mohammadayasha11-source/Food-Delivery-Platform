package com.mnr.fooddelivery.util;

import com.mnr.fooddelivery.model.*;import java.util.*;

public class DemoData {
    public final List<Customer> customers=new ArrayList<>(); public final List<Restaurant> restaurants=new ArrayList<>(); public final List<DeliveryPartner> partners=new ArrayList<>();
    public DemoData(){
        customers.addAll(List.of(new Customer("C1","Aarav","9000000001"),new Customer("C2","Diya","9000000002"),new Customer("C3","Kabir","9000000003")));
        Restaurant r1=new Restaurant("R1","MNR Biryani House",true);r1.addItem(new MenuItem("M1","Veg Biryani",160));r1.addItem(new MenuItem("M2","Chicken Biryani",220));
        Restaurant r2=new Restaurant("R2","Campus Cafe",true);r2.addItem(new MenuItem("M3","Masala Dosa",90));r2.addItem(new MenuItem("M4","Paneer Wrap",130));
        Restaurant r3=new Restaurant("R3","Sweet Spot",false);r3.addItem(new MenuItem("M5","Gulab Jamun",70));restaurants.addAll(List.of(r1,r2,r3));
        partners.addAll(List.of(new DeliveryPartner("D1","Ravi","9880000001"),new DeliveryPartner("D2","Meena","9880000002"),new DeliveryPartner("D3","Imran","9880000003")));
    }
}
