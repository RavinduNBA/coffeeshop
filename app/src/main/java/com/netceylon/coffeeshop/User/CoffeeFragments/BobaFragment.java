package com.netceylon.coffeeshop.User.CoffeeFragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netceylon.coffeeshop.R;
import com.netceylon.coffeeshop.User.MainFragments.BuyNowFragment;
import com.netceylon.coffeeshop.User.MainFragments.CoffeeDetailsFragment;
import com.netceylon.coffeeshop.User.UserActivity;


public class BobaFragment extends Fragment {

    ImageView imageView;

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
                Drawable boba1 = ResourcesCompat.getDrawable(getResources(), R.drawable.boba1, null);
                CoffeeDetailsFragment coffeeDetailsFragment = CoffeeDetailsFragment.newInstance(
                        R.drawable.boba1,
                        "Coffee Name",
                        "Toast Text",
                        "Milk Name",
                        "Description"
                );
                activity.navigateToFragment(coffeeDetailsFragment, R.id.buynow);
            }
        });
    }


}