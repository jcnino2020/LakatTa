package com.example.lakatta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

// This class now correctly implements the click listener from our adapter
public class ThingsToDoActivity extends AppCompatActivity implements PlaceAdapter.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_things_to_do);

        // Back button still works as before
        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        // --- NEW NAVIGATION LOGIC ---
        findViewById(R.id.navHome).setOnClickListener(v -> navigateToMain("HOME"));
        findViewById(R.id.navMap).setOnClickListener(v -> navigateToMain("MAP"));
        // Add these for the other buttons if they exist in your layout
        // findViewById(R.id.navItinerary).setOnClickListener(v -> navigateToMain("ITINERARY"));
        // findViewById(R.id.navProfile).setOnClickListener(v -> navigateToMain("PROFILE"));

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewPlaces);
        List<Place> places = new ArrayList<>();

        // =================================================================
        // START: DATA CREATION (This is the corrected part)
        // =================================================================

        // Define the long descriptions for each place
        String ruinsLongDesc = "The Ruins in Talisay City, Negros Occidental, is a well-preserved mansion built in the early 1900s by Don Mariano Ledesma Lacson in memory of his wife, Maria Braga.\n\nOften dubbed the 'Taj Mahal of Negros,' the mansion showcases Italian-inspired architecture with neoclassical columns and a romantic, skeletal frame made of concrete and egg white.";
        String campuestohanLongDesc = "Nestled in the lush green mountains of Talisay City, Campuestohan Highland Resort offers a refreshing escape with its cool climate and numerous attractions. It features a wave pool, giant statues, a zip line, and various themed areas perfect for family photos and fun.";
        String sanSebastianLongDesc = "The San Sebastian Cathedral, located in the heart of Bacolod City, is a magnificent example of Baroque architecture. Originally built in the 19th century with coral stone, it stands as a historical and spiritual center for the local Catholic community.";
        String artDistrictLongDesc = "Bacolod's Art District is a vibrant and dynamic space where creativity flourishes. It features a collection of art galleries, street murals, unique cafes, and performance venues, making it a must-visit for artists and culture enthusiasts.";
        String upperEastLongDesc = "The Upper East is a modern, integrated urban township developed by Megaworld. It combines residential condominiums, shopping malls, and open-air recreational spaces, offering a contemporary lifestyle hub for dining and people-watching.";


        // Now, create the Place objects using the NEW constructor that includes the long description
        places.add(new Place(R.drawable.place_the_ruins, "The Ruins", "Known as the 'Taj Mahal of Negros'.", ruinsLongDesc, "★★★★★"));
        places.add(new Place(R.drawable.place_campuestohan, "Campuestohan Highland Resort", "A family-friendly mountain resort.", campuestohanLongDesc, "★★★★☆"));
        places.add(new Place(R.drawable.place_san_sebastian, "San Sebastian Cathedral", "A late 19th-century church in Bacolod.", sanSebastianLongDesc, "★★★★☆"));
        places.add(new Place(R.drawable.place_art_district, "Art District", "Bacolod's creative corner: Art galleries and studios.", artDistrictLongDesc, "★★★★☆"));
        places.add(new Place(R.drawable.place_upper_east, "The Upper East", "Open-air, casual eats and people-watching.", upperEastLongDesc, "★★★☆☆"));

        // =================================================================
        // END: DATA CREATION
        // =================================================================


        // The rest of the setup remains the same
        PlaceAdapter adapter = new PlaceAdapter(places);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * This method is called by the adapter when a list item is clicked.
     */
    @Override
    public void onItemClick(Place place) {
        // Create an intent to open the detail screen
        Intent intent = new Intent(this, PlaceDetailActivity.class);

        // Put the entire 'Place' object (which is Parcelable) into the intent
        intent.putExtra("PLACE_DETAILS", place);

        // Start the new activity
        startActivity(intent);
    }

    private void navigateToMain(String destination) {
        Intent intent = new Intent(this, MainActivity.class);
        // These flags clear all activities on top of MainActivity and bring it to the front
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("NAVIGATE_TO", destination);
        startActivity(intent);
    }
}