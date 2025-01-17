package com.netceylon.coffeeshop.User.MainFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.netceylon.coffeeshop.R;
import com.netceylon.coffeeshop.User.Cart;
import com.netceylon.coffeeshop.User.UserActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuyNowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuyNowFragment extends Fragment {

    Button buy_now;

    public BuyNowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buy_now, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up button and its click listener
        buy_now = view.findViewById(R.id.buttonbuynow);

        buy_now.setOnClickListener(v -> {
            if (getActivity() instanceof UserActivity) {
                UserActivity activity = (UserActivity) getActivity();
                activity.navigateToFragment(new CartFragment(), R.id.cartIcon, true);
            }
        });

    }

    private void updateCoffeeDetails(int imageResource, String name, String toast, String milk, String description) {
        // Update the coffee image
        ImageView coffeeImage = requireView().findViewById(R.id.coffeeImage);
        coffeeImage.setImageResource(imageResource);

        // Update the coffee name
        TextView coffeeName = requireView().findViewById(R.id.coffeeName);
        coffeeName.setText(name);

        // Update the toast text
        TextView toastText = requireView().findViewById(R.id.toastText);
        toastText.setText(toast);

        // Update the milk name
        TextView milkName = requireView().findViewById(R.id.milkName);
        milkName.setText(milk);

        // Update the description text
        TextView descriptionText = requireView().findViewById(R.id.descriptionText);
        descriptionText.setText(description);
    }
}
