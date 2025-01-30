package com.adrian.cinemakiosk.domain.servise.validators;

import com.adrian.cinemakiosk.persistence.entity.impl.Hall;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас для валідації даних про зал.
 * Виконує перевірки на правильність введених даних для залу.
 */
public class HallValidator {

    /**
     * Перевіряє дані про зал на коректність.
     * Генерує помилку, якщо будь-яке з полів залу некоректне.
     *
     * @param hall Об'єкт залу, дані якого потрібно перевірити.
     * @throws IllegalArgumentException Якщо будь-яке з полів залу є некоректним.
     */
    public static void validate(Hall hall) {
        List<String> errors = new ArrayList<>();

        // Перевірка на null
        if (hall == null) {
            errors.add("\nЗал не може бути null");
        }

        // Перевірка на коректність ID
        if (hall.getId() <= 0) {
            errors.add("\nID залу має бути додатнім числом");
        }

        // Перевірка на порожню назву
        if (hall.getName() == null || hall.getName().trim().isEmpty()) {
            errors.add("\nНазва залу не може бути порожньою");
        }

        // Перевірка на коректність кількості місць
        if (hall.getSeatCapacity() <= 0) {
            errors.add("\nКількість місць у залі має бути більшою за 0");
        }

        // Якщо є помилки, викидається виключення
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(" ", errors));
        }
    }
}
