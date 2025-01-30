package com.adrian.cinemakiosk.persistence.repository.impl;

import com.adrian.cinemakiosk.persistence.entity.impl.Order;
import com.google.gson.reflect.TypeToken;
import java.util.List;

/**
 * Репозиторій для роботи з даними про замовлення.
 * Наслідує від абстрактного класу {@link BaseRepository}, що використовує Gson для серіалізації та десеріалізації.
 * Зберігає дані про замовлення в JSON файлі.
 */
public class OrderRepository extends BaseRepository<Order> {

    /**
     * Конструктор, що ініціалізує репозиторій для роботи з файлами даних про замовлення.
     * Використовує тип {@link List<Order>} для визначення формату даних.
     */
    public OrderRepository() {
        super("data/orders.json", new TypeToken<List<Order>>() {
        }.getType());
    }

    /**
     * Отримує ідентифікатор замовлення.
     *
     * @param order замовлення, для якого потрібно отримати ідентифікатор.
     * @return ідентифікатор замовлення.
     */
    @Override
    protected int getId(Order order) {
        return order.getId();
    }

    /**
     * Перевіряє, чи відповідає значення певного поля заданому значенню.
     * Підтримує фільтрацію за полями "userId" і "status".
     *
     * @param order об'єкт замовлення, для якого перевіряється поле.
     * @param fieldName ім'я поля для перевірки.
     * @param value значення для порівняння.
     * @return {@code true}, якщо значення поля відповідає заданому, {@code false} в іншому випадку.
     */
    @Override
    protected boolean matchesField(Order order, String fieldName, Object value) {
        switch (fieldName) {
            case "userId":
                return order.getUserId() == (int) value;
            case "status":
                return order.getStatus().equals(value);
            default:
                return false;
        }
    }
}
