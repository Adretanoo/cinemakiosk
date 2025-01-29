package com.adrian.cinemakiosk.persistence.entity.impl;

public class Movie {

    private final int id;
    private final String title;
    private final String description;
    private final int year;
    private final double rating;
    private final String posterUrl;
    private final String genre;
    private String director; // Додаємо поле "Режисер"

    // Конструктор
    public Movie(int id, String title, String description, int year, double rating,
        String posterUrl, String genre, String director) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.year = year;
        this.rating = rating;
        this.posterUrl = posterUrl;
        this.genre = genre;
        this.director = director;
    }

    // Гетери
    public int getId() {
        return id;
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

    public String getDirector() { // Додаємо гетер для режисера
        return director;
    }

    // Сетери (якщо потрібно)
    public void setDirector(String director) {
        this.director = director;
    }
}
