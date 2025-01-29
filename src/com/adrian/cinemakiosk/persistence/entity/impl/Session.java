package com.adrian.cinemakiosk.persistence.entity.impl;

import java.util.List;

public class Session {

    private int id;
    private String dateTime; // Дата і час проведення
    private String format; // Наприклад, "2D", "3D", "IMAX"
    private int movieId; // Foreign Key до Movie
    private int hallId; // Foreign Key до Hall
    private List<Seat> seats; // Додано список місць

    public Session(int id, String dateTime, String format, int movieId, int hallId, List<Seat> seats) {
        this.id = id;
        this.dateTime = dateTime;
        this.format = format;
        this.movieId = movieId;
        this.hallId = hallId;
        this.seats = seats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public List<Seat> getSeats() {
        return seats;  // Додано метод для отримання місць
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;  // Додано метод для встановлення місць
    }

    @Override
    public String toString() {
        return "Session{" +
            "id=" + id +
            ", dateTime='" + dateTime + '\'' +
            ", format='" + format + '\'' +
            ", movieId=" + movieId +
            ", hallId=" + hallId +
            ", seats=" + seats +  // Додано виведення місць
            '}';
    }
}
