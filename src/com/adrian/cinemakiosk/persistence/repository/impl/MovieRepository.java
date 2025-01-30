package com.adrian.cinemakiosk.persistence.repository.impl;

import com.adrian.cinemakiosk.persistence.entity.impl.Movie;
import com.google.gson.reflect.TypeToken;
import java.util.List;

/**
 * Репозиторій для роботи з даними про фільми.
 * Наслідує від абстрактного класу {@link BaseRepository}, що використовує Gson для серіалізації та десеріалізації.
 * Зберігає дані про фільми в JSON файлі.
 */
public class MovieRepository extends BaseRepository<Movie> {

    /**
     * Конструктор, що ініціалізує репозиторій для роботи з файлами даних про фільми.
     * Використовує тип {@link List<Movie>} для визначення формату даних.
     */
    public MovieRepository() {
        super("data/movies.json", new TypeToken<List<Movie>>() {
        }.getType());
    }

    /**
     * Отримує ідентифікатор фільму.
     *
     * @param movie фільм, для якого потрібно отримати ідентифікатор.
     * @return ідентифікатор фільму.
     */
    @Override
    protected int getId(Movie movie) {
        return movie.getId();
    }

    /**
     * Перевіряє, чи відповідає значення певного поля заданому значенню.
     * Підтримує фільтрацію за полями "title", "genre", "rating" і "year".
     *
     * @param movie об'єкт фільму, для якого перевіряється поле.
     * @param fieldName ім'я поля для перевірки.
     * @param value значення для порівняння.
     * @return {@code true}, якщо значення поля відповідає заданому, {@code false} в іншому випадку.
     */
    @Override
    protected boolean matchesField(Movie movie, String fieldName, Object value) {
        switch (fieldName) {
            case "title":
                return movie.getTitle().equals(value);
            case "genre":
                return movie.getGenre().equals(value);
            case "rating":
                return movie.getRating() == (Double) value;
            case "year":
                return movie.getYear() == (Integer) value;
            default:
                return false;
        }
    }
}
