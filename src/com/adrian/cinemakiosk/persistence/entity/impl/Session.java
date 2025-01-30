package com.adrian.cinemakiosk.persistence.entity.impl;

import java.util.List;

public class Session {

    private int id;
    private String dateTime;
    private String format;
    private int movieId;
    private int hallId;
    private List<Seat> seats;

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
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

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
