package com.example.lakatta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class AboutPlaceFragment extends Fragment {

    // A key for the argument bundle
    private static final String ARG_PLACE_DESCRIPTION = "place_description";

    public AboutPlaceFragment() {
        // Required empty public constructor
    }

    /**
     * The recommended way to create a new instance of this fragment
     * and pass data to it.
     */
    public static AboutPlaceFragment newInstance(String description) {
        AboutPlaceFragment fragment = new AboutPlaceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PLACE_DESCRIPTION, description);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_place, container, false);

        // Find the TextView in the fragment's layout
        TextView descriptionTextView = view.findViewById(R.id.textViewDescription);

        // Check if arguments were passed to this fragment
        if (getArguments() != null) {
            String description = getArguments().getString(ARG_PLACE_DESCRIPTION);
            descriptionTextView.setText(description);
        }

        return view;
    }
}