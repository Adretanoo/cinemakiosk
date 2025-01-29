package com.adrian.cinemakiosk.domain.servise.validators;

import com.adrian.cinemakiosk.persistence.entity.impl.Movie;
import java.util.ArrayList;
import java.util.List;

public class MovieValidator {

    public static List<String> validate(Movie movie) {
        List<String> errors = new ArrayList<>();

        if (movie == null) {
            errors.add("Фільм не може бути null.");
            return errors; // Якщо movie == null, перевірки не проводяться
        }

        // Перевірка полів фільму
        validateTitle(movie, errors);
        validateDescription(movie, errors);
        validateYear(movie, errors);
        validateRating(movie, errors);
        validatePosterUrl(movie, errors);

        return errors;
    }

    private static void validateTitle(Movie movie, List<String> errors) {
        if (movie.getTitle() == null || movie.getTitle().trim().isEmpty()) {
            errors.add("Назва фільму не може бути порожньою.");
        }
    }

    private static void validateDescription(Movie movie, List<String> errors) {
        if (movie.getDescription() == null || movie.getDescription().trim().isEmpty()) {
            errors.add("Опис фільму не може бути порожнім.");
        }
    }

    private static void validateYear(Movie movie, List<String> errors) {
        if (movie.getYear() <= 0) {
            errors.add("Рік фільму має бути більшим за 0.");
        }
    }

    private static void validateRating(Movie movie, List<String> errors) {
        if (movie.getRating() < 0 || movie.getRating() > 10) {
            errors.add("Рейтинг фільму має бути в діапазоні від 0 до 10.");
        }
    }

    private static void validatePosterUrl(Movie movie, List<String> errors) {
        if (movie.getPosterUrl() == null || movie.getPosterUrl().trim().isEmpty()) {
            errors.add("URL постера не може бути порожнім.");
        }
    }

    // Додатковий метод для перевірки валідності фільму
    public static boolean isValid(Movie movie) {
        return validate(movie).isEmpty();
    }
}
