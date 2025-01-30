package com.adrian.cinemakiosk.domain.servise.validators;

import com.adrian.cinemakiosk.persistence.entity.impl.Seat;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас для валідації даних про місце в залі.
 * Виконує перевірки на правильність введених даних для місця.
 */
public class SeatValidator {

    /**
     * Перевіряє дані про місце в залі на коректність.
     * Якщо є помилки, викидається виключення IllegalArgumentException з повідомленням про помилки.
     *
     * @param seat Об'єкт місця в залі, дані якого потрібно перевірити.
     * @throws IllegalArgumentException Якщо будь-яке з полів місця є некоректним.
     */
    public static void validate(Seat seat) {
        List<String> errors = new ArrayList<>();

        // Перевірка на null
        if (seat == null) {
            errors.add("\nМісце не може бути null");
        }

        // Перевірка на коректність ID місця
        if (seat.getId() <= 0) {
            errors.add("\nID місця має бути додатним числом");
        }

        // Перевірка на коректність номера місця
        if (seat.getSeatNumber() <= 0) {
            errors.add("\nНомер місця має бути додатним числом");
        }

        // Перевірка на коректність номера ряду
        if (seat.getRow() <= 0) {
            errors.add("\nНомер ряду має бути додатним числом");
        }

        // Перевірка на коректність ID залу
        if (seat.getHallId() <= 0) {
            errors.add("\nID залу має бути додатним числом");
        }

        // Якщо є помилки, викидається виключення з усіма повідомленнями
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(" ", errors));
        }
    }
}
