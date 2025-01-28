package com.adrian.cinemakiosk.domain.servise.validators;

import com.adrian.cinemakiosk.persistence.entity.impl.User;
import java.util.ArrayList;
import java.util.List;

public class UserValidator {

    public static void validate(User user) {
        List<String> errors = new ArrayList<>();

        if (user == null) {
            errors.add("\nКористувач не може бути null");
        }
        if (user.getId() <= 0) {
            errors.add("\nID користувача має бути додатним числом");
        }
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            errors.add("\nІм'я користувача не може бути порожнім");
        }
        if (user.getEmail() == null || !user.getEmail().matches(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            errors.add("\nНевірний формат електронної пошти");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            errors.add("\nПароль повинен бути не менше 6 символів");
        }
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            errors.add("\nРоль користувача не може бути порожньою");
        }
        if (user.getRole() != "admin" || user.getRole() != "user") {
            errors.add("\nРоль користувача може бути тільки admin або user");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(" ", errors));
        }
    }
}
