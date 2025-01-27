package com.adrian.cinemakiosk.domain.entitys.impl;

public class Session {

    private int id;
    private String dateTime; // Дата і час проведення
    private String format; // Наприклад, "2D", "3D", "IMAX"
    private int movieId; // Foreign Key до Movie
    private int hallId; // Foreign Key до Hall

    public Session(int id, String dateTime, String format, int movieId, int hallId) {
        this.id = id;
        this.dateTime = dateTime;
        this.format = format;
        this.movieId = movieId;
        this.hallId = hallId;
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

    @Override
    public String toString() {
        return "Session{" +
            "id=" + id +
            ", dateTime='" + dateTime + '\'' +
            ", format='" + format + '\'' +
            ", movieId=" + movieId +
            ", hallId=" + hallId +
            '}';
    }
}
