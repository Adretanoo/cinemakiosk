package com.adrian.cinemakiosk.appui.pages;

import com.adrian.cinemakiosk.persistence.entity.impl.Ticket;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.List;

public class MyTicketView {

    private List<Ticket> userTickets;
    private final Screen screen;
    private final TextGraphics textGraphics;

    public MyTicketView(Screen screen, TextGraphics textGraphics) {
        this.screen = screen;
        this.textGraphics = textGraphics;
        this.userTickets = loadTestTickets();
    }

    private List<Ticket> loadTestTickets() {
        String json = "["
            + "{\"id\":1,\"price\":100.0,\"sessionId\":0,\"seatNumber\":0,\"quantity\":10,\"orderId\":1,\"movie\":\"Матриця\",\"time\":\"2025-01-29T19:00\"},"
            + "{\"id\":2,\"price\":120.0,\"sessionId\":0,\"seatNumber\":0,\"quantity\":5,\"orderId\":1,\"movie\":\"Початок\",\"time\":\"2025-01-30T19:00\"}"
            + "]";

        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<Ticket>>(){}.getType());
    }

    // Метод для показу квитків
    public void showTickets() throws IOException {
        screen.clear();
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(2, 2, "Список квитків:");

        int row = 4;
        for (Ticket ticket : userTickets) {
            String ticketInfo = String.format("Квиток ID: %d | Фільм: %s | Ціна: %.2f | Статус: %s",
                ticket.getId(), ticket.getMovie(), ticket.getPrice(), ticket.getStatus());

            textGraphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));  // Зелений колір для тексту
            textGraphics.putString(2, row, ticketInfo);
            row++;
        }

        screen.refresh();
        screen.readInput();

    }
}
