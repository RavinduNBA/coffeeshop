package com.netceylon.coffeeshop.User.MainFragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netceylon.coffeeshop.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoffeeDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoffeeDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CoffeeDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CoffeeDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CoffeeDetailsFragment newInstance(String param1, String param2) {
        CoffeeDetailsFragment fragment = new CoffeeDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coffee_details, container, false);

    }

    public void updateCoffeeDetails(Drawable imageResource, String name, String toast, String milk, String description) {
        // Update the coffee image
        ImageView coffeeImage = requireView().findViewById(R.id.coffeeImage);
        coffeeImage.setImageDrawable(imageResource);

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