package com.netceylon.coffeeshop.User;

import android.os.Bundle;

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

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
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
        // Call the overloaded method with false to skip highlightMenuItem by default
        navigateToFragment(fragment, bottomNavigationItemId, false);
    }

    public void navigateToFragment(Fragment fragment, int bottomNavigationItemId, boolean shouldHighlight) {
        // Replace the fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

        // Update the BottomNavigationView selection if shouldHighlight is true
        if (shouldHighlight) {
            highlightMenuItem(bottomNavigationItemId);
        }
    }

    public void highlightMenuItem(int bottomNavigationItemId) {
        // Highlight the menu item manually
        binding.bottomNavigationView.getMenu().findItem(bottomNavigationItemId).setChecked(true);
    }

}