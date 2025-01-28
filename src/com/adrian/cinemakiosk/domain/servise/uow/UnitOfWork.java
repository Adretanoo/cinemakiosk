package com.adrian.cinemakiosk.domain.servise.uow;

import com.adrian.cinemakiosk.persistence.entity.impl.Ticket;
import com.adrian.cinemakiosk.persistence.repository.Repository;
import com.adrian.cinemakiosk.persistence.repository.impl.TicketRepository;

public class UnitOfWork {

    private final TicketRepository ticketRepository;

    public UnitOfWork() {
        this.ticketRepository = new TicketRepository();
    }

    public Repository<?> getRepository(Class<?> entityClass) {
        if (entityClass == Ticket.class) {
            return ticketRepository;
        }
        throw new IllegalArgumentException(
            "No repository found for class: " + entityClass.getName());
    }
}