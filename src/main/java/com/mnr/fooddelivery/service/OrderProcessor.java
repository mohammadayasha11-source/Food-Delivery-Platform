package com.mnr.fooddelivery.service;

import com.mnr.fooddelivery.model.*;

public class OrderProcessor implements Runnable {
    private final FoodOrder order; private final Runnable onUpdate;
    public OrderProcessor(FoodOrder order,Runnable onUpdate){this.order=order;this.onUpdate=onUpdate;}
    public void run(){
        OrderStatus[] path={OrderStatus.PREPARING,OrderStatus.ON_THE_WAY,OrderStatus.DELIVERED};
        try { for(OrderStatus step:path){Thread.sleep(1600);order.setStatus(step);onUpdate.run();} }
        catch(InterruptedException e){Thread.currentThread().interrupt();}
        finally { if(order.getPartner()!=null) order.getPartner().setAvailable(true); }
    }
}
