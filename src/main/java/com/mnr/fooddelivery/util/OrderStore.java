package com.mnr.fooddelivery.util;

import com.mnr.fooddelivery.model.FoodOrder;
import java.io.*;import java.nio.file.*;

public class OrderStore {
    private final Path file;
    public OrderStore(Path file){this.file=file;}
    public synchronized void save(FoodOrder o){
        try{Files.createDirectories(file.getParent());boolean header=!Files.exists(file);try(BufferedWriter w=Files.newBufferedWriter(file,StandardOpenOption.CREATE,StandardOpenOption.APPEND)){if(header)w.write("orderId,customer,restaurant,total,status,createdAt\n");w.write(String.join(",",safe(o.getId()),safe(o.getCustomer().getName()),safe(o.getRestaurant().getName()),String.format(java.util.Locale.US,"%.2f",o.getTotal()),o.getStatus().name(),o.getCreatedAt().toString()));w.newLine();}}
        catch(IOException e){throw new UncheckedIOException("Could not save order history",e);}
    }
    private String safe(String s){return s.replace(","," ");}
}
