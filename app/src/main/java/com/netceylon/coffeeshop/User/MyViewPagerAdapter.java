package com.netceylon.coffeeshop.User;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.netceylon.coffeeshop.User.CoffeeFragments.*;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new ExpressoFragment();
            case 1:
                return new LatteFragment();
            case 2:
                return new CappuccinoFragment();
            case 3:
                return new BobaFragment();
            default:
                return new ExpressoFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
