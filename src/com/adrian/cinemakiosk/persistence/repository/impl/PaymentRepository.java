package com.adrian.cinemakiosk.persistence.repository.impl;

import com.adrian.cinemakiosk.persistence.entity.impl.Payment;
import com.google.gson.reflect.TypeToken;
import java.util.List;

public class PaymentRepository extends BaseRepository<Payment> {

    public PaymentRepository() {
        super("data/payments.json", new TypeToken<List<Payment>>() {
        }.getType());
    }

    @Override
    protected int getId(Payment payment) {
        return payment.getId();
    }

    @Override
    protected boolean matchesField(Payment payment, String fieldName, Object value) {
        switch (fieldName) {
            case "orderId":
                return payment.getOrderId() == (int) value;
            case "amount":
                return payment.getAmount() == (double) value;
            default:
                return false;
        }
    }
}