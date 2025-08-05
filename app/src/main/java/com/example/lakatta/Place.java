package com.example.lakatta;

import android.os.Parcel;
import android.os.Parcelable;

public class Place implements Parcelable {
    private final int imageResId;
    private final String title;
    private final String shortDescription; // Renamed for clarity
    private final String longDescription;  // <-- NEW FIELD
    private final String stars;

    // --- UPDATED CONSTRUCTOR ---
    public Place(int imageResId, String title, String shortDescription, String longDescription, String stars) {
        this.imageResId = imageResId;
        this.title = title;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription; // <-- INITIALIZE NEW FIELD
        this.stars = stars;
    }

    // --- UPDATED GETTERS ---
    public int getImageResId() { return imageResId; }
    public String getTitle() { return title; }
    public String getShortDescription() { return shortDescription; } // Renamed for clarity
    public String getLongDescription() { return longDescription; }   // <-- NEW GETTER
    public String getStars() { return stars; }

    // --- PARCELABLE CODE ---
    protected Place(Parcel in) {
        imageResId = in.readInt();
        title = in.readString();
        shortDescription = in.readString();
        longDescription = in.readString(); // <-- READ FROM PARCEL
        stars = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageResId);
        dest.writeString(title);
        dest.writeString(shortDescription);
        dest.writeString(longDescription); // <-- WRITE TO PARCEL
        dest.writeInt(flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }
        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };
}