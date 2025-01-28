package com.adrian.cinemakiosk.persistence.repository.impl;

import com.adrian.cinemakiosk.persistence.entity.impl.Ticket;
import com.google.gson.reflect.TypeToken;
import java.util.List;

public class TicketRepository extends BaseRepository<Ticket> {

    public TicketRepository() {
        super("data/tickets.json", new TypeToken<List<Ticket>>() {
        }.getType());
    }

    @Override
    protected int getId(Ticket ticket) {
        return ticket.getId();
    }

    @Override
    protected boolean matchesField(Ticket ticket, String fieldName, Object value) {
        switch (fieldName) {
            case "price":
                return ticket.getPrice() == (double) value;
            case "status":
                return ticket.getStatus().equals(value);
            case "ticketType":
                return ticket.getTicketType().equals(value);
            default:
                return false;
        }
    }
}


