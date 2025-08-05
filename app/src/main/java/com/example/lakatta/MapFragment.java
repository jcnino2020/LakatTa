package com.example.lakatta;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MapFragment extends Fragment {

    private MapView mapView = null;

    // A launcher to handle the permission request result
    private final ActivityResultLauncher<String[]> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
                // After user responds, check if location permission was granted
                if (Boolean.TRUE.equals(permissions.get(Manifest.permission.ACCESS_FINE_LOCATION))) {
                    // Permission granted, you can now add features that use the user's location
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // --- osmdroid Configuration ---
        // This is important to set before the map is inflated
        Context ctx = requireActivity().getApplicationContext();
        Configuration.getInstance().load(ctx, androidx.preference.PreferenceManager.getDefaultSharedPreferences(ctx));

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // --- Initialize the MapView ---
        mapView = view.findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK); // Standard map tiles
        mapView.setMultiTouchControls(true); // Allow pinch-to-zoom

        // --- Set Initial Map Center and Zoom ---
        // Centering on Bacolod City Public Plaza
        GeoPoint startPoint = new GeoPoint(10.6700, 122.9525);
        mapView.getController().setZoom(15.5);
        mapView.getController().setCenter(startPoint);

        // --- Add a Marker for "The Ruins" ---
        addMarker(10.7107, 122.9832, "The Ruins", "Taj Mahal of Negros");
        // --- Add a Marker for "Campuestohan" ---
        addMarker(10.7583, 123.0458, "Campuestohan Highland Resort", "A mountain resort");

        // --- Request Location Permissions ---
        checkAndRequestPermissions();

        return view;
    }

    /**
     * Adds a simple marker to the map.
     */
    private void addMarker(double lat, double lon, String title, String snippet) {
        if (mapView == null) return;

        GeoPoint markerPoint = new GeoPoint(lat, lon);
        Marker startMarker = new Marker(mapView);
        startMarker.setPosition(markerPoint);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker.setTitle(title);
        startMarker.setSnippet(snippet);
        mapView.getOverlays().add(startMarker);
    }

    /**
     * Checks if location permissions are granted. If not, it requests them.
     */
    private void checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, so request it
            requestPermissionLauncher.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }
    }

    // --- Lifecycle Methods for osmdroid ---
    // These are important to correctly handle the map's state
    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume(); //needed for compass, my location overlays, v6.0.0 and up
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();  //needed for compass, my location overlays, v6.0.0 and up
        }
    }
}