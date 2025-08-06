package com.example.lakatta;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.lakatta.databinding.ActivityMainBinding;

import org.osmdroid.api.IMapController;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private LinearLayout currentActiveNav;

    // --- Variables for Image Slider ---
    private ImageSliderAdapter sliderAdapter;
    private final Handler sliderHandler = new Handler();
    private Runnable sliderRunnable;

    // --- Variables for Location ---
    private MyLocationNewOverlay myLocationOverlay;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        currentActiveNav = binding.navHome;

        // --- Setup all UI components ---
        setupNavigationListeners();
        setupCategoryListeners();
        setupImageSlider();
        setupPreviewMap(); // Sets up the static map view
        requestLocationPermissions(); // Kicks off the process to get location and add the blue dot

        handleNavigationIntent(getIntent());
    }

    /**
     * Sets up the basic, non-interactive properties of the preview map.
     */
    private void setupPreviewMap() {
        // Set a default location and zoom. This will be visible until the user's location is found.
        IMapController mapController = binding.previewMapView.getController();
        mapController.setZoom(15.0);
        GeoPoint startPoint = new GeoPoint(10.6695, 122.9506); // Bacolod Plaza default
        mapController.setCenter(startPoint);

        // Disable all interactions to make it a static preview
        binding.previewMapView.setMultiTouchControls(false);
        binding.previewMapView.setOnTouchListener((v, event) -> true); // Consumes touch events
        binding.previewMapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);

        // Make the entire card clickable to navigate to the full map fragment
        binding.nearMeCard.setOnClickListener(v -> binding.navMap.performClick());
    }

    /**
     * Kicks off the process of asking for location permissions.
     */
    private void requestLocationPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permission is already granted, set up the location overlay
            setupLocationOverlay();
        } else {
            // Permission is not granted, ask for it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    /**
     * This is called after the user responds to the permission request dialog.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted by the user.
                setupLocationOverlay();
            } else {
                // Permission was denied. The map will just stay at the default location.
                showToast("Location permission denied.");
            }
        }
    }

    /**
     * Sets up the MyLocationNewOverlay to show the user's location on the map.
     * This is only called AFTER permission has been granted.
     */
    private void setupLocationOverlay() {
        this.myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), binding.previewMapView);

        // 1. Load your existing 'ic_current_location_marker' drawable
        Drawable personDrawable = ContextCompat.getDrawable(this, R.drawable.ic_current_location_marker);
        Bitmap personBitmap = null;

        if (personDrawable != null) {
            // 2. Convert the Drawable to a Bitmap
            personBitmap = Bitmap.createBitmap(personDrawable.getIntrinsicWidth(), personDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(personBitmap);
            personDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            personDrawable.draw(canvas);
        }

        // 3. Set the custom bitmap for the location icon
        if (personBitmap != null) {
            this.myLocationOverlay.setPersonIcon(personBitmap);

            // ▼▼▼ THIS IS THE FIX ▼▼▼
            // 4. Set the "hotspot" or anchor of the icon to its center. This tells the map
            //    to place the center of the bitmap on the GPS coordinate, not the top-left corner.
            this.myLocationOverlay.setPersonHotspot(
                    personBitmap.getWidth() / 2.0f,
                    personBitmap.getHeight() / 2.0f
            );
            // ▲▲▲ END OF FIX ▲▲▲
        }

        // This executes a runnable when the location is first found.
        this.myLocationOverlay.runOnFirstFix(() -> {
            if (myLocationOverlay.getMyLocation() != null && binding != null) {
                // Animate the map to the user's location on the main UI thread
                runOnUiThread(() -> {
                    IMapController mapController = binding.previewMapView.getController();
                    mapController.animateTo(myLocationOverlay.getMyLocation());
                });
            }
        });

        // Add the overlay to the map
        binding.previewMapView.getOverlays().add(this.myLocationOverlay);

        // IMPORTANT: Tell the overlay to start listening for location updates.
        this.myLocationOverlay.enableMyLocation();
    }
    /**
     * Sets up the ViewPager2 for the featured image slider.
     */
    private void setupImageSlider() {
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.featured_bacolod);
        imageList.add(R.drawable.featured_campuestohan);
        imageList.add(R.drawable.featured_ruins);

        sliderAdapter = new ImageSliderAdapter(imageList);
        binding.featuredViewPager.setAdapter(sliderAdapter);

        sliderRunnable = () -> {
            if (sliderAdapter.getItemCount() > 0) {
                int currentItem = binding.featuredViewPager.getCurrentItem();
                int nextItem = (currentItem + 1) % sliderAdapter.getItemCount(); // Use modulo for clean looping
                binding.featuredViewPager.setCurrentItem(nextItem, true);
            }
        };

        binding.featuredViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000); // Reset timer on user swipe
            }
        });
    }

    /**
     * Manages the lifecycle of all dynamic components.
     */
    @Override
    protected void onResume() {
        super.onResume();
        // Resume image slider
        sliderHandler.postDelayed(sliderRunnable, 3000);
        // Resume map preview
        binding.previewMapView.onResume();
        // Resume location tracking
        if (myLocationOverlay != null) {
            myLocationOverlay.enableMyLocation();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pause image slider to save resources
        sliderHandler.removeCallbacks(sliderRunnable);
        // Pause map preview to save resources
        binding.previewMapView.onPause();
        // Pause location tracking to save battery
        if (myLocationOverlay != null) {
            myLocationOverlay.disableMyLocation();
        }
    }


    // --- Existing Navigation and Helper Methods ---

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleNavigationIntent(intent);
    }

    private void handleNavigationIntent(Intent intent) {
        if (intent != null && intent.hasExtra("NAVIGATE_TO")) {
            String destination = intent.getStringExtra("NAVIGATE_TO");
            if (destination == null) return;

            switch (destination) {
                case "MAP":
                    binding.navMap.performClick();
                    break;
                case "ITINERARY":
                    binding.navItinerary.performClick();
                    break;
                case "PROFILE":
                    binding.navProfile.performClick();
                    break;
                case "HOME":
                default:
                    binding.navHome.performClick();
                    break;
            }
            intent.removeExtra("NAVIGATE_TO");
        }
    }

    private void setupNavigationListeners() {
        binding.navHome.setOnClickListener(v -> {
            setActiveNav(binding.navHome);
            showHomeContent();
        });
        binding.navMap.setOnClickListener(v -> {
            setActiveNav(binding.navMap);
            loadFragment(new MapFragment());
        });
        binding.navItinerary.setOnClickListener(v -> {
            setActiveNav(binding.navItinerary);
            loadFragment(new ItineraryFragment());
        });
        binding.navProfile.setOnClickListener(v -> {
            setActiveNav(binding.navProfile);
            loadFragment(new ProfileFragment());
        });
    }

    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            binding.scrollView.setVisibility(View.GONE);
            binding.fragmentContainer.setVisibility(View.VISIBLE);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    private void setupCategoryListeners() {
        binding.categoryThingsToDo.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ThingsToDoActivity.class);
            startActivity(intent);
        });
        binding.categoryFood.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FoodActivity.class);
            startActivity(intent);
        });

        // ▼▼▼ ADD THIS CODE BLOCK FOR THE HOTELS BUTTON ▼▼▼
        binding.categoryHotels.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HotelsActivity.class);
            startActivity(intent);
        });
        // ▲▲▲ END OF NEW CODE ▲▲▲
    }

    private void showHomeContent() {
        binding.fragmentContainer.setVisibility(View.GONE);
        binding.scrollView.setVisibility(View.VISIBLE);
    }

    private void setActiveNav(LinearLayout newActiveNav) {
        if (currentActiveNav == newActiveNav) return;

        ImageView oldIcon = (ImageView) currentActiveNav.getChildAt(0);
        TextView oldLabel = (TextView) currentActiveNav.getChildAt(1);
        oldIcon.setBackgroundResource(R.drawable.nav_inactive_background);
        oldIcon.setColorFilter(getColor(R.color.inactive_gray));
        oldLabel.setTextColor(getColor(R.color.inactive_gray));

        ImageView newIcon = (ImageView) newActiveNav.getChildAt(0);
        TextView newLabel = (TextView) newActiveNav.getChildAt(1);
        newIcon.setBackgroundResource(R.drawable.nav_home_background);
        newIcon.setColorFilter(getColor(android.R.color.white));
        newLabel.setTextColor(getColor(R.color.active_green));

        currentActiveNav = newActiveNav;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}