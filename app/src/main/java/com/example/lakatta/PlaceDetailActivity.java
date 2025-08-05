package com.example.lakatta;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class PlaceDetailActivity extends AppCompatActivity {

    private TextView tabAbout, tabDetails, tabReviews;
    private View tabIndicator;
    private TextView currentActiveTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        // --- Find all the views ---
        tabAbout = findViewById(R.id.tabAbout);
        tabDetails = findViewById(R.id.tabDetails);
        tabReviews = findViewById(R.id.tabReviews);
        tabIndicator = findViewById(R.id.tabIndicator);

        // --- Load Data (as before) ---
        Place place = getIntent().getParcelableExtra("PLACE_DETAILS");
        if (place == null) {
            Toast.makeText(this, "Error: Place details not found.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ImageView imageViewHeader = findViewById(R.id.imageViewHeader);
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewStars = findViewById(R.id.textViewStars);

        imageViewHeader.setImageResource(place.getImageResId());
        textViewTitle.setText(place.getTitle());
        textViewStars.setText(place.getStars());

        // --- Setup Button Listeners (as before) ---
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
        findViewById(R.id.buttonAddToItinerary).setOnClickListener(v -> showToast("Added " + place.getTitle() + " to Itinerary!"));
        findViewById(R.id.buttonGetDirections).setOnClickListener(v -> showToast("Getting directions to " + place.getTitle()));
        findViewById(R.id.bookRideButton).setOnClickListener(v -> showToast("Booking a ride to " + place.getTitle()));

        // --- Setup Tab Click Listeners ---
        tabAbout.setOnClickListener(v -> selectTab(tabAbout));
        tabDetails.setOnClickListener(v -> selectTab(tabDetails));
        tabReviews.setOnClickListener(v -> selectTab(tabReviews));

        // --- Load the initial "About" fragment ---
        currentActiveTab = tabAbout; // Set initial active tab
        loadFragment(new AboutPlaceFragment());
    }

    private void selectTab(TextView newActiveTab) {
        // Do nothing if the same tab is clicked
        if (currentActiveTab == newActiveTab) {
            return;
        }

        // De-activate the old tab
        currentActiveTab.setTypeface(null, Typeface.NORMAL);
        currentActiveTab.setTextColor(ContextCompat.getColor(this, R.color.inactive_gray));

        // Activate the new tab
        newActiveTab.setTypeface(null, Typeface.BOLD);
        newActiveTab.setTextColor(ContextCompat.getColor(this, R.color.active_green));

        // Move the indicator
        tabIndicator.animate().x(newActiveTab.getX()).setDuration(200).start();

        // Load the correct fragment
        if (newActiveTab.getId() == R.id.tabAbout) {
            loadFragment(new AboutPlaceFragment());
        } else if (newActiveTab.getId() == R.id.tabDetails) {
            loadFragment(new DetailsPlaceFragment());
        } else if (newActiveTab.getId() == R.id.tabReviews) {
            loadFragment(new ReviewsPlaceFragment());
        }

        // Update the current active tab
        currentActiveTab = newActiveTab;
    }

    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.place_detail_fragment_container, fragment);
            transaction.commit();
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}