package com.adrian.cinemakiosk.persistence.entity.impl;

public class Film {

    private String title;
    private String genre;
    private double rating;
    private String description;
    private int releaseYear;

    // Конструктор
    public Film(String title, String genre, double rating, String description, int releaseYear) {
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.description = description;
        this.releaseYear = releaseYear;
    }

    // Геттери та сеттери
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    // Метод для виведення інформації про фільм
    @Override
    public String toString() {
        return "Фільм: " + title + "\nЖанр: " + genre + "\nРейтинг: " + rating + "\nОпис: "
            + description + "\nРік випуску: " + releaseYear;
    }
}

