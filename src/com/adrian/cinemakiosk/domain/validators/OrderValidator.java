package com.adrian.cinemakiosk.domain.validators;

import com.adrian.cinemakiosk.domain.entitys.impl.Order;
import java.util.ArrayList;
import java.util.List;

public class OrderValidator {

    public static void validate(Order order) {
        List<String> errors = new ArrayList<>();

        if (order == null) {
            errors.add("\nЗамовлення не може бути null");
        }
        if (order.getId() <= 0) {
            errors.add("\nID замовлення має бути додатним числом");
        }
        if (order.getDataTime() == null || order.getDataTime().trim().isEmpty()) {
            errors.add("\nДата і час замовлення не можуть бути порожніми");
        }
        if (order.getStatus() == null || order.getStatus().trim().isEmpty()) {
            errors.add("\nСтатус замовлення не може бути порожнім");
        }
        if (order.getTotalPrice() <= 0) {
            errors.add("\nЗагальна сума замовлення має бути більшою за 0");
        }
        if (order.getPaymentMethod() == null || order.getPaymentMethod().trim().isEmpty()) {
            errors.add("\nМетод платежу не може бути порожнім");
        }
        if (order.getUserId() <= 0) {
            errors.add("\nID користувача має бути додатним числом");
        }
        if (order.getTickets() == null || order.getTickets().isEmpty()) {
            errors.add("\nЗамовлення повинно містити квитки");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(" ", errors));
        }
    }
}
