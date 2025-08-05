package com.example.lakatta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.example.lakatta.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private LinearLayout currentActiveNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Set Home as the default active navigation item
        currentActiveNav = binding.navHome;

        // Set up the click listeners for all buttons
        setupNavigationListeners();
        setupCategoryListeners();
    }

    private void setupNavigationListeners() {
        binding.navHome.setOnClickListener(v -> {
            setActiveNav(binding.navHome);
            showHomeContent(); // Show the main ScrollView
        });

        binding.navMap.setOnClickListener(v -> {
            setActiveNav(binding.navMap);
            loadFragment(new MapFragment()); // Load the MapFragment
        });

        binding.navItinerary.setOnClickListener(v -> {
            setActiveNav(binding.navItinerary);
            loadFragment(new ItineraryFragment()); // Load the ItineraryFragment
        });

        binding.navProfile.setOnClickListener(v -> {
            setActiveNav(binding.navProfile);
            loadFragment(new ProfileFragment()); // Load the ProfileFragment
        });
    }

    private void setupCategoryListeners() {
        // These can stay the same, they don't affect navigation
        binding.categoryThingsToDo.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this, ThingsToDoActivity.class);
                    startActivity(intent);
        });

        binding.categoryFood.setOnClickListener(v -> showToast("Food Clicked!"));
        // ... add the rest of your category listeners here if you want ...
    }

    /**
     * Replaces the content area with the given fragment.
     * @param fragment The fragment to display.
     */
    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            // Hide the home screen's ScrollView
            binding.scrollView.setVisibility(View.GONE);
            // Show the fragment container
            binding.fragmentContainer.setVisibility(View.VISIBLE);

            // Use the FragmentManager to replace the container with the new fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    /**
     * Shows the home content (the ScrollView) and hides the fragment container.
     */
    private void showHomeContent() {
        binding.fragmentContainer.setVisibility(View.GONE);
        binding.scrollView.setVisibility(View.VISIBLE);
    }

    /**
     * Updates the visual style of the bottom navigation buttons.
     * @param newActiveNav The LinearLayout of the navigation item that was just clicked.
     */
    private void setActiveNav(LinearLayout newActiveNav) {
        if (currentActiveNav == newActiveNav) return; // Do nothing if it's already active

        // De-activate the PREVIOUSLY active button
        ImageView oldIcon = (ImageView) currentActiveNav.getChildAt(0);
        TextView oldLabel = (TextView) currentActiveNav.getChildAt(1);
        oldIcon.setBackgroundResource(R.drawable.nav_inactive_background);
        oldIcon.setColorFilter(getColor(R.color.inactive_gray));
        oldLabel.setTextColor(getColor(R.color.inactive_gray));

        // Activate the NEW button
        ImageView newIcon = (ImageView) newActiveNav.getChildAt(0);
        TextView newLabel = (TextView) newActiveNav.getChildAt(1);
        newIcon.setBackgroundResource(R.drawable.nav_home_background);
        newIcon.setColorFilter(getColor(android.R.color.white));
        newLabel.setTextColor(getColor(R.color.active_green));

        // Update the reference to the new active item
        currentActiveNav = newActiveNav;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}