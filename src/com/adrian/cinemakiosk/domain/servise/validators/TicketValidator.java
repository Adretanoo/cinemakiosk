package com.adrian.cinemakiosk.domain.servise.validators;

import com.adrian.cinemakiosk.persistence.entity.impl.Ticket;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас для валідації даних про квиток.
 * Виконує перевірки на правильність введених даних для квитка.
 */
public class TicketValidator {

    /**
     * Перевіряє дані про квиток на коректність.
     * Якщо є помилки, викидається виключення IllegalArgumentException з повідомленням про помилки.
     *
     * @param ticket Об'єкт квитка, дані якого потрібно перевірити.
     * @throws IllegalArgumentException Якщо будь-яке з полів квитка є некоректним.
     */
    public static void validate(Ticket ticket) {
        List<String> errors = new ArrayList<>();

        // Перевірка на null
        if (ticket == null) {
            errors.add("\nКвиток не може бути null");
        } else {
            // Перевірка на коректність ID квитка
            if (ticket.getId() <= 0) {
                errors.add("\nID квитка має бути додатнім числом");
            }

            // Перевірка на коректність ціни квитка
            if (ticket.getPrice() <= 0) {
                errors.add("\nЦіна квитка має бути більшою за 0");
            }

            // Перевірка на коректність ID сеансу
            if (ticket.getSessionId() <= 0) {
                errors.add("\nID сеансу має бути додатнім числом");
            }

            // Перевірка на коректність статусу квитка
            if (ticket.getStatus() == null || ticket.getStatus().trim().isEmpty()) {
                errors.add("\nСтатус квитка не може бути порожнім");
            }

            // Перевірка на коректність типу квитка
            if (ticket.getTicketType() == null || ticket.getTicketType().trim().isEmpty()) {
                errors.add("\nТип квитка не може бути порожнім");
            }

            // Перевірка на коректність ID замовлення
            if (ticket.getOrderId() <= 0) {
                errors.add("\nID замовлення має бути додатнім числом");
            }
        }

        // Якщо є помилки, викидається виключення з усіма повідомленнями
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(" ", errors));
        }
    }
}
