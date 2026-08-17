package com.example.restaurantproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.PopupMenu;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantproject.models.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<Food> foodList;
    private List<Food> allFoodList;

    public FoodAdapter(List<Food> foodList) {
        this.foodList = foodList;
        this.allFoodList = new ArrayList<>(foodList);
    }
    public void filterByCategory(String category) {

        foodList.clear();

        if (category.equals("All")) {

            foodList.addAll(allFoodList);

        } else {

            for (Food food : allFoodList) {

                if (food.getCategory().equals(category)) {
                    foodList.add(food);
                }
            }
        }

        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food, parent, false);

        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {

        Food food = foodList.get(position);

        holder.tvFoodName.setText(food.getName());
        holder.tvFoodDescription.setText(food.getDescription());
        holder.tvFoodPrice.setText(food.getPrice() + " EGP");
        holder.ivFood.setImageResource(food.getImage());
        holder.btnFoodMenu.setOnClickListener(v -> {

            PopupMenu popupMenu = new PopupMenu(v.getContext(), holder.btnFoodMenu);

            popupMenu.getMenuInflater().inflate(
                    R.menu.food_popup_menu,
                    popupMenu.getMenu()
            );

            popupMenu.setOnMenuItemClickListener(item -> {

                if (item.getItemId() == R.id.action_view_details) {
                    // TODO: Open Food Details
                    return true;
                }

                if (item.getItemId() == R.id.action_add_to_cart) {
                    // TODO: Add food to Cart
                    return true;
                }

                return false;
            });

            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {

        ImageView ivFood;
        TextView tvFoodName;
        TextView tvFoodDescription;
        TextView tvFoodPrice;
        ImageButton btnFoodMenu;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            ivFood = itemView.findViewById(R.id.ivFood);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvFoodDescription = itemView.findViewById(R.id.tvFoodDescription);
            tvFoodPrice = itemView.findViewById(R.id.tvFoodPrice);
            btnFoodMenu = itemView.findViewById(R.id.btnFoodMenu);
        }
    }
}