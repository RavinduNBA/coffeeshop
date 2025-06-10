package com.netceylon.coffeeshop.User.CoffeeFragments;

import java.io.Serializable;

public class Coffee implements Serializable {
    private int imageResId;
    private String name;
    private String topping;
    private String milkType;
    private String description;
    private String price;

    public Coffee(int imageResId, String name, String topping, String milkType, String description, String price) {
        this.imageResId = imageResId;
        this.name = name;
        this.topping = topping;
        this.milkType = milkType;
        this.description = description;
        this.price = price;
    }

    public int getImageResId() { return imageResId; }
    public String getName() { return name; }
    public String getTopping() { return topping; }
    public String getMilkType() { return milkType; }
    public String getDescription() { return description; }
    public String getPrice() { return price; }
}
