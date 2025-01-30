package com.adrian.cinemakiosk.domain.servise.validators;

import com.adrian.cinemakiosk.persistence.entity.impl.Session;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас для валідації даних про сеанс.
 * Виконує перевірки на правильність введених даних для сеансу.
 */
public class SessionValidator {

    /**
     * Перевіряє дані про сеанс на коректність.
     * Якщо є помилки, викидається виключення IllegalArgumentException з повідомленням про помилки.
     *
     * @param session Об'єкт сеансу, дані якого потрібно перевірити.
     * @throws IllegalArgumentException Якщо будь-яке з полів сеансу є некоректним.
     */
    public static void validate(Session session) {
        List<String> errors = new ArrayList<>();

        // Перевірка на null
        if (session == null) {
            errors.add("\nСеанс не може бути null");
        }

        // Перевірка на коректність ID сеансу
        if (session.getId() <= 0) {
            errors.add("\nID сеансу має бути додатним числом");
        }

        // Перевірка на коректність дати та часу сеансу
        if (session.getDateTime() == null || session.getDateTime().trim().isEmpty()) {
            errors.add("\nДата і час сеансу не можуть бути порожніми");
        }

        // Перевірка на коректність формату сеансу
        if (session.getFormat() == null || session.getFormat().trim().isEmpty()) {
            errors.add("\nФормат сеансу не може бути порожнім");
        }

        // Перевірка на коректність ID фільму
        if (session.getMovieId() <= 0) {
            errors.add("\nID фільму має бути додатним числом");
        }

        // Перевірка на коректність ID залу
        if (session.getHallId() <= 0) {
            errors.add("\nID залу має бути додатним числом");
        }

        // Якщо є помилки, викидається виключення з усіма повідомленнями
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(" ", errors));
        }
    }
}
