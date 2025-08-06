package com.example.lakatta;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    // The 'final' keyword is removed so we can modify the list's contents.
    private List<Restaurant> restaurantList;

    public RestaurantAdapter(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_restaurant, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.imageView.setImageResource(restaurant.getImageResId());
        holder.titleView.setText(restaurant.getTitle());
        holder.descriptionView.setText(restaurant.getDescription());
        holder.ratingView.setText(restaurant.getRating());
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    // ▼▼▼ THIS IS THE CORRECT LOCATION FOR THE updateData METHOD ▼▼▼
    /**
     * Updates the list of restaurants and notifies the adapter to refresh the view.
     * @param newRestaurantList The new list to display.
     */
    public void updateData(List<Restaurant> newRestaurantList) {
        // Clear the original list.
        restaurantList.clear();
        // Add all the new items from the filtered list.
        restaurantList.addAll(newRestaurantList);
        // Notify the adapter that the data has changed so it can redraw the list.
        notifyDataSetChanged();
    }


    // The ViewHolder's only job is to hold the views for a single item.
    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleView, descriptionView, ratingView;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            // Make sure these IDs match your list_item_restaurant.xml
            imageView = itemView.findViewById(R.id.restaurant_image);
            titleView = itemView.findViewById(R.id.restaurant_title);
            descriptionView = itemView.findViewById(R.id.restaurant_description);
            ratingView = itemView.findViewById(R.id.restaurant_stars);
        }

        // The updateData method has been MOVED OUT of here.
    }
}