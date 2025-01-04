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
            if (getActivity() != null) {
                Fragment cartFragment = new CartFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, cartFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        if (getActivity() instanceof UserActivity) {
            ((UserActivity) getActivity()).updateBottomNavigation(R.id.cartIcon);
        }

    }
}
