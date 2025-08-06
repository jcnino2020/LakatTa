package com.example.lakatta;

public class Hotel {
    private final int imageResId;
    private final String title;
    private final String description;
    private final String rating;

    public Hotel(int imageResId, String title, String description, String rating) {
        this.imageResId = imageResId;
        this.title = title;
        this.description = description;
        this.rating = rating;
    }

    public int getImageResId() { return imageResId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getRating() { return rating; }
}