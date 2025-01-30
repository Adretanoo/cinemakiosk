package com.adrian.cinemakiosk.domain.servise.validators;

import com.adrian.cinemakiosk.persistence.entity.impl.Payment;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас для валідації даних про платіж.
 * Виконує перевірки на правильність введених даних для платежу.
 */
public class PaymentValidator {

    /**
     * Перевіряє дані про платіж на коректність.
     * Якщо є помилки, викидається виключення IllegalArgumentException з повідомленням про помилки.
     *
     * @param payment Об'єкт платежу, дані якого потрібно перевірити.
     * @throws IllegalArgumentException Якщо будь-яке з полів платежу є некоректним.
     */
    public static void validate(Payment payment) {
        List<String> errors = new ArrayList<>();

        // Перевірка на null
        if (payment == null) {
            errors.add("\nПлатіж не може бути null");
        }

        // Перевірка на коректність ID платежу
        if (payment.getId() <= 0) {
            errors.add("\nID платежу має бути додатним числом");
        }

        // Перевірка на коректність суми платежу
        if (payment.getAmount() <= 0) {
            errors.add("\nСума платежу має бути більшою за 0");
        }

        // Перевірка на коректність дати та часу платежу
        if (payment.getDateTime() == null || payment.getDateTime().trim().isEmpty()) {
            errors.add("\nДата і час платежу не можуть бути порожніми");
        }

        // Перевірка на коректність методу платежу
        if (payment.getPaymentMethod() == null || payment.getPaymentMethod().trim().isEmpty()) {
            errors.add("\nМетод платежу не може бути порожнім");
        }

        // Перевірка на коректність ID замовлення
        if (payment.getOrderId() <= 0) {
            errors.add("\nID замовлення має бути додатним числом");
        }

        // Якщо є помилки, викидається виключення з усіма повідомленнями
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(" ", errors));
        }
    }
}
