package com.example.lakatta;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import com.google.android.material.chip.ChipGroup;
import android.widget.Toast;

public class HotelsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);

        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        // ... your navigateToMain listeners ...

        // ▼▼▼ ADD THIS CHIPGROUP LISTENER ▼▼▼
        ChipGroup chipGroup = findViewById(R.id.chipGroupHotels);
        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) {
                // Handle case where no chip is selected, if necessary
                return;
            }

            int checkedChipId = checkedIds.get(0); // Since it's single selection

            if (checkedChipId == R.id.chipLuxury) {
                Toast.makeText(this, "Filtering for Luxury Hotels", Toast.LENGTH_SHORT).show();
                // Here you would update your RecyclerView with luxury hotels
            } else if (checkedChipId == R.id.chipBudget) {
                Toast.makeText(this, "Filtering for Budget Hotels", Toast.LENGTH_SHORT).show();
                // Here you would update your RecyclerView with budget hotels
            } else if (checkedChipId == R.id.chipResort) {
                Toast.makeText(this, "Filtering for Resort Hotels", Toast.LENGTH_SHORT).show();
                // Here you would update your RecyclerView with resort hotels
            }
        });

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewHotels);

        List<Hotel> hotels = new ArrayList<>();
        hotels.add(new Hotel(R.drawable.hotel_lfisher, "L'Fisher Hotel", "A premier hotel in the city.", "₱₱₱ · ★★★★★"));
        hotels.add(new Hotel(R.drawable.hotel_seda, "Seda Capitol Central", "Modern hotel with a rooftop bar.", "₱₱₱ · ★★★★★"));

        HotelAdapter adapter = new HotelAdapter(hotels);
        recyclerView.setAdapter(adapter);
    }

    private void navigateToMain(String destination) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("NAVIGATE_TO", destination);
        startActivity(intent);
    }
}