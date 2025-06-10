package com.netceylon.coffeeshop.User.MainFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.netceylon.coffeeshop.R;
import com.netceylon.coffeeshop.User.CoffeeFragments.BobaAdapter;
import com.netceylon.coffeeshop.User.CoffeeFragments.BobaItem;
import com.netceylon.coffeeshop.User.CoffeeFragments.BobaRow;
import com.netceylon.coffeeshop.User.CoffeeFragments.Coffee;
import com.netceylon.coffeeshop.User.CoffeeFragments.CoffeeAdapter;
import com.netceylon.coffeeshop.User.CoffeeFragments.CoffeePair;
import com.netceylon.coffeeshop.User.CoffeeFragments.CoffeePairAdapter;
import com.netceylon.coffeeshop.User.UserActivity;

import java.util.ArrayList;
import java.util.List;


public class CoffeeListFragment extends Fragment {

    private RecyclerView recyclerView;
    private CoffeePairAdapter adapter;
    private ArrayList<CoffeePair> coffeePairs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coffee_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.bobaRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        if (getArguments() != null) {
            coffeePairs = (ArrayList<CoffeePair>) getArguments().getSerializable("coffee_pairs");

            adapter = new CoffeePairAdapter(coffeePairs, coffee -> {
                if (getActivity() instanceof UserActivity) {
                    UserActivity activity = (UserActivity) getActivity();
                    CoffeeDetailsFragment coffeeDetailsFragment = CoffeeDetailsFragment.newInstance(
                            coffee.getImageResId(),
                            coffee.getName(),
                            coffee.getTopping(),
                            coffee.getMilkType(),
                            coffee.getDescription()
                    );
                    activity.setBottomNavigationVisibility(false);
                    activity.navigateToFragment(coffeeDetailsFragment, R.id.specialOffersFragment);
                }
            });

            recyclerView.setAdapter(adapter);
        }
    }
}

