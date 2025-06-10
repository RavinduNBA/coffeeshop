package com.netceylon.coffeeshop.User;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.netceylon.coffeeshop.R;
import com.netceylon.coffeeshop.User.MainFragments.*;
import com.netceylon.coffeeshop.databinding.ActivityUserBinding;


public class UserActivity extends AppCompatActivity {


    ActivityUserBinding binding;
    public void selectBottomNavigationViewItem(int itemId) {
        binding.bottomNavigationView.setSelectedItemId(itemId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        View bottomNavOverlay = findViewById(R.id.bottomNavOverlay);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
            navController.addOnDestinationChangedListener((controller, destination, args) -> {
                if (destination.getId() == R.id.coffeeDetailsFragment) {
                    bottomNavOverlay.setVisibility(View.VISIBLE);
                } else {
                    bottomNavOverlay.setVisibility(View.GONE);
                }
            });
        }
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

    }

    public void navigateToFragment(Fragment fragment, int bottomNavigationItemId) {
        // Calls with default: no highlight, show bottom nav
        navigateToFragment(fragment, bottomNavigationItemId, false, true);
    }

    public void navigateToFragment(Fragment fragment, int bottomNavigationItemId, boolean shouldHighlight) {
        // Calls with default: show bottom nav
        navigateToFragment(fragment, bottomNavigationItemId, shouldHighlight, true);
    }

    public void navigateToFragment(Fragment fragment, int bottomNavigationItemId, boolean shouldHighlight, boolean showBottomNav) {
        // Replace the fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

        // Show or hide BottomNavigationView
        setBottomNavigationVisibility(showBottomNav);

        // Update the BottomNavigationView selection if shouldHighlight is true
        if (shouldHighlight) {
            highlightMenuItem(bottomNavigationItemId);
        }
    }

    public void highlightMenuItem(int bottomNavigationItemId) {
        // Highlight the menu item manually
        binding.bottomNavigationView.getMenu().findItem(bottomNavigationItemId).setChecked(true);
    }



    public void setBottomNavigationVisibility(boolean isVisible) {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        if (bottomNavigationView != null) {
            bottomNavigationView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }
}