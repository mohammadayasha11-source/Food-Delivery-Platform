package com.mnr.fooddelivery.service;

import com.mnr.fooddelivery.model.*;
import com.mnr.fooddelivery.exception.*;
import com.mnr.fooddelivery.util.OrderStore;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderService {
    private final AtomicInteger sequence=new AtomicInteger(1001);
    private final List<FoodOrder> orders=new ArrayList<>(); private final DeliveryService delivery; private final OrderStore store;
    public OrderService(DeliveryService delivery,OrderStore store){this.delivery=delivery;this.store=store;}
    public synchronized FoodOrder place(Customer customer,Restaurant restaurant,String itemId,int qty,String coupon) throws RestaurantClosedException {
        if(!restaurant.isOpen()) throw new RestaurantClosedException(restaurant.getName()+" is currently closed.");
        MenuItem item=restaurant.findItem(itemId); if(item==null) throw new IllegalArgumentException("Menu item not found");
        double subtotal=item.getPrice()*qty; DiscountStrategy discount="SAVE10".equalsIgnoreCase(coupon)?new PercentageDiscount(10):new DiscountStrategy(){public double apply(double v){return v;} public String description(){return "No coupon";}};
        FoodOrder order=new FoodOrder("FD"+sequence.getAndIncrement(),customer,restaurant,List.of(new OrderItem(item,qty)),discount.apply(subtotal)+49);
        delivery.assign(order); orders.add(order); store.save(order); return order;
    }
    public synchronized List<FoodOrder> getOrders(){return List.copyOf(orders);}
    public synchronized Map<String,Double> salesByRestaurant(){Map<String,Double> m=new LinkedHashMap<>();for(FoodOrder o:orders)m.merge(o.getRestaurant().getName(),o.getTotal(),Double::sum);return m;}
    private static class LocalDateTimeHolder { static java.time.LocalDateTime now(){return java.time.LocalDateTime.now();} }
}



