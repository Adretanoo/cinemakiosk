package com.adrian.cinemakiosk.domain.servise.validators;

import com.adrian.cinemakiosk.persistence.entity.impl.Movie;
import java.util.ArrayList;
import java.util.List;

public class MovieValidator {

    public static void validate(Movie movie) {
        List<String> errors = new ArrayList<>();

        if (movie == null) {
            errors.add("\nФільм не може бути null");
        }
        if (movie.getId() <= 0) {
            errors.add("\nID фільму має бути додатним числом");
        }
        if (movie.getTitle() == null || movie.getTitle().trim().isEmpty()) {
            errors.add("\nНазва фільму не може бути порожньою");
        }
        if (movie.getGenre() == null || movie.getGenre().trim().isEmpty()) {
            errors.add("\nЖанр фільму не може бути порожнім");
        }
        if (movie.getDuration() <= 0) {
            errors.add("\nТривалість фільму має бути більшою за 0");
        }
        if (movie.getAgeRestriction() == null || movie.getAgeRestriction().trim().isEmpty()) {
            errors.add("\nВікове обмеження не може бути порожнім");
        }
        if (movie.getDescription() == null || movie.getDescription().trim().isEmpty()) {
            errors.add("\nОпис фільму не може бути порожнім");
        }
        if (movie.getDirector() == null || movie.getDirector().trim().isEmpty()) {
            errors.add("\nРежисер не може бути порожнім");
        }
        if (movie.getActors() == null || movie.getActors().trim().isEmpty()) {
            errors.add("\nАктори не можуть бути порожніми");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(" ", errors));
        }
    }
}
