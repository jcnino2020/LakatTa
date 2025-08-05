package com.example.lakatta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

public class ReviewsPlaceFragment extends Fragment {
    public static ReviewsPlaceFragment newInstance() {
        return new ReviewsPlaceFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // For now, this just shows the placeholder layout
        return inflater.inflate(R.layout.fragment_reviews_place, container, false);
    }
}