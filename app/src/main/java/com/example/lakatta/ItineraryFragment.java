package com.example.lakatta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ItineraryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_itinerary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // --- Set up Click Listeners for buttons ---
        view.findViewById(R.id.backButton).setOnClickListener(v -> showToast("Back button clicked!"));
        view.findViewById(R.id.buttonCreateTrip).setOnClickListener(v -> showToast("Create New Trip clicked!"));

        // --- Populate the included layouts with data ---
        // Since we are not using a RecyclerView for this static example, we find each 'include' block
        // Note: For a dynamic list, you would use a RecyclerView.

        // Find all the included layouts
        View item1 = view.findViewById(R.id.item1); // We need to add IDs to the includes
        View item2 = view.findViewById(R.id.item2);
        View item3 = view.findViewById(R.id.item3);

        // Populate the first item
        populateItem(item1, R.drawable.place_the_ruins, "10:00 AM - The Ruins", "Explore the historic mansion.");

        // Populate the second item
        // IMPORTANT: Add an image named 'place_manokan_country' to res/drawable
        populateItem(item2, R.drawable.place_manokan_country, "12:30 PM - Manokan Country", "Lunch: Chicken Inasal.");

        // Populate the third item
        // IMPORTANT: Add an image named 'place_calea' to res/drawable
        populateItem(item3, R.drawable.place_calea, "2:30 PM - Calea", "Deserts from the famous Calea.");
    }

    /**
     * Helper method to populate the views inside an included layout.
     */
    private void populateItem(View itemView, int imageRes, String title, String description) {
        if (itemView == null) return;

        ImageView imageView = itemView.findViewById(R.id.item_image);
        TextView titleView = itemView.findViewById(R.id.item_title);
        TextView descriptionView = itemView.findViewById(R.id.item_description);

        imageView.setImageResource(imageRes);
        titleView.setText(title);
        descriptionView.setText(description);
    }

    private void showToast(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}