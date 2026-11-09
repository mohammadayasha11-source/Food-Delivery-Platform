package com.mnr.fooddelivery.service;

import com.mnr.fooddelivery.model.*;
import java.util.*;

public class DeliveryService {
    private final List<DeliveryPartner> partners;
    public DeliveryService(List<DeliveryPartner> partners){this.partners=partners;}
    public synchronized Optional<DeliveryPartner> assign(FoodOrder order){
        for(DeliveryPartner p:partners) if(p.isAvailable()){p.setAvailable(false);order.setPartner(p);return Optional.of(p);} return Optional.empty();
    }
}
