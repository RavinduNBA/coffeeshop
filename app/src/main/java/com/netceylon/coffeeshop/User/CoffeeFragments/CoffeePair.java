package com.netceylon.coffeeshop.User.CoffeeFragments;

import java.io.Serializable;

public class CoffeePair implements Serializable {
    private Coffee leftCoffee;
    private Coffee rightCoffee;

    public CoffeePair  (Coffee leftCoffee, Coffee rightCoffee) {
        this.leftCoffee = leftCoffee;
        this.rightCoffee = rightCoffee;
    }

    public Coffee getLeftCoffee() {
        return leftCoffee;
    }

    public Coffee getRightCoffee() {
        return rightCoffee;
    }
}
