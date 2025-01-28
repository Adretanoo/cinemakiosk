package com.adrian.cinemakiosk.persistence.repository.impl;

import com.adrian.cinemakiosk.persistence.entity.impl.Movie;
import com.google.gson.reflect.TypeToken;
import java.util.List;

class MovieRepository extends BaseRepository<Movie> {

    public MovieRepository() {
        super("data/movies.json", new TypeToken<List<Movie>>() {
        }.getType());
    }

    @Override
    protected int getId(Movie movie) {
        return movie.getId();
    }

    @Override
    protected boolean matchesField(Movie movie, String fieldName, Object value) {
        switch (fieldName) {
            case "title":
                return movie.getTitle().equals(value);
            case "genre":
                return movie.getGenre().equals(value);
            default:
                return false;
        }
    }
}
