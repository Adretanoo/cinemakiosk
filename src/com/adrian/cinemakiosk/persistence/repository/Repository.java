package com.adrian.cinemakiosk.persistence.repository;

import java.util.List;

/**
 * Інтерфейс для роботи з репозиторієм об'єктів.
 * Він визначає стандартні операції для додавання, отримання, оновлення та видалення об'єктів.
 *
 * @param <T> тип об'єкта, з яким працює репозиторій.
 */
public interface Repository<T> {

    /**
     * Додає новий об'єкт до репозиторію.
     *
     * @param entity об'єкт, який потрібно додати.
     */
    void add(T entity);

    /**
     * Отримує об'єкт за його ідентифікатором.
     *
     * @param id унікальний ідентифікатор об'єкта.
     * @return об'єкт з вказаним ідентифікатором, або null, якщо не знайдено.
     */
    T getById(int id);

    /**
     * Отримує всі об'єкти з репозиторію.
     *
     * @return список всіх об'єктів.
     */
    List<T> getAll();

    /**
     * Оновлює існуючий об'єкт в репозиторії.
     *
     * @param entity об'єкт з новими даними для оновлення.
     */
    void update(T entity);

    /**
     * Видаляє об'єкт за його ідентифікатором.
     *
     * @param id ідентифікатор об'єкта, який потрібно видалити.
     */
    void deleteById(int id);

    /**
     * Фільтрує об'єкти за вказаним полем та значенням.
     *
     * @param fieldName назва поля, за яким буде здійснено фільтрацію.
     * @param value значення, яке повинно бути у вказаному полі для того, щоб об'єкт потрапив у результат.
     * @return список об'єктів, що відповідають фільтру.
     */
    List<T> filter(String fieldName, Object value);
}
