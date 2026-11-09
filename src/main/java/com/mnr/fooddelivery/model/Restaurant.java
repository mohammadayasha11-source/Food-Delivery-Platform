package com.mnr.fooddelivery.model;

import java.util.*;

public class Restaurant {
    private final String id, name; private boolean open;
    private final Map<String,MenuItem> menu = new LinkedHashMap<>();
    public Restaurant(String id,String name,boolean open){this.id=id;this.name=name;this.open=open;}
    public void addItem(MenuItem item){menu.put(item.getId(),item);}
    public Collection<MenuItem> getMenu(){return Collections.unmodifiableCollection(menu.values());}
    public MenuItem findItem(String id){return menu.get(id);}
    public String getId(){return id;} public String getName(){return name;}
    public boolean isOpen(){return open;} public void setOpen(boolean open){this.open=open;}
}
