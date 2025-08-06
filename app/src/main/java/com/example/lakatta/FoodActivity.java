package com.example.lakatta;

import android.content.Intent; // <-- FIXED: Added the missing import
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // --- FIXED: Loading the CORRECT layout file ---
        setContentView(R.layout.activity_food);

        // Back button still works as before
        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        // --- FIXED: Make sure the nav buttons exist in your activity_food.xml layout ---
        // If they don't, you need to copy the bottomNavBar LinearLayout from activity_main.xml
        findViewById(R.id.navHome).setOnClickListener(v -> navigateToMain("HOME"));
        findViewById(R.id.navMap).setOnClickListener(v -> navigateToMain("MAP"));
        findViewById(R.id.navItinerary).setOnClickListener(v -> navigateToMain("ITINERARY"));
        findViewById(R.id.navProfile).setOnClickListener(v -> navigateToMain("PROFILE"));

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        // --- FIXED: This will now correctly find the RecyclerView ---
        RecyclerView recyclerView = findViewById(R.id.recyclerViewRestaurants);

        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(R.drawable.restaurant_manokan_country, "Manokan Country", "The best Chicken Inasal.", "₱₱ · ★★★★★"));
        restaurants.add(new Restaurant(R.drawable.restaurant_calea, "Calea Pastries & Coffee", "Famous for delicious cakes.", "₱₱ · ★★★★★"));
        restaurants.add(new Restaurant(R.drawable.restaurant_calea, "Apollo Restaurant", "A Bacolod institution for classic Chinese-Filipino cuisine.", "₱₱ · ★★★★☆"));
        restaurants.add(new Restaurant(R.drawable.restaurant_calea, "Maria Kucina Familia", "Cozy, family-run spot for authentic Italian food.", "₱₱₱ · ★★★★☆"));
        restaurants.add(new Restaurant(R.drawable.restaurant_calea, "Chifa Comedor", "Unique restaurant offering delicious Peruvian-Chinese fusion.", "₱₱₱ · ★★★★☆"));
        restaurants.add(new Restaurant(R.drawable.restaurant_calea, "Chicken Deli", "Another major player in the inasal scene with many branches.", "₱₱ · ★★★☆☆"));
        restaurants.add(new Restaurant(R.drawable.restaurant_calea, "Eron's Cansi House", "A top contender for the best cansi in the city.", "₱ · ★★★★☆"));
        restaurants.add(new Restaurant(R.drawable.restaurant_calea, "Negros Museum Cafe", "Enjoy Negrense dishes within a cultural and historical setting.", "₱₱ · ★★★★☆"));
        restaurants.add(new Restaurant(R.drawable.restaurant_calea, "Bascon Cafe", "A classic Bacolod cafe known for its Sate Babe and cakes.", "₱₱₱ · ★★★★☆"));
        restaurants.add(new Restaurant(R.drawable.restaurant_calea, "L'Kaisei Japanese Restaurant", "A go-to for quality sushi, sashimi, and Japanese entrees.", "₱₱₱ · ★★★★☆"));
        restaurants.add(new Restaurant(R.drawable.restaurant_calea, "Balay Bintana Cafe", "A charming and artsy cafe with a relaxing, homey vibe.", "₱₱ · ★★★★☆"));
        restaurants.add(new Restaurant(R.drawable.restaurant_calea, "Miren Cafe", "Modern and popular cafe for specialty coffee and pastries.", "₱₱ · ★★★★☆"));
        restaurants.add(new Restaurant(R.drawable.restaurant_calea, "Greeno'z Pizzaria", "A local favorite for their unique and generously topped pizzas.", "₱₱ · ★★★☆☆"));
        restaurants.add(new Restaurant(R.drawable.restaurant_calea, "Sugarcane Bar & Restaurant", "Creative cocktails and dishes made from local Negrense ingredients.", "₱₱₱ · ★★★★☆"));
        restaurants.add(new Restaurant(R.drawable.restaurant_calea, "Bar 21", "The iconic bar of 21 Resto, famous for its batchoy and classic ambiance.", "₱₱ · ★★★★☆"));
        restaurants.add(new Restaurant(R.drawable.restaurant_calea, "Copo de Tia Maria", "A beloved local spot for great coffee and Filipino snacks.", "₱ · ★★★★☆"));
        restaurants.add(new Restaurant(R.drawable.restaurant_calea, "JJ's Inato", "An affordable and popular choice for grilled chicken and Filipino meals.", "₱ · ★★★☆☆"));

        RestaurantAdapter adapter = new RestaurantAdapter(restaurants);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Creates an intent to go back to MainActivity and tells it which tab to open.
     * @param destination The tab to navigate to ("HOME", "MAP", etc.)
     */
    private void navigateToMain(String destination) {
        Intent intent = new Intent(this, MainActivity.class);
        // These flags clear all activities on top of MainActivity and bring it to the front
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("NAVIGATE_TO", destination);
        startActivity(intent);
    }
}