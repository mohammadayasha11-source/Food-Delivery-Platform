package com.mnr.fooddelivery.ui;

import javax.swing.*;import java.awt.*;import java.nio.file.*;
import com.mnr.fooddelivery.util.*;import com.mnr.fooddelivery.service.*;

public class Main {
    public static void main(String[] args){SwingUtilities.invokeLater(()->{DemoData data=new DemoData();OrderStore store=new OrderStore(Path.of("data","orders.csv"));OrderService service=new OrderService(new DeliveryService(data.partners),store);new FoodDeliveryFrame(data,service).setVisible(true);});}
}
