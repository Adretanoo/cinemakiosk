package com.adrian.cinemakiosk.persistence.entity.impl;

/**
 * Клас, що представляє фільм з його деталями.
 * Містить інформацію про заголовок, опис, рік випуску, рейтинг, URL постера, жанр та режисера фільму.
 */
public class Movie {

    private int id;
    private String title;
    private String description;
    private int year;
    private double rating;
    private String posterUrl;
    private String genre;
    private String director;

    /**
     * Конструктор для створення об'єкта фільму.
     *
     * @param title       заголовок фільму.
     * @param description опис фільму.
     * @param year        рік випуску фільму.
     * @param rating      рейтинг фільму.
     * @param posterUrl   URL постера фільму.
     * @param genre       жанр фільму.
     */
    public Movie(String title, String description, int year, double rating,
        String posterUrl, String genre) {
        this.title = title;
        this.description = description;
        this.year = year;
        this.rating = rating;
        this.posterUrl = posterUrl;
        this.genre = genre;
        this.director = director;
    }

    /**
     * Отримує ідентифікатор фільму.
     *
     * @return ідентифікатор фільму.
     */
    public int getId() {
        return id;
    }

    /**
     * Встановлює ідентифікатор фільму.
     *
     * @param id новий ідентифікатор фільму.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Отримує заголовок фільму.
     *
     * @return заголовок фільму.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Отримує опис фільму.
     *
     * @return опис фільму.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Отримує рік випуску фільму.
     *
     * @return рік випуску фільму.
     */
    public int getYear() {
        return year;
    }

    /**
     * Отримує рейтинг фільму.
     *
     * @return рейтинг фільму.
     */
    public double getRating() {
        return rating;
    }

    /**
     * Отримує URL постера фільму.
     *
     * @return URL постера фільму.
     */
    public String getPosterUrl() {
        return posterUrl;
    }

    /**
     * Отримує жанр фільму.
     *
     * @return жанр фільму.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Отримує режисера фільму.
     *
     * @return режисер фільму.
     */
    public String getDirector() {
        return director;
    }
}
