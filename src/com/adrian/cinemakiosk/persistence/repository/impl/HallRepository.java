package com.adrian.cinemakiosk.persistence.repository.impl;

import com.adrian.cinemakiosk.persistence.entity.impl.Hall;
import com.google.gson.reflect.TypeToken;
import java.util.List;

/**
 * Репозиторій для роботи з даними про зали кінотеатрів.
 * Наслідує від абстрактного класу {@link BaseRepository}, що використовує Gson для серіалізації та десеріалізації.
 * Зберігає дані про зали кінотеатрів в JSON файлі.
 */
public class HallRepository extends BaseRepository<Hall> {

    /**
     * Конструктор, що ініціалізує репозиторій для роботи з файлами даних про зали.
     * Використовує тип {@link List<Hall>} для визначення формату даних.
     */
    public HallRepository() {
        super("data/halls.json", new TypeToken<List<Hall>>() {
        }.getType());
    }

    /**
     * Отримує ідентифікатор залу.
     *
     * @param hall зал, для якого потрібно отримати ідентифікатор.
     * @return ідентифікатор залу.
     */
    @Override
    protected int getId(Hall hall) {
        return hall.getId();
    }

    /**
     * Перевіряє, чи відповідає значення певного поля заданому значенню.
     * Підтримує фільтрацію за полями "name" і "capacity".
     *
     * @param hall об'єкт залу, для якого перевіряється поле.
     * @param fieldName ім'я поля для перевірки.
     * @param value значення для порівняння.
     * @return {@code true}, якщо значення поля відповідає заданому, {@code false} в іншому випадку.
     */
    @Override
    protected boolean matchesField(Hall hall, String fieldName, Object value) {
        switch (fieldName) {
            case "name":
                return hall.getName().equals(value);
            case "capacity":
                return hall.getSeatCapacity() == (int) value;
            default:
                return false;
        }
    }
}
