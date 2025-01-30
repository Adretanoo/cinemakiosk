package com.adrian.cinemakiosk.appui.pages;

import com.adrian.cinemakiosk.persistence.entity.impl.Ticket;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class TicketManagementView {
    private final Screen screen;
    private final TextGraphics textGraphics;
    private final String ticketFilePath = "data/tickets.json";

    public TicketManagementView(Screen screen, TextGraphics textGraphics) {
        this.screen = screen;
        this.textGraphics = textGraphics;
    }

    public void showMenu() throws IOException {
        String[] menuOptions = {"1. Додати квиток", "2. Видалити квиток", "3. Переглянути квитки", "4. Повернутися до меню"};
        int selectedIndex = 0;

        while (true) {
            screen.clear();
            renderMenu(menuOptions, selectedIndex);
            screen.refresh();
            var keyStroke = screen.readInput();

            if (keyStroke.getKeyType() == KeyType.Escape || selectedIndex == 3) {
                return;
            }
            if (keyStroke.getKeyType() == KeyType.Enter) {
                switch (selectedIndex) {
                    case 0 -> handleAddTicket();
                    case 1 -> handleRemoveTicket();
                    case 2 -> handleViewTickets();
                }
            }
            if (keyStroke.getKeyType() == KeyType.ArrowUp) {
                selectedIndex = (selectedIndex - 1 + menuOptions.length) % menuOptions.length;
            } else if (keyStroke.getKeyType() == KeyType.ArrowDown) {
                selectedIndex = (selectedIndex + 1) % menuOptions.length;
            }
        }
    }

    private void handleViewTickets() throws IOException {
        screen.clear();
        List<Ticket> tickets = readTicketsFromFile();
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
        textGraphics.putString(2, 1, "📜 Список квитків:");

        if (tickets.isEmpty()) {
            textGraphics.putString(2, 3, "❌ Немає квитків у системі.");
        } else {
            int line = 3;
            for (Ticket ticket : tickets) {
                textGraphics.putString(2, line++, "ID: " + ticket.getId() + " | Фільм: " + ticket.getMovie() + " | Ціна: " + ticket.getPrice() + " | Місце: " + ticket.getSeatNumber() + " | Статус: " + ticket.getStatus());
            }
        }

        screen.refresh();
        screen.readInput();
    }

    private void handleAddTicket() throws IOException {
        screen.clear();
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
        textGraphics.putString(2, 1, "Введіть назву фільму: ");
        String movie = getInput(3);
        if (movie == null) return;

        textGraphics.putString(2, 5, "Введіть ціну квитка: ");
        String priceInput = getInput(7);
        if (priceInput == null) return;
        double price = Double.parseDouble(priceInput);

        textGraphics.putString(2, 9, "Введіть номер місця: ");
        String seatNumberInput = getInput(11);
        if (seatNumberInput == null) return;
        int seatNumber = Integer.parseInt(seatNumberInput);

        textGraphics.putString(2, 13, "Введіть статус (Продано / Доступно): ");
        String status = getInput(15);
        if (status == null) return;

        Ticket ticket = new Ticket(movie, price);
        ticket.setSeatNumber(seatNumber);
        ticket.setStatus(status);

        List<Ticket> tickets = readTicketsFromFile();
        int newId = tickets.stream().mapToInt(Ticket::getId).max().orElse(0) + 1;
        ticket.setId(newId);
        tickets.add(ticket);
        saveTicketsToFile(tickets);

        textGraphics.putString(2, 17, "✅ Квиток успішно додано!");
        screen.refresh();
        screen.readInput();
    }

    private void handleRemoveTicket() throws IOException {
        screen.clear();
        textGraphics.putString(2, 1, "Введіть ID квитка для видалення: ");
        String ticketIdInput = getInput(3);
        if (ticketIdInput == null) return;

        try {
            int ticketId = Integer.parseInt(ticketIdInput);
            List<Ticket> tickets = readTicketsFromFile();
            tickets.removeIf(ticket -> ticket.getId() == ticketId);
            saveTicketsToFile(tickets);
            textGraphics.putString(2, 5, "✅ Квиток успішно видалено!");
        } catch (NumberFormatException e) {
            textGraphics.putString(2, 5, "❌ Невірний формат ID!");
        }

        screen.refresh();
        screen.readInput();
    }

    private String getInput(int inputLine) throws IOException {
        StringBuilder input = new StringBuilder();
        screen.refresh();
        while (true) {
            var keyStroke = screen.readInput();

            if (keyStroke.getKeyType() == KeyType.Escape) {
                return null;
            }

            if (keyStroke.getKeyType() == KeyType.Enter) {
                return input.toString().trim();
            } else if (keyStroke.getKeyType() == KeyType.Backspace && input.length() > 0) {
                input.deleteCharAt(input.length() - 1);
            } else if (keyStroke.getCharacter() != null) {
                input.append(keyStroke.getCharacter());
            }

            textGraphics.putString(2, inputLine, input.toString() + " ");
            screen.refresh();
        }
    }

    private List<Ticket> readTicketsFromFile() throws IOException {
        Gson gson = new Gson();
        Type ticketListType = new TypeToken<List<Ticket>>(){}.getType();
        try (Reader reader = new FileReader(ticketFilePath)) {
            return gson.fromJson(reader, ticketListType);
        } catch (FileNotFoundException e) {
            return List.of();
        }
    }

    private void saveTicketsToFile(List<Ticket> tickets) throws IOException {
        Gson gson = new Gson();
        try (Writer writer = new FileWriter(ticketFilePath)) {
            gson.toJson(tickets, writer);
        }
    }

    private void renderMenu(String[] menuOptions, int selectedIndex) {
        for (int i = 0; i < menuOptions.length; i++) {
            textGraphics.setForegroundColor(i == selectedIndex ? TextColor.ANSI.YELLOW : TextColor.ANSI.WHITE);
            textGraphics.putString(2, i + 1, (i == selectedIndex ? "❯ " : "  ") + menuOptions[i]);
        }
    }
}