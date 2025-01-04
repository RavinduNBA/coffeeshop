package com.netceylon.coffeeshop.User.MainFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.netceylon.coffeeshop.R;
import com.netceylon.coffeeshop.User.MyViewPagerAdapter;

public class HomeFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    MyViewPagerAdapter myViewPagerAdapter;

    public HomeFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);


    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        // Find the ViewPager2
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);

        // Set up the adapter
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(requireActivity());
        viewPager.setAdapter(adapter);
    }

}