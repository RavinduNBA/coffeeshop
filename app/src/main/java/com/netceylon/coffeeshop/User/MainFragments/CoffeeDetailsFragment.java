package com.netceylon.coffeeshop.User.MainFragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.netceylon.coffeeshop.R;
import com.netceylon.coffeeshop.User.UserActivity;


public class CoffeeDetailsFragment extends Fragment {

    // Member variables to store arguments
    Button buy_now;
    private Drawable imageResource;
    private String name;
    private String toast;
    private String milk;
    private String description;

    public CoffeeDetailsFragment() {
        // Required empty public constructor
    }

    public static CoffeeDetailsFragment newInstance(int imageResourceId, String name, String toast, String milk, String description) {
        CoffeeDetailsFragment fragment = new CoffeeDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("imageResourceId", imageResourceId); // Use resource ID
        args.putString("name", name);
        args.putString("toast", toast);
        args.putString("milk", milk);
        args.putString("description", description);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Load arguments into member variables
            int imageResourceId = getArguments().getInt("imageResourceId");
            imageResource = ResourcesCompat.getDrawable(getResources(), imageResourceId, null);
            name = getArguments().getString("name");
            toast = getArguments().getString("toast");
            milk = getArguments().getString("milk");
            description = getArguments().getString("description");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coffee_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        buy_now = view.findViewById(R.id.buttonbuynow);


        buy_now.setOnClickListener(v -> {
            if (getActivity() instanceof UserActivity) {
                UserActivity activity = (UserActivity) getActivity();
                activity.navigateToFragment(new CartFragment(), R.id.cartFragment);
            }
        });

//        buy_now.setOnClickListener(v -> {
//            NavController navController = NavHostFragment.findNavController(this);
//            navController.navigate(R.id.cartFragment);
//        });
        // Update views now that the layout is fully created
        updateCoffeeDetails(view);
    }

    private void updateCoffeeDetails(View view) {
        // Update the coffee image
        ImageView coffeeImage = view.findViewById(R.id.coffeeImage);
        if (imageResource != null) {
            coffeeImage.setImageDrawable(imageResource);
        }

        // Update the coffee name
        TextView coffeeName = view.findViewById(R.id.coffeeName);
        coffeeName.setText(name);

        // Update the toast text
        TextView toastText = view.findViewById(R.id.toastText);
        toastText.setText(toast);

        // Update the milk name
        TextView milkName = view.findViewById(R.id.milkName);
        milkName.setText(milk);

        // Update the description text
        TextView descriptionText = view.findViewById(R.id.descriptionText);
        descriptionText.setText(description);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getActivity() instanceof UserActivity) {
            ((UserActivity) getActivity()).setBottomNavigationVisibility(true);
        }
    }
}
