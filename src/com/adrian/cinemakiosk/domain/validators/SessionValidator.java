package com.adrian.cinemakiosk.domain.validators;

import com.adrian.cinemakiosk.domain.entitys.impl.Session;
import java.util.ArrayList;
import java.util.List;

public class SessionValidator {

    public static void validate(Session session) {
        List<String> errors = new ArrayList<>();

        if (session == null) {
            errors.add("\nСеанс не може бути null");
        }
        if (session.getId() <= 0) {
            errors.add("\nID сеансу має бути додатним числом");
        }
        if (session.getDateTime() == null || session.getDateTime().trim().isEmpty()) {
            errors.add("\nДата і час сеансу не можуть бути порожніми");
        }
        if (session.getFormat() == null || session.getFormat().trim().isEmpty()) {
            errors.add("\nФормат сеансу не може бути порожнім");
        }
        if (session.getMovieId() <= 0) {
            errors.add("\nID фільму має бути додатним числом");
        }
        if (session.getHallId() <= 0) {
            errors.add("\nID залу має бути додатним числом");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(" ", errors));
        }
    }
}
