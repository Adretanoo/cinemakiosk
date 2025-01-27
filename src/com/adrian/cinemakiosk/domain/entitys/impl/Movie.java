package com.adrian.cinemakiosk.domain.entitys.impl;

public class Movie {

    private int id;
    private String title;
    private String genre;
    private int duration; // Тривалість у хвилинах
    private String ageRestriction; // Наприклад, "12+", "16+"
    private String description;
    private String director;
    private String actors;

    public Movie(int id, String title, String genre, int duration, String ageRestriction,
        String description, String director, String actors) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.ageRestriction = ageRestriction;
        this.description = description;
        this.director = director;
        this.actors = actors;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(String ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    @Override
    public String toString() {
        return "Movie{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", genre='" + genre + '\'' +
            ", duration=" + duration +
            ", ageRestriction='" + ageRestriction + '\'' +
            ", description='" + description + '\'' +
            ", director='" + director + '\'' +
            ", actors='" + actors + '\'' +
            '}';
    }
}
