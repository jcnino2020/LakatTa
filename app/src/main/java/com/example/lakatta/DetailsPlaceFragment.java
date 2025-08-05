package com.example.lakatta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

public class DetailsPlaceFragment extends Fragment {
    public static DetailsPlaceFragment newInstance() {
        return new DetailsPlaceFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // For now, this just shows the placeholder layout
        return inflater.inflate(R.layout.fragment_details_place, container, false);
    }
}