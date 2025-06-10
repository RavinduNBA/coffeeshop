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

public class BobaAdapter extends RecyclerView.Adapter<BobaAdapter.BobaViewHolder> {

    private List<BobaRow> bobaRows;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onLeftAddClick(BobaItem item);
        void onRightAddClick(BobaItem item);
    }

    public BobaAdapter(List<BobaRow> bobaRows, OnItemClickListener listener) {
        this.bobaRows = bobaRows;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BobaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coffee_pair, parent, false);
        return new BobaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BobaViewHolder holder, int position) {
        BobaRow row = bobaRows.get(position);

        // Left Card
        holder.leftImage.setImageResource(row.leftItem.imageResId);
        holder.leftName.setText(row.leftItem.coffeeName);
        holder.leftMilk.setText(row.leftItem.milkName);
        holder.leftPrice.setText(row.leftItem.price);

        // Right Card
        holder.rightImage.setImageResource(row.rightItem.imageResId);
        holder.rightName.setText(row.rightItem.coffeeName);
        holder.rightMilk.setText(row.rightItem.milkName);
        holder.rightPrice.setText(row.rightItem.price);

        // Add Icon Click Listeners
        holder.leftAddIcon.setOnClickListener(v -> listener.onLeftAddClick(row.leftItem));
        holder.rightAddIcon.setOnClickListener(v -> listener.onRightAddClick(row.rightItem));
    }

    @Override
    public int getItemCount() {
        return bobaRows.size();
    }

    static class BobaViewHolder extends RecyclerView.ViewHolder {
        ImageView leftImage, rightImage, leftAddIcon, rightAddIcon;
        TextView leftName, leftMilk, leftPrice, rightName, rightMilk, rightPrice;

        public BobaViewHolder(@NonNull View itemView) {
            super(itemView);
            leftImage = itemView.findViewById(R.id.leftImage);
            leftName = itemView.findViewById(R.id.leftName);
            leftMilk = itemView.findViewById(R.id.leftMilk);
            leftPrice = itemView.findViewById(R.id.leftPrice);
            leftAddIcon = itemView.findViewById(R.id.leftAddIcon);

            rightImage = itemView.findViewById(R.id.rightImage);
            rightName = itemView.findViewById(R.id.rightName);
            rightMilk = itemView.findViewById(R.id.rightMilk);
            rightPrice = itemView.findViewById(R.id.rightPrice);
            rightAddIcon = itemView.findViewById(R.id.rightAddIcon);
        }
    }
}