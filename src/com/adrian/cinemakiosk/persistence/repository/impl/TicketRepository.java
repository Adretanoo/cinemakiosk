package com.adrian.cinemakiosk.persistence.repository.impl;

import com.adrian.cinemakiosk.persistence.entity.impl.Ticket;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TicketRepository {

    private static final String FILE_PATH = "data/tickets.json";
    private final Gson gson;
    private final List<Ticket> tickets;

    public TicketRepository() {
        this.gson = new Gson();
        this.tickets = loadTickets();
    }

    private List<Ticket> loadTickets() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            return gson.fromJson(reader, new TypeToken<List<Ticket>>() {}.getType());
        } catch (IOException e) {
            System.out.println("Помилка завантаження квитків: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void saveTickets() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(tickets, writer);
        } catch (IOException e) {
            System.out.println("Помилка збереження квитків: " + e.getMessage());
        }
    }

    public List<Ticket> getAvailableTickets() {
        List<Ticket> availableTickets = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if ("Доступний".equals(ticket.getStatus()) && ticket.getQuantity() > 0) {
                availableTickets.add(ticket);
            }
        }
        return availableTickets;
    }

    public void updateTicket(Ticket updatedTicket) {
        for (int i = 0; i < tickets.size(); i++) {
            if (tickets.get(i).getId() == updatedTicket.getId()) {
                tickets.set(i, updatedTicket);
                saveTickets();
                return;
            }
        }
    }

    public boolean buyTicket(int ticketId) {
        for (Ticket ticket : tickets) {
            if (ticket.getId() == ticketId && ticket.getQuantity() > 0) {
                ticket.setQuantity(ticket.getQuantity() - 1);
                if (ticket.getQuantity() == 0) {
                    ticket.setStatus("Продано");
                }
                saveTickets();
                return true;
            }
        }
        return false;
    }
}
