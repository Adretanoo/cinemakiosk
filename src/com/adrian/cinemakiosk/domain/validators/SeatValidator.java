package com.adrian.cinemakiosk.domain.validators;

import com.adrian.cinemakiosk.domain.entitys.impl.Seat;
import java.util.ArrayList;
import java.util.List;

public class SeatValidator {

    public static void validate(Seat seat) {
        List<String> errors = new ArrayList<>();

        if (seat == null) {
            errors.add("\nМісце не може бути null");
        }
        if (seat.getId() <= 0) {
            errors.add("\nID місця має бути додатним числом");
        }
        if (seat.getSeatNumber() <= 0) {
            errors.add("\nНомер місця має бути додатним числом");
        }
        if (seat.getRow() <= 0) {
            errors.add("\nНомер ряду має бути додатним числом");
        }
        if (seat.getHallId() <= 0) {
            errors.add("\nID залу має бути додатним числом");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(" ", errors));
        }
    }
}
