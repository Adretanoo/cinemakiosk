package com.adrian.cinemakiosk.persistence.repository.impl;

import com.adrian.cinemakiosk.persistence.entity.impl.Payment;
import com.google.gson.reflect.TypeToken;
import java.util.List;

/**
 * Репозиторій для роботи з даними про платежі.
 * Наслідує від абстрактного класу {@link BaseRepository}, що використовує Gson для серіалізації та десеріалізації.
 * Зберігає дані про платежі в JSON файлі.
 */
public class PaymentRepository extends BaseRepository<Payment> {

    /**
     * Конструктор, що ініціалізує репозиторій для роботи з файлами даних про платежі.
     * Використовує тип {@link List<Payment>} для визначення формату даних.
     */
    public PaymentRepository() {
        super("data/payments.json", new TypeToken<List<Payment>>() {
        }.getType());
    }

    /**
     * Отримує ідентифікатор платежу.
     *
     * @param payment платіж, для якого потрібно отримати ідентифікатор.
     * @return ідентифікатор платежу.
     */
    @Override
    protected int getId(Payment payment) {
        return payment.getId();
    }

    /**
     * Перевіряє, чи відповідає значення певного поля заданому значенню.
     * Підтримує фільтрацію за полями "orderId" і "amount".
     *
     * @param payment об'єкт платежу, для якого перевіряється поле.
     * @param fieldName ім'я поля для перевірки.
     * @param value значення для порівняння.
     * @return {@code true}, якщо значення поля відповідає заданому, {@code false} в іншому випадку.
     */
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
