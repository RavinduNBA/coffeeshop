package com.netceylon.coffeeshop.User.CoffeeFragments;

public class BobaItem {
    public int imageResId;
    public String coffeeName;
    public String milkName;
    public String price;

    public BobaItem(int imageResId, String coffeeName, String milkName, String price) {
        this.imageResId = imageResId;
        this.coffeeName = coffeeName;
        this.milkName = milkName;
        this.price = price;
    }
}
