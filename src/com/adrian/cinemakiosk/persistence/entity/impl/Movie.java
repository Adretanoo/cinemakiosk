package com.adrian.cinemakiosk.persistence.entity.impl;

public class Movie {
    private int id;
    private String title;
    private String description;
    private int year;
    private double rating;
    private String posterUrl;
    private String genre;
    private String director;


    public Movie(String title, String description, int year, double rating,
        String posterUrl, String genre, String director) {
        this.title = title;
        this.description = description;
        this.year = year;
        this.rating = rating;
        this.posterUrl = posterUrl;
        this.genre = genre;
        this.director = director;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getYear() {
        return year;
    }

    public double getRating() {
        return rating;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getGenre() {
        return genre;
    }

    public String getDirector() {
        return director;
    }
}
