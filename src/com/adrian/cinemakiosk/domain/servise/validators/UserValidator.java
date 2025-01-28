package com.adrian.cinemakiosk.domain.servise.validators;

import com.adrian.cinemakiosk.persistence.entity.impl.User;

public class UserValidator {

    public static void validate(User user) {
        validateNotNull(user);
        validateId(user.getId());
        validateUsername(user.getUsername());
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
        validateRole(user.getRole());
    }

    public static void validateNotNull(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Користувач не може бути null");
        }
    }

    public static void validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID користувача має бути додатним числом");
        }
    }

    public static void validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Ім'я користувача не може бути порожнім");
        }
    }

    public static void validateEmail(String email) {
        if (email == null || !email.matches(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            throw new IllegalArgumentException("Невірний формат електронної пошти");
        }
    }

    public static void validatePassword(String password) {
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Пароль повинен бути не менше 6 символів");
        }
    }

    public static void validateRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("Роль користувача не може бути порожньою");
        }
        if (!(role.equals("admin") || role.equals("user"))) {
            throw new IllegalArgumentException("Роль користувача може бути тільки admin або user");
        }
    }
}
