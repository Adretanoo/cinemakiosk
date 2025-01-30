package com.adrian.cinemakiosk.domain.servise.validators;

import com.adrian.cinemakiosk.persistence.entity.impl.User;

/**
 * Клас для валідації даних користувача.
 * Перевіряє правильність введених даних для користувача.
 */
public class UserValidator {

    /**
     * Перевіряє дані користувача на коректність.
     * Викликає окремі методи для перевірки кожного поля користувача.
     *
     * @param user Об'єкт користувача, дані якого потрібно перевірити.
     * @throws IllegalArgumentException Якщо будь-яке з полів користувача є некоректним.
     */
    public static void validate(User user) {
        validateNotNull(user);
        validateId(user.getId());
        validateUsername(user.getUsername());
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
        validateRole(user.getRole());
    }

    /**
     * Перевіряє, чи є користувач null.
     *
     * @param user Об'єкт користувача.
     * @throws IllegalArgumentException Якщо користувач є null.
     */
    public static void validateNotNull(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Користувач не може бути null");
        }
    }

    /**
     * Перевіряє, чи є ID користувача додатним числом.
     *
     * @param id ID користувача.
     * @throws IllegalArgumentException Якщо ID користувача є непозитивним числом.
     */
    public static void validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID користувача має бути додатним числом");
        }
    }

    /**
     * Перевіряє, чи є ім'я користувача непорожнім.
     *
     * @param username Ім'я користувача.
     * @throws IllegalArgumentException Якщо ім'я користувача є порожнім.
     */
    public static void validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Ім'я користувача не може бути порожнім");
        }
    }

    /**
     * Перевіряє, чи є електронна пошта коректного формату.
     *
     * @param email Електронна пошта користувача.
     * @throws IllegalArgumentException Якщо електронна пошта має неправильний формат.
     */
    public static void validateEmail(String email) {
        if (email == null || !email.matches(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            throw new IllegalArgumentException("Невірний формат електронної пошти");
        }
    }

    /**
     * Перевіряє, чи є пароль достатньо довгим (мінімум 6 символів).
     *
     * @param password Пароль користувача.
     * @throws IllegalArgumentException Якщо пароль занадто короткий.
     */
    public static void validatePassword(String password) {
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Пароль повинен бути не менше 6 символів");
        }
    }

    /**
     * Перевіряє, чи є роль користувача коректною (admin або user).
     *
     * @param role Роль користувача.
     * @throws IllegalArgumentException Якщо роль користувача не є коректною.
     */
    public static void validateRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("Роль користувача не може бути порожньою");
        }
        if (!(role.equals("admin") || role.equals("user"))) {
            throw new IllegalArgumentException("Роль користувача може бути тільки admin або user");
        }
    }
}
