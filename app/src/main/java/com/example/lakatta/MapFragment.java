package com.example.lakatta;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MapFragment extends Fragment {

    private MapView mapView = null;
    private FusedLocationProviderClient fusedLocationClient;
    private Marker currentLocationMarker; // A marker specifically for the user's location

    // The permission launcher to request location permissions
    private final ActivityResultLauncher<String[]> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
                if (Boolean.TRUE.equals(permissions.get(Manifest.permission.ACCESS_FINE_LOCATION))) {
                    // Permission granted, now get the location
                    getCurrentLocation();
                } else {
                    Toast.makeText(getContext(), "Location permission denied.", Toast.LENGTH_LONG).show();
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Context ctx = requireActivity().getApplicationContext();
        Configuration.getInstance().load(ctx, androidx.preference.PreferenceManager.getDefaultSharedPreferences(ctx));

        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // Initialize the location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        // --- Initialize MapView ---
        mapView = view.findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        mapView.getController().setZoom(15.5);

        // --- Set Initial Map Center to Bacolod (this will be updated once location is found) ---
        GeoPoint startPoint = new GeoPoint(10.6702613, 122.9469417); // Centered on San Sebastian Cathedral
        mapView.getController().setCenter(startPoint);

        // --- Add Static Markers for places ---
        // Your original 5 landmarks with updated coordinates
        addStaticMarker(10.7092206, 122.9824589, "The Ruins", "Known as the Taj Mahal of Negros'");
        addStaticMarker(10.660606, 123.1445225, "Campuestohan Highland Resort", "A family-friendly mountain resort.");
        addStaticMarker(10.6702613, 122.9469417, "San Sebastian Cathedral", "A late 19th-century church in Bacolod.");
        addStaticMarker(10.6950026, 122.9602796, "Art District", "Bacolod's creative corner: Art galleries and studios.");
        addStaticMarker(10.6633883, 122.9641266, "The Upper East", "Open-air, casual eats and people-watching.");

        // 10 Additional Bacolod Area Attractions
        addStaticMarker(10.676760, 122.950880, "Negros Museum", "A glimpse into Negrense history and culture.");
        addStaticMarker(10.676207, 122.951660, "Capitol Park and Lagoon", "A provincial park in the heart of Bacolod.");
        addStaticMarker(10.671111, 122.945556, "Manokan Country", "The best place for authentic Bacolod Chicken Inasal.");
        addStaticMarker(10.800045, 122.973156, "Balay Negrense", "A beautifully preserved ancestral house in Silay.");
        addStaticMarker(10.801858, 122.977309, "Bernardino Jalandoni Museum", "The 'Pink House' of Silay, a heritage landmark.");
        addStaticMarker(10.500000, 123.102000, "Mambukal Mountain Resort", "A mountain resort with hot springs and waterfalls.");
        addStaticMarker(11.037300, 123.201300, "Lakawon Island", "A banana-shaped island with white sand beaches.");
        addStaticMarker(10.624926, 122.965465, "Panaad Park and Stadium", "A multi-purpose stadium and home of the Panaad sa Negros Festival.");
        addStaticMarker(10.669167, 122.946389, "Bacolod Public Plaza", "The central plaza of Bacolod City, a hub of activity.");
        addStaticMarker(10.537610, 122.831631, "Bantayan Park", "A serene park in nearby Bago City.");


        // --- Setup FAB to re-center on location ---
        FloatingActionButton fab = view.findViewById(R.id.fab_my_location);
        fab.setOnClickListener(v -> {
            if (currentLocationMarker != null && currentLocationMarker.getPosition() != null) {
                mapView.getController().animateTo(currentLocationMarker.getPosition());
            } else {
                Toast.makeText(getContext(), "Current location not available yet.", Toast.LENGTH_SHORT).show();
            }
        });

        // --- Check permissions and get the user's location ---
        checkAndRequestPermissions();

        return view;
    }

    private void checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permission is already granted
            getCurrentLocation();
        } else {
            // Permission is not granted, so request it
            requestPermissionLauncher.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }
    }

    @SuppressLint("MissingPermission") // Suppressing warning because we check permissions right before calling
    private void getCurrentLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(requireActivity(), location -> {
                    if (location != null) {
                        // Location found. Center map and add/update the marker.
                        GeoPoint currentLocation = new GeoPoint(location.getLatitude(), location.getLongitude());

                        // Center the map on the user's location
                        mapView.getController().animateTo(currentLocation);

                        // If the marker doesn't exist, create it
                        if (currentLocationMarker == null) {
                            currentLocationMarker = new Marker(mapView);
                            currentLocationMarker.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_current_location_marker));
                            currentLocationMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
                            mapView.getOverlays().add(currentLocationMarker);
                        }
                        // Update the marker's position
                        currentLocationMarker.setPosition(currentLocation);
                        mapView.invalidate(); // Redraw the map
                    } else {
                        Toast.makeText(getContext(), "Could not retrieve current location. Please ensure location is enabled.", Toast.LENGTH_LONG).show();
                    }
                });
    }

    /**
     * Adds a static, default-style marker to the map for points of interest.
     */
    private void addStaticMarker(double lat, double lon, String title, String snippet) {
        GeoPoint markerPoint = new GeoPoint(lat, lon);
        Marker startMarker = new Marker(mapView);
        startMarker.setPosition(markerPoint);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker.setTitle(title);
        startMarker.setSnippet(snippet);
        mapView.getOverlays().add(startMarker);
    }

    // Lifecycle methods for osmdroid
    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
    }
}