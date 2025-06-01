package com.netceylon.coffeeshop.User.MainFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.netceylon.coffeeshop.Databases.SessionManager;
import com.netceylon.coffeeshop.R;
import com.netceylon.coffeeshop.User.UserActivity;

import java.util.HashMap;


public class ProfileFragment extends Fragment {

    Button backtoshopping;
    TextInputEditText name, email, password;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);

        SessionManager sessionManager = new SessionManager(getActivity(), SessionManager.SESSION_USERSESSION);
        HashMap<String, String> userData = sessionManager.getUserDetailFromSession();
        String usernameT = userData.get("username");
        String passwordT = userData.get("password");
        String emailT = userData.get("email");

        name.setText(usernameT);
        email.setText(emailT);
        password.setText(passwordT);




        // Set up button and its click listener
        backtoshopping = view.findViewById(R.id.backToShopping);

        backtoshopping.setOnClickListener(v -> {
            if (getActivity() instanceof UserActivity) {
                UserActivity activity = (UserActivity) getActivity();
                activity.navigateToFragment(new HomeFragment(), R.id.homeIcon, true);
            }
        });
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);





    }
}