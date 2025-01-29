package com.adrian.cinemakiosk.domain.servise.validators;

import com.adrian.cinemakiosk.persistence.entity.impl.Ticket;
import java.util.ArrayList;
import java.util.List;

public class TicketValidator {

    public static void validate(Ticket ticket) {
        List<String> errors = new ArrayList<>();

        if (ticket == null) {
            errors.add("\nКвиток не може бути null");
        } else {
            if (ticket.getId() <= 0) {
                errors.add("\nID квитка має бути додатнім числом");
            }

            if (ticket.getPrice() <= 0) {
                errors.add("\nЦіна квитка має бути більшою за 0");
            }


            if (ticket.getSessionId() <= 0) {
                errors.add("\nID сеансу має бути додатнім числом");
            }

            if (ticket.getStatus() == null || ticket.getStatus().trim().isEmpty()) {
                errors.add("\nСтатус квитка не може бути порожнім");
            }

            if (ticket.getTicketType() == null || ticket.getTicketType().trim().isEmpty()) {
                errors.add("\nТип квитка не може бути порожнім");
            }

            if (ticket.getOrderId() <= 0) {
                errors.add("\nID замовлення має бути додатнім числом");
            }
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(" ", errors));
        }
    }
}
