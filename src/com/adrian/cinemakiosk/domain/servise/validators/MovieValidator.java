package com.adrian.cinemakiosk.domain.servise.validators;

import com.adrian.cinemakiosk.persistence.entity.impl.Movie;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас для валідації даних про фільм.
 * Виконує перевірки на правильність введених даних для фільму.
 */
public class MovieValidator {

    /**
     * Перевіряє дані про фільм на коректність.
     * Якщо є помилки, повертається список з повідомленнями про помилки.
     * Якщо фільм є null, повертається список з повідомленням про це.
     *
     * @param movie Об'єкт фільму, дані якого потрібно перевірити.
     * @return Список з помилками валидації або порожній список, якщо перевірка пройшла успішно.
     */
    public static List<String> validate(Movie movie) {
        List<String> errors = new ArrayList<>();

        // Перевірка на null
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

    /**
     * Перевіряє правильність назви фільму.
     *
     * @param movie Об'єкт фільму, який перевіряється.
     * @param errors Список помилок валідації, куди додаються повідомлення.
     */
    private static void validateTitle(Movie movie, List<String> errors) {
        if (movie.getTitle() == null || movie.getTitle().trim().isEmpty()) {
            errors.add("Назва фільму не може бути порожньою.");
        }
    }

    /**
     * Перевіряє правильність опису фільму.
     *
     * @param movie Об'єкт фільму, який перевіряється.
     * @param errors Список помилок валідації, куди додаються повідомлення.
     */
    private static void validateDescription(Movie movie, List<String> errors) {
        if (movie.getDescription() == null || movie.getDescription().trim().isEmpty()) {
            errors.add("Опис фільму не може бути порожнім.");
        }
    }

    /**
     * Перевіряє правильність року випуску фільму.
     *
     * @param movie Об'єкт фільму, який перевіряється.
     * @param errors Список помилок валідації, куди додаються повідомлення.
     */
    private static void validateYear(Movie movie, List<String> errors) {
        if (movie.getYear() <= 0) {
            errors.add("Рік фільму має бути більшим за 0.");
        }
    }

    /**
     * Перевіряє правильність рейтингу фільму.
     * Рейтинг має бути в діапазоні від 0 до 10.
     *
     * @param movie Об'єкт фільму, який перевіряється.
     * @param errors Список помилок валідації, куди додаються повідомлення.
     */
    private static void validateRating(Movie movie, List<String> errors) {
        if (movie.getRating() < 0 || movie.getRating() > 10) {
            errors.add("Рейтинг фільму має бути в діапазоні від 0 до 10.");
        }
    }

    /**
     * Перевіряє правильність URL постера фільму.
     *
     * @param movie Об'єкт фільму, який перевіряється.
     * @param errors Список помилок валідації, куди додаються повідомлення.
     */
    private static void validatePosterUrl(Movie movie, List<String> errors) {
        if (movie.getPosterUrl() == null || movie.getPosterUrl().trim().isEmpty()) {
            errors.add("URL постера не може бути порожнім.");
        }
    }

    /**
     * Перевіряє, чи є фільм валідним.
     *
     * @param movie Об'єкт фільму, який перевіряється.
     * @return true, якщо фільм є валідним (немає помилок), false в іншому випадку.
     */
    public static boolean isValid(Movie movie) {
        return validate(movie).isEmpty();
    }
}
