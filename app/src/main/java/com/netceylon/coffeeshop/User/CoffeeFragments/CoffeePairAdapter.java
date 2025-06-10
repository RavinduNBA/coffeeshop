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

public class CoffeePairAdapter extends RecyclerView.Adapter<CoffeePairAdapter.CoffeePairViewHolder> {

    public interface OnCoffeeClickListener {
        void onCoffeeClick(Coffee coffee);
    }

    private List<CoffeePair> coffeePairs;
    private OnCoffeeClickListener listener;

    public CoffeePairAdapter(List<CoffeePair> coffeePairs, OnCoffeeClickListener listener) {
        this.coffeePairs = coffeePairs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CoffeePairViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coffee_pair, parent, false);
        return new CoffeePairViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoffeePairViewHolder holder, int position) {
        CoffeePair pair = coffeePairs.get(position);
        holder.bind(pair);
    }

    @Override
    public int getItemCount() {
        return coffeePairs.size();
    }

    class CoffeePairViewHolder extends RecyclerView.ViewHolder {

        ImageView leftImage, rightImage, leftAddIcon, rightAddIcon;
        TextView leftName, rightName, leftMilk, rightMilk, leftPrice, rightPrice;

        public CoffeePairViewHolder(@NonNull View itemView) {
            super(itemView);
            leftImage = itemView.findViewById(R.id.leftImage);
            rightImage = itemView.findViewById(R.id.rightImage);
            leftName = itemView.findViewById(R.id.leftName);
            rightName = itemView.findViewById(R.id.rightName);
            leftMilk = itemView.findViewById(R.id.leftMilk);
            rightMilk = itemView.findViewById(R.id.rightMilk);
            leftPrice = itemView.findViewById(R.id.leftPrice);
            rightPrice = itemView.findViewById(R.id.rightPrice);
            leftAddIcon = itemView.findViewById(R.id.leftAddIcon);
            rightAddIcon = itemView.findViewById(R.id.rightAddIcon);
        }

        public void bind(CoffeePair pair) {
            Coffee leftCoffee = pair.getLeftCoffee();
            Coffee rightCoffee = pair.getRightCoffee();

            if (leftCoffee != null) {
                leftImage.setImageResource(leftCoffee.getImageResId());
                leftName.setText(leftCoffee.getName());
                leftMilk.setText(leftCoffee.getMilkType());
                leftPrice.setText(leftCoffee.getPrice());
                leftAddIcon.setOnClickListener(v -> listener.onCoffeeClick(leftCoffee));
            }

            if (rightCoffee != null) {
                rightImage.setImageResource(rightCoffee.getImageResId());
                rightName.setText(rightCoffee.getName());
                rightMilk.setText(rightCoffee.getMilkType());
                rightPrice.setText(rightCoffee.getPrice());
                rightAddIcon.setOnClickListener(v -> listener.onCoffeeClick(rightCoffee));
            } else {
                // Hide right card if there's no right item
                itemView.findViewById(R.id.rightCard).setVisibility(View.INVISIBLE);
            }
        }
    }
}
