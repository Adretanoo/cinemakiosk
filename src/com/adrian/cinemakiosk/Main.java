package com.adrian.cinemakiosk;

import com.adrian.cinemakiosk.domain.entitys.impl.User;
import com.adrian.cinemakiosk.domain.validators.UserValidator;

public class Main {

    public static void main(String[] args) {
//        Mailler.sendVerificationCodeEmail("c.tehza.adrian@student.uzhnu.edu.ua", "sdlkfjkds");

        User user = new User(-1, "", "adrianzail.com", "12345678D", "f");
        try {
            UserValidator.validate(user);
            System.out.println("Валідаці успішна");
        } catch (IllegalArgumentException e) {
            String errorMessage = e.getMessage();
            System.out.println("Помилки валідації: " + errorMessage);
        }
    }

}
