package com.adrian.cinemakiosk.domain.servise.validators;

import com.adrian.cinemakiosk.persistence.entity.impl.Order;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас для валідації даних про замовлення.
 * Виконує перевірки на правильність введених даних для замовлення.
 */
public class OrderValidator {

    /**
     * Перевіряє дані про замовлення на коректність.
     * Якщо є помилки, викидається виключення IllegalArgumentException з повідомленням про помилки.
     *
     * @param order Об'єкт замовлення, дані якого потрібно перевірити.
     * @throws IllegalArgumentException Якщо будь-яке з полів замовлення є некоректним.
     */
    public static void validate(Order order) {
        List<String> errors = new ArrayList<>();

        // Перевірка на null
        if (order == null) {
            errors.add("\nЗамовлення не може бути null");
        }

        // Перевірка на коректність ID замовлення
        if (order.getId() <= 0) {
            errors.add("\nID замовлення має бути додатним числом");
        }

        // Перевірка на коректність дати та часу замовлення
        if (order.getDataTime() == null || order.getDataTime().trim().isEmpty()) {
            errors.add("\nДата і час замовлення не можуть бути порожніми");
        }

        // Перевірка на коректність статусу замовлення
        if (order.getStatus() == null || order.getStatus().trim().isEmpty()) {
            errors.add("\nСтатус замовлення не може бути порожнім");
        }

        // Перевірка на коректність загальної суми замовлення
        if (order.getTotalPrice() <= 0) {
            errors.add("\nЗагальна сума замовлення має бути більшою за 0");
        }

        // Перевірка на коректність методу платежу
        if (order.getPaymentMethod() == null || order.getPaymentMethod().trim().isEmpty()) {
            errors.add("\nМетод платежу не може бути порожнім");
        }

        // Перевірка на коректність ID користувача
        if (order.getUserId() <= 0) {
            errors.add("\nID користувача має бути додатним числом");
        }

        // Перевірка на наявність квитків у замовленні
        if (order.getTickets() == null || order.getTickets().isEmpty()) {
            errors.add("\nЗамовлення повинно містити квитки");
        }

        // Якщо є помилки, викидається виключення з усіма повідомленнями
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(" ", errors));
        }
    }
}
