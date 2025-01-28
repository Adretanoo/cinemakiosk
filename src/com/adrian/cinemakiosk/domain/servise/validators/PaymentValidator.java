package com.adrian.cinemakiosk.domain.servise.validators;

import com.adrian.cinemakiosk.persistence.entity.impl.Payment;
import java.util.ArrayList;
import java.util.List;

public class PaymentValidator {

    public static void validate(Payment payment) {
        List<String> errors = new ArrayList<>();

        if (payment == null) {
            errors.add("\nПлатіж не може бути null");
        }
        if (payment.getId() <= 0) {
            errors.add("\nID платежу має бути додатним числом");
        }
        if (payment.getAmount() <= 0) {
            errors.add("\nСума платежу має бути більшою за 0");
        }
        if (payment.getDateTime() == null || payment.getDateTime().trim().isEmpty()) {
            errors.add("\nДата і час платежу не можуть бути порожніми");
        }
        if (payment.getPaymentMethod() == null || payment.getPaymentMethod().trim().isEmpty()) {
            errors.add("\nМетод платежу не може бути порожнім");
        }
        if (payment.getOrderId() <= 0) {
            errors.add("\nID замовлення має бути додатним числом");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(" ", errors));
        }
    }
}
