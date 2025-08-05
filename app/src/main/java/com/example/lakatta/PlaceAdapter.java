package com.example.lakatta;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.widget.Toast;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    private final List<Place> placeList;
    private OnItemClickListener listener; // <-- ADD THIS

    // --- ADD THIS INTERFACE ---
    public interface OnItemClickListener {
        void onItemClick(Place place);
    }

    // --- ADD THIS METHOD ---
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public PlaceAdapter(List<Place> placeList) {
        this.placeList = placeList;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_place, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        Place place = placeList.get(position);
        holder.imageViewPlace.setImageResource(place.getImageResId());
        holder.textViewTitle.setText(place.getTitle());
        holder.textViewDescription.setText(place.getShortDescription()); // <--- CORRECT: Use the new method name
        holder.textViewStars.setText(place.getStars());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(place);
            }
        });
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public static class PlaceViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPlace;
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewStars;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPlace = itemView.findViewById(R.id.imageViewPlace);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewStars = itemView.findViewById(R.id.textViewStars);
        }
    }
}