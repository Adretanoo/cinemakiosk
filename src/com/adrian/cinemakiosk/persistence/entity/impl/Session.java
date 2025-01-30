package com.adrian.cinemakiosk.persistence.entity.impl;

import java.util.List;

/**
 * Клас, що представляє сеанс у кінотеатрі.
 * Містить інформацію про дату та час сеансу, формат, ідентифікатор фільму,
 * ідентифікатор залу та список доступних місць.
 */
public class Session {

    private int id;
    private String dateTime;
    private String format;
    private int movieId;
    private int hallId;
    private List<Seat> seats;

    /**
     * Конструктор для створення сеансу.
     *
     * @param id ідентифікатор сеансу.
     * @param dateTime дата та час сеансу.
     * @param format формат сеансу (наприклад, 2D, 3D).
     * @param movieId ідентифікатор фільму, який показується.
     * @param hallId ідентифікатор кінозалу.
     * @param seats список місць, доступних на сеансі.
     */
    public Session(int id, String dateTime, String format, int movieId, int hallId, List<Seat> seats) {
        this.id = id;
        this.dateTime = dateTime;
        this.format = format;
        this.movieId = movieId;
        this.hallId = hallId;
        this.seats = seats;
    }

    /**
     * Отримує ідентифікатор сеансу.
     *
     * @return ідентифікатор сеансу.
     */
    public int getId() {
        return id;
    }

    /**
     * Встановлює ідентифікатор сеансу.
     *
     * @param id новий ідентифікатор сеансу.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Отримує дату та час сеансу.
     *
     * @return дата та час сеансу.
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * Встановлює дату та час сеансу.
     *
     * @param dateTime нова дата та час сеансу.
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Отримує формат сеансу.
     *
     * @return формат сеансу.
     */
    public String getFormat() {
        return format;
    }

    /**
     * Встановлює формат сеансу.
     *
     * @param format новий формат сеансу.
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * Отримує ідентифікатор фільму, що показується на сеансі.
     *
     * @return ідентифікатор фільму.
     */
    public int getMovieId() {
        return movieId;
    }

    /**
     * Встановлює ідентифікатор фільму для цього сеансу.
     *
     * @param movieId новий ідентифікатор фільму.
     */
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    /**
     * Отримує ідентифікатор кінозалу, де проходить сеанс.
     *
     * @return ідентифікатор кінозалу.
     */
    public int getHallId() {
        return hallId;
    }

    /**
     * Встановлює ідентифікатор кінозалу для цього сеансу.
     *
     * @param hallId новий ідентифікатор кінозалу.
     */
    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    /**
     * Отримує список місць, доступних на сеансі.
     *
     * @return список місць.
     */
    public List<Seat> getSeats() {
        return seats;
    }

    /**
     * Встановлює список місць для цього сеансу.
     *
     * @param seats новий список місць.
     */
    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    /**
     * Повертає строкове представлення сеансу.
     *
     * @return строкове представлення об'єкта Session.
     */
    @Override
    public String toString() {
        return "Session{" +
            "id=" + id +
            ", dateTime='" + dateTime + '\'' +
            ", format='" + format + '\'' +
            ", movieId=" + movieId +
            ", hallId=" + hallId +
            ", seats=" + seats +
            '}';
    }
}
