package com.example.lakatta;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private final List<Hotel> hotelList;

    public HotelAdapter(List<Hotel> hotelList) {
        this.hotelList = hotelList;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_hotel, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        holder.imageView.setImageResource(hotel.getImageResId());
        holder.titleView.setText(hotel.getTitle());
        holder.descriptionView.setText(hotel.getDescription());
        holder.ratingView.setText(hotel.getRating());
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public static class HotelViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleView, descriptionView, ratingView;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.hotel_image);
            titleView = itemView.findViewById(R.id.hotel_title);
            descriptionView = itemView.findViewById(R.id.hotel_description);
            ratingView = itemView.findViewById(R.id.hotel_stars);
        }
    }
}