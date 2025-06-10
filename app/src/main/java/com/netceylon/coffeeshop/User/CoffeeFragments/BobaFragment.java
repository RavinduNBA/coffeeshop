package com.netceylon.coffeeshop.User.CoffeeFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.netceylon.coffeeshop.R;
import com.netceylon.coffeeshop.User.MainFragments.CoffeeDetailsFragment;
import com.netceylon.coffeeshop.User.MainFragments.CoffeeRecyclerFragment;
import com.netceylon.coffeeshop.User.UserActivity;


public class BobaFragment extends Fragment {

    ImageView imageView;
    ImageView imageView2;
    CoffeeRecyclerFragment coffeeRecyclerFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_boba, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.addIcon1);


        imageView.setOnClickListener(v -> {
            if (getActivity() instanceof UserActivity) {
                UserActivity activity = (UserActivity) getActivity();
                CoffeeDetailsFragment coffeeDetailsFragment = CoffeeDetailsFragment.newInstance(
                        R.drawable.boba1,
                        "Avacado Boba",
                        "Boba Pearls",
                        "Oat Milk",
                        "Delight in the creamy, refreshing taste of our Avocado Boba, a perfect fusion of rich avocado, smooth milk, and chewy tapioca pearls. Each sip offers a luscious, velvety texture with a hint of natural sweetness, making it the ultimate treat for boba lovers seeking a unique and indulgent experience."
                );
                activity.setBottomNavigationVisibility(false);  // Hide bottom nav
                activity.navigateToFragment(coffeeDetailsFragment, R.id.specialOffersFragment);
            }
        });

        imageView2 = view.findViewById(R.id.addIcon2);
        imageView2.setOnClickListener(v -> {
            if (getActivity() instanceof UserActivity) {
                UserActivity activity = (UserActivity) getActivity();
                CoffeeDetailsFragment coffeeDetailsFragment = CoffeeDetailsFragment.newInstance(
                        R.drawable.mangoboba_1__1_,
                        "Coffee Boba2",
                        "Boba Pearls",
                        "Oat Milk",
                        "Enjoy the tropical, refreshing taste of our Mango Boba, a perfect blend of juicy mango, creamy milk, and chewy tapioca pearls. Each sip bursts with sweet, vibrant flavor and a smooth, luscious texture, making it the ultimate treat for boba lovers craving a fruity and delightful experience."
                );
                activity.navigateToFragment(coffeeDetailsFragment, R.id.specialOffersFragment);
            }
        });
    }


}