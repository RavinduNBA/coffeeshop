package com.netceylon.coffeeshop.User.CoffeeFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.netceylon.coffeeshop.R;
import com.netceylon.coffeeshop.User.MainFragments.CoffeeDetailsFragment;
import com.netceylon.coffeeshop.User.MainFragments.HomeFragment;
import com.netceylon.coffeeshop.User.MainFragments.SpecialOffersFragment;
import com.netceylon.coffeeshop.User.UserActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpressoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpressoFragment extends Fragment {

    ImageView imageView;
    ImageView imageView2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expresso, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.addIcon1);

        imageView.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(ExpressoFragment.this);

            ExpressoFragmentDirections.ActionExpressoFragmentToCoffeeDetailsFragment action =
                    ExpressoFragmentDirections.actionExpressoFragmentToCoffeeDetailsFragment(
                            R.drawable.boba1,
                            "Coffee Expresso",
                            "Toast Text",
                            "Milk Name",
                            "Description"
                    );

            navController.navigate(action);

            // Deselect bottom nav items
            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
            bottomNavigationView.getMenu().setGroupCheckable(0, true, false);
            for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
                bottomNavigationView.getMenu().getItem(i).setChecked(false);
            }
            bottomNavigationView.getMenu().setGroupCheckable(0, true, true);
        });
        imageView2 = view.findViewById(R.id.addIcon2);
        imageView2.setOnClickListener(v -> {
            if (getActivity() instanceof UserActivity) {
                UserActivity activity = (UserActivity) getActivity();
                CoffeeDetailsFragment coffeeDetailsFragment = CoffeeDetailsFragment.newInstance(
                        R.drawable.mangoboba_1__1_,
                        "Coffee Expresso2",
                        "Toast Text",
                        "Milk Name",
                        "Description"
                );
                activity.navigateToFragment(coffeeDetailsFragment, R.id.specialOffersFragment);
            }
        });
    }
}