package com.netceylon.coffeeshop.User;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.netceylon.coffeeshop.R;
import com.netceylon.coffeeshop.User.CoffeeFragments.*;
import com.netceylon.coffeeshop.User.MainFragments.CoffeeListFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyViewPagerAdapter extends FragmentStateAdapter {

    private final List<String> categories;

    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<String> categories) {
        super(fragmentActivity);
        this.categories = categories;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        CoffeeListFragment fragment = new CoffeeListFragment();
        ArrayList<CoffeePair> coffeePairs = new ArrayList<>();

        if (position == 0) { // Espresso tab
            coffeePairs = createCoffeePairs(Arrays.asList(
                    new Coffee(R.drawable.boba1, "Espresso Single", "Whole topping", "Whole Milk","Sample Description","$4.99"),
                    new Coffee(R.drawable.boba1, "Espresso Double", "Whole topping", "Whole Milk","Sample Description","$4.99")
            ));
        } else if (position == 1) { // Latte tab
            coffeePairs = createCoffeePairs(Arrays.asList(
                    new Coffee(R.drawable.mangoboba_1__1_, "Latte Vanilla", "Whole topping", "Whole Milk","Sample Description","$4.99"),
                    new Coffee(R.drawable.mangoboba_1__1_, "Latte Caramel", "Whole topping", "Whole Milk","Sample Description","$4.99")
            ));
        } else if (position == 2) { // Cappuccino tab
            coffeePairs = createCoffeePairs(Arrays.asList(
                    new Coffee(R.drawable.boba1, "Cappuccino Classic", "Whole topping", "Whole Milk","Sample Description","$4.99"),
                    new Coffee(R.drawable.boba1, "Cappuccino Mocha", "Whole topping", "Whole Milk","Sample Description","$4.99")
            ));
        } else if (position == 3) { // Boba tab
            coffeePairs = createCoffeePairs(Arrays.asList(
                    new Coffee(R.drawable.boba1, "Avocado Boba", "Whole topping", "Whole Milk","Sample Description","$4.99"),
                    new Coffee(R.drawable.mangoboba_1__1_, "Mango Boba", "Whole topping", "Whole Milk","Sample Description","$4.99")
            ));
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable("coffee_pairs", coffeePairs);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    private ArrayList<CoffeePair> createCoffeePairs(List<Coffee> coffeeList) {
        ArrayList<CoffeePair> pairs = new ArrayList<>();
        for (int i = 0; i < coffeeList.size(); i += 2) {
            Coffee left = coffeeList.get(i);
            Coffee right = (i + 1 < coffeeList.size()) ? coffeeList.get(i + 1) : null;
            pairs.add(new CoffeePair(left, right));
        }
        return pairs;
    }
}

