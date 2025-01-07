package com.netceylon.coffeeshop.User;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();




        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.homeIcon) {
                replaceFragment(new HomeFragment());
            } else if (id == R.id.cartIcon) {
                replaceFragment(new CartFragment());
            } else if (id == R.id.buynow) {
                replaceFragment(new CoffeeDetailsFragment());
            } else if (id == R.id.profile) {
                replaceFragment(new ProfileFragment());
            }

            return true;
        });



    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

    }

    public void navigateToFragment(Fragment fragment, int bottomNavigationItemId) {
        // Replace the fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

        // Update the BottomNavigationView selection
        //binding.bottomNavigationView.setSelectedItemId(bottomNavigationItemId);
        highlightMenuItem(bottomNavigationItemId);
    }

    public void highlightMenuItem(int bottomNavigationItemId) {
        // Highlight the menu item manually
        binding.bottomNavigationView.getMenu().findItem(bottomNavigationItemId).setChecked(true);
    }

}