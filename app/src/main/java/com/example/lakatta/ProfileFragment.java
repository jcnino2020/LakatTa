package com.example.lakatta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the new layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up click listeners for all interactive elements
        view.findViewById(R.id.backButton).setOnClickListener(v -> showToast("Back button clicked!"));
        view.findViewById(R.id.textViewEditProfile).setOnClickListener(v -> showToast("Edit Profile clicked!"));

        view.findViewById(R.id.menuBookings).setOnClickListener(v -> showToast("My Bookings clicked!"));
        view.findViewById(R.id.menuFavorites).setOnClickListener(v -> showToast("My Favorites clicked!"));
        view.findViewById(R.id.menuReviews).setOnClickListener(v -> showToast("My Reviews clicked!"));
        view.findViewById(R.id.menuSettings).setOnClickListener(v -> showToast("Settings clicked!"));

        view.findViewById(R.id.buttonLogout).setOnClickListener(v -> showToast("Log Out clicked!"));
    }

    private void showToast(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}