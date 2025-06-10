package com.netceylon.coffeeshop.User.CoffeeFragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.netceylon.coffeeshop.R;

import java.util.List;

public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.CoffeeViewHolder> {

    public interface OnCoffeeClickListener {
        void onCoffeeClick(Coffee coffee);
    }

    private List<Coffee> coffeeList;
    private OnCoffeeClickListener listener;

    public CoffeeAdapter(List<Coffee> coffeeList, OnCoffeeClickListener listener) {
        this.coffeeList = coffeeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CoffeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coffee_pair, parent, false);
        return new CoffeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoffeeViewHolder holder, int position) {
        Coffee coffee = coffeeList.get(position);
        holder.bind(coffee);
    }

    @Override
    public int getItemCount() {
        return coffeeList.size();
    }

    class CoffeeViewHolder extends RecyclerView.ViewHolder {

        ImageView coffeeImage;
        TextView coffeeName;
        TextView coffeePrice;
        ImageView addIcon;

        public CoffeeViewHolder(@NonNull View itemView) {
            super(itemView);
            coffeeImage = itemView.findViewById(R.id.coffeeImageview);
            coffeeName = itemView.findViewById(R.id.coffeeName);
            coffeePrice = itemView.findViewById(R.id.coffeePrice);
            addIcon = itemView.findViewById(R.id.addIcon1);
        }

        public void bind(Coffee coffee) {
            coffeeImage.setImageResource(coffee.getImageResId());
            coffeeName.setText(coffee.getName());
            coffeePrice.setText(coffee.getPrice());

            addIcon.setOnClickListener(v -> listener.onCoffeeClick(coffee));
        }
    }
}
