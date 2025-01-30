package com.adrian.cinemakiosk.appui.pages;

import com.adrian.cinemakiosk.persistence.entity.impl.Ticket;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.List;

/**
 * Клас для відображення квитків користувача.
 */
public class MyTicketView {

    private List<Ticket> userTickets; // Список квитків користувача
    private final Screen screen; // Екран для відображення інтерфейсу
    private final TextGraphics textGraphics; // Графічний контекст для малювання тексту

    /**
     * Конструктор для ініціалізації MyTicketView.
     *
     * @param screen екран для відображення
     * @param textGraphics графічний контекст для малювання тексту
     */
    public MyTicketView(Screen screen, TextGraphics textGraphics) {
        this.screen = screen;
        this.textGraphics = textGraphics;
        this.userTickets = loadTestTickets(); // Завантаження тестових квитків
    }

    /**
     * Завантажує тестові квитки для демонстрації.
     *
     * @return список квитків
     */
    private List<Ticket> loadTestTickets() {
        String json = "["
            + "{\"id\":1,\"price\":100.0,\"sessionId\":0,\"seatNumber\":0,\"quantity\":10,\"orderId\":1,\"movie\":\"Матриця\",\"time\":\"2025-01-29T19:00\"},"
            + "{\"id\":2,\"price\":120.0,\"sessionId\":0,\"seatNumber\":0,\"quantity\":5,\"orderId\":1,\"movie\":\"Початок\",\"time\":\"2025-01-30T19:00\"}"
            + "]";

        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<Ticket>>(){}.getType()); // Перетворення JSON в список об'єктів Ticket
    }

    /**
     * Відображає список квитків користувача.
     *
     * @throws IOException якщо виникнуть проблеми при відображенні на екрані
     */
    public void showTickets() throws IOException {
        screen.clear();
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(2, 2, "Список квитків:");

        int row = 4;
        for (Ticket ticket : userTickets) {
            // Формування рядка з інформацією про квиток
            String ticketInfo = String.format("Квиток ID: %d | Фільм: %s | Ціна: %.2f | Час: %s",
                ticket.getId(), ticket.getMovie(), ticket.getPrice(), ticket.getStatus());

            textGraphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
            textGraphics.putString(2, row, ticketInfo); // Виведення квитка на екран
            row++;
        }

        screen.refresh(); // Оновлення екрану
        screen.readInput(); // Очікування введення користувача
    }
}
