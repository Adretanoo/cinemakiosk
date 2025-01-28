package com.adrian.cinemakiosk.domain.servise.validators;

import com.adrian.cinemakiosk.persistence.entity.impl.Hall;
import java.util.ArrayList;
import java.util.List;

public class HallValidator {

    public static void validate(Hall hall) {
        List<String> errors = new ArrayList<>();

        if (hall == null) {
            errors.add("\nЗал не може бути null");
        }
        if (hall.getId() <= 0) {
            errors.add("\nID залу має бути додатнім числом");
        }
        if (hall.getName() == null || hall.getName().trim().isEmpty()) {
            errors.add("\nНазва залу не може бути порожньою");
        }
        if (hall.getSeatCapacity() <= 0) {
            errors.add("\nКількість місць у залі має бути більшою за 0");
        }
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(" ", errors));
        }
    }
}
