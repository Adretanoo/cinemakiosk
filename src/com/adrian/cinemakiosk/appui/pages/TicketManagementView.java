package com.adrian.cinemakiosk.appui.pages;

import com.adrian.cinemakiosk.persistence.entity.impl.Ticket;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
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
            drawMenuFrame();
            renderMenu(menuOptions, selectedIndex);
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

    private void drawMenuFrame() throws IOException {
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(0, 0, "┌────────────────────────────────┐");
        textGraphics.putString(0, 1, "│                                │");
        textGraphics.putString(0, 2, "        Управління квитками      ");
        textGraphics.putString(0, 3, "├────────────────────────────────┤");
        textGraphics.putString(0, 4, "│                                │");
        textGraphics.putString(0, 5, "│                                │");
        textGraphics.putString(0, 6, "│                                │");
        textGraphics.putString(0, 7, "│                                │");
        textGraphics.putString(0, 8, "│                                │");
        textGraphics.putString(0, 9, "│                                │");
        textGraphics.putString(0, 10, "└────────────────────────────────┘");
        screen.refresh();
    }

    private void renderMenu(String[] menuOptions, int selectedIndex) throws IOException {
        for (int i = 0; i < menuOptions.length; i++) {
            textGraphics.setForegroundColor(i == selectedIndex ? TextColor.Factory.fromString("#FFFF00") : TextColor.Factory.fromString("#FFFFFF"));
            textGraphics.putString(2, 4 + i, (i == selectedIndex ? "❯ " : "  ") + menuOptions[i]);
        }
        screen.refresh();
    }
    private void handleViewTickets() throws IOException {
        screen.clear();
        List<Ticket> tickets = readTicketsFromFile();

        int selectedIndex = 0;
        int startIndex = 0;
        int itemsPerPage = 10; // Кількість квитків на одній сторінці

        while (true) {
            screen.clear();
            drawTicketsFrame(); // Малюємо рамку для квитків

            // Вивести квитки на екран
            for (int i = startIndex; i < Math.min(startIndex + itemsPerPage, tickets.size()); i++) {
                Ticket ticket = tickets.get(i);
                textGraphics.setForegroundColor(i == selectedIndex ? TextColor.Factory.fromString("#FFFF00") : TextColor.Factory.fromString("#FFFFFF"));
                textGraphics.putString(2, 3 + (i - startIndex), String.format("%-5d %-30s %-10.2f %-12d %-15s",
                    ticket.getId(),
                    ticket.getMovie(),
                    ticket.getPrice(),
                    ticket.getSeatNumber(),
                    ticket.getStatus()));
            }

            screen.refresh();

            // Читання введення користувача
            KeyStroke keyStroke = screen.readInput();
            if (keyStroke.getKeyType() == KeyType.Escape) {
                return;
            } else if (keyStroke.getKeyType() == KeyType.ArrowDown) {
                if (selectedIndex < Math.min(startIndex + itemsPerPage, tickets.size()) - 1) {
                    selectedIndex++;
                } else if (startIndex + itemsPerPage < tickets.size()) {
                    // Якщо на кінці сторінки, прокручувати вниз
                    startIndex++;
                }
            } else if (keyStroke.getKeyType() == KeyType.ArrowUp) {
                if (selectedIndex > 0) {
                    selectedIndex--;
                } else if (startIndex > 0) {
                    // Якщо на початку сторінки, прокручувати вгору
                    startIndex--;
                }
            }
        }
    }

    private void drawTicketsFrame() throws IOException {
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(0, 0, "┌───────────────────────────────────────────────────────────────────────────────────────┐");
        textGraphics.putString(0, 1, "│                         Список квитків                                                │");
        textGraphics.putString(0, 2, "├───────────────────────────────────────────────────────────────────────────────────────┤");
    }



    private void handleAddTicket() throws IOException {
        screen.clear();

        // Введення назви фільму
        String movie = null;
        while (movie == null || movie.trim().isEmpty()) {
            textGraphics.putString(2, 1, "Введіть назву фільму:                                                                  ");
            movie = getInput(3);
            if (movie == null) return;  // Якщо натискається Esc, повертаємось в меню
            // Очищення попередньої помилки
            textGraphics.putString(2, 5, " ");  // Очищення попередньої помилки
            if (movie.trim().isEmpty()) {
                textGraphics.setForegroundColor(TextColor.ANSI.RED);
                textGraphics.putString(2, 5, "Помилка: Назва фільму не може бути порожньою!");
                screen.refresh();
                screen.readInput();
                textGraphics.setForegroundColor(TextColor.ANSI.DEFAULT);
            }
        }

        // Введення ціни квитка
        double price = -1;
        while (price <= 0) {
            textGraphics.putString(2, 5, "Введіть ціну квитка:                                                   ");
            String priceInput = getInput(7);
            if (priceInput == null) return;  // Якщо натискається Esc, повертаємось в меню
            // Очищення попередньої помилки
            textGraphics.putString(2, 9, " ");  // Очищення попередньої помилки
            if (priceInput.trim().isEmpty()) {
                textGraphics.setForegroundColor(TextColor.ANSI.RED);
                textGraphics.putString(2, 9, "Помилка: Ціна не може бути порожньою!");
                screen.refresh();
                screen.readInput();
                textGraphics.setForegroundColor(TextColor.ANSI.DEFAULT);
                continue;
            }
            try {
                price = Double.parseDouble(priceInput);
                if (price <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                textGraphics.setForegroundColor(TextColor.ANSI.RED);
                textGraphics.putString(2, 9, "Помилка: Введіть коректну ціну (число більше 0)!");
                screen.refresh();
                screen.readInput();
                textGraphics.setForegroundColor(TextColor.ANSI.DEFAULT);
            }
        }

        // Введення номера місця
        int seatNumber = -1;
        while (seatNumber <= 0) {
            textGraphics.putString(2, 9, "Введіть номер місця:                                    ");
            String seatNumberInput = getInput(11);
            if (seatNumberInput == null) return;  // Якщо натискається Esc, повертаємось в меню
            // Очищення попередньої помилки
            textGraphics.putString(2, 13, " ");  // Очищення попередньої помилки
            if (seatNumberInput.trim().isEmpty()) {
                textGraphics.setForegroundColor(TextColor.ANSI.RED);
                textGraphics.putString(2, 13, "Помилка: Номер місця не може бути порожнім!");
                screen.refresh();
                screen.readInput();
                textGraphics.setForegroundColor(TextColor.ANSI.DEFAULT);
                continue;
            }
            try {
                seatNumber = Integer.parseInt(seatNumberInput);
                if (seatNumber <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                textGraphics.setForegroundColor(TextColor.ANSI.RED);
                textGraphics.putString(2, 13, "Помилка: Введіть коректний номер місця (ціле число більше 0)!");
                screen.refresh();
                screen.readInput();
                textGraphics.setForegroundColor(TextColor.ANSI.DEFAULT);
            }
        }

        // Введення статусу
        String status = null;
        while (status == null || status.trim().isEmpty()) {
            textGraphics.putString(2, 13, "Введіть статус (Продано / Доступно):                                 ");
            status = getInput(15);
            if (status == null) return;  // Якщо натискається Esc, повертаємось в меню
            // Очищення попередньої помилки
            textGraphics.putString(2, 17, " ");  // Очищення попередньої помилки
            if (status.trim().isEmpty()) {
                textGraphics.setForegroundColor(TextColor.ANSI.RED);
                textGraphics.putString(2, 17, "Помилка: Статус не може бути порожнім!");
                screen.refresh();
                screen.readInput();
                textGraphics.setForegroundColor(TextColor.ANSI.DEFAULT);
                continue;
            }
            if (!status.equalsIgnoreCase("Продано") && !status.equalsIgnoreCase("Доступно")) {
                textGraphics.setForegroundColor(TextColor.ANSI.RED);
                textGraphics.putString(2, 17, "Помилка: Статус має бути 'Продано' або 'Доступно'!");
                screen.refresh();
                screen.readInput();
                textGraphics.setForegroundColor(TextColor.ANSI.DEFAULT);
                status = null; // Встановлюємо null, щоб повторно запитувати
            }
        }

        // Створення та збереження квитка
        Ticket ticket = new Ticket(movie, price);
        ticket.setSeatNumber(seatNumber);
        ticket.setStatus(status);

        List<Ticket> tickets = readTicketsFromFile();
        int newId = tickets.stream().mapToInt(Ticket::getId).max().orElse(0) + 1;
        ticket.setId(newId);
        tickets.add(ticket);
        saveTicketsToFile(tickets);

        textGraphics.putString(2, 17, "Квиток успішно додано!                       ");
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
            textGraphics.putString(2, 5, "Квиток успішно видалено!");
        } catch (NumberFormatException e) {
            textGraphics.putString(2, 5, "Невірний формат ID!");
        }

        screen.refresh();
        screen.readInput();
    }

    private String getInput(int inputLine) throws IOException {
        StringBuilder input = new StringBuilder();

        while (true) {
            textGraphics.putString(2, inputLine, input.toString() + " "); // Додаємо пробіл, щоб затирати символи
            screen.refresh();

            KeyStroke keyStroke = screen.readInput();

            if (keyStroke.getKeyType() == KeyType.Enter) {
                return input.toString().trim();
            } else if (keyStroke.getKeyType() == KeyType.Backspace) {
                if (input.length() > 0) {
                    input.deleteCharAt(input.length() - 1);
                }
            } else if (keyStroke.getKeyType() == KeyType.Escape) {
                return null;
            } else if (keyStroke.getCharacter() != null) {
                input.append(keyStroke.getCharacter());
            }
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


}
