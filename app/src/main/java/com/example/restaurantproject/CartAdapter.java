package com.example.restaurantproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restaurantproject.database.DatabaseHelper;
import com.example.restaurantproject.models.CartItem;
import com.example.restaurantproject.models.Food;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartList;
    private DatabaseHelper dbHelper;
    private Runnable onCartUpdated;

    public CartAdapter(List<CartItem> cartList, DatabaseHelper dbHelper, Runnable onCartUpdated) {
        this.cartList = cartList;
        this.dbHelper = dbHelper;
        this.onCartUpdated = onCartUpdated;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartList.get(position);
        Food food = dbHelper.getFoodById(item.getFoodId());

        if (food != null) {
            holder.tvCartFoodName.setText(food.getName() + " (x" + item.getQuantity() + ")");
            
            StringBuilder extras = new StringBuilder("Size: " + item.getSize());
            if (item.getExtraCheese() == 1) extras.append("\n+ Extra Cheese");
            if (item.getExtraBacon() == 1) extras.append("\n+ Extra Bacon");
            if (item.getExtraSauce() == 1) extras.append("\n+ Extra Sauce");
            if (item.getExtraLettuce() == 1) extras.append("\n+ Extra Lettuce");
            
            holder.tvCartExtras.setText(extras.toString());

            double itemTotal = food.getPrice();
            if (item.getSize().equals("Medium")) itemTotal += 50.0;
            else if (item.getSize().equals("Large")) itemTotal += 100.0;
            
            if (item.getExtraCheese() == 1) itemTotal += 15.0;
            if (item.getExtraSauce() == 1) itemTotal += 10.0;
            if (item.getExtraLettuce() == 1) itemTotal += 20.0;
            if (item.getExtraBacon() == 1) itemTotal += 30.0;
            
            double totalLinePrice = itemTotal * item.getQuantity();
            holder.tvCartPrice.setText(String.format("%.2f EGP", totalLinePrice));

            Glide.with(holder.itemView.getContext())
                    .load(food.getImage())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(holder.ivCartFood);
        }

        holder.btnRemoveCartItem.setOnClickListener(v -> {
            dbHelper.removeCartItem(item.getId());
            cartList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartList.size());
            if (onCartUpdated != null) {
                onCartUpdated.run();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCartFood;
        TextView tvCartFoodName, tvCartExtras, tvCartPrice;
        ImageButton btnRemoveCartItem;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCartFood = itemView.findViewById(R.id.ivCartFood);
            tvCartFoodName = itemView.findViewById(R.id.tvCartFoodName);
            tvCartExtras = itemView.findViewById(R.id.tvCartExtras);
            tvCartPrice = itemView.findViewById(R.id.tvCartPrice);
            btnRemoveCartItem = itemView.findViewById(R.id.btnRemoveCartItem);
        }
    }
}
