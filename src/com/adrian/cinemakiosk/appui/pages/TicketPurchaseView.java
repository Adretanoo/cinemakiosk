package com.adrian.cinemakiosk.appui.pages;

import com.adrian.cinemakiosk.persistence.entity.impl.Ticket;
import com.adrian.cinemakiosk.persistence.entity.impl.User;
import com.adrian.cinemakiosk.persistence.repository.impl.TicketRepository;
import com.adrian.cinemakiosk.persistence.repository.impl.UserRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class TicketPurchaseView {

    private final Screen screen;
    private final TextGraphics textGraphics;
    private final User user;
    private List<Ticket> availableTickets;
    private int selectedIndex = 0;

    public TicketPurchaseView(Screen screen, TextGraphics textGraphics, User user,
        TicketRepository ticketRepository, UserRepository userRepository) {
        this.screen = screen;
        this.textGraphics = textGraphics;
        this.user = user;
        this.availableTickets = loadTicketsFromJson("data/tickets.json");
    }

    private List<Ticket> loadTicketsFromJson(String filePath) {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(filePath);
            Type ticketListType = new TypeToken<List<Ticket>>(){}.getType();
            return gson.fromJson(reader, ticketListType);
        } catch (IOException e) {
            e.printStackTrace();
            return List.of(); // Повертаємо порожній список в разі помилки
        }
    }

    public void showPurchaseMenu() throws IOException {
        while (true) {
            renderMenu();

            var keyStroke = screen.readInput();
            if (keyStroke.getKeyType() == KeyType.Escape) {
                return;
            } else if (keyStroke.getKeyType() == KeyType.ArrowUp && selectedIndex > 0) {
                selectedIndex--;
            } else if (keyStroke.getKeyType() == KeyType.ArrowDown && selectedIndex < availableTickets.size() - 1) {
                selectedIndex++;
            } else if (keyStroke.getKeyType() == KeyType.Enter) {
                purchaseTicket(selectedIndex);
            }
        }
    }

    private void renderMenu() throws IOException {
        screen.clear();

        // Виведення балансу
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(2, 2, "Баланс: " + user.getBalance());

        // Заголовок
        textGraphics.putString(2, 3, "Оберіть квиток:");

        // Заголовки таблиці з акцентами
        String header = String.format("%-4s %-12s %-20s %-12s", "№", "Ціна", "Фільм", "Час");
        textGraphics.putString(2, 5, header);

        // Роздільна лінія для таблиці
        textGraphics.putString(2, 6, "───────────────────────────────────────────────────────────────");

        // Виведення інформації про доступні квитки
        int startIndex = selectedIndex; // Стартовий індекс для відображення
        int endIndex = Math.min(startIndex + 10, availableTickets.size()); // Відображаємо 10 квитків на екран

        for (int i = startIndex; i < endIndex; i++) {
            Ticket ticket = availableTickets.get(i);
            String ticketInfo = String.format("%-4d %-12.2f %-20s %-12s", i + 1, ticket.getPrice(), ticket.getMovie(), ticket.getTime());

            if (i == selectedIndex) {
                // Виділення вибраного квитка
                textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
                textGraphics.putString(2, 7 + (i - startIndex), "❯ " + ticketInfo);
            } else {
                textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
                textGraphics.putString(4, 7 + (i - startIndex), ticketInfo);
            }
        }

        // Роздільна лінія після списку квитків
        textGraphics.putString(2, 7 + (endIndex - startIndex), "───────────────────────────────────────────────────────────────");

        // Показуємо індекс сторінки або ще кілька інструкцій, якщо потрібно
        if (availableTickets.size() > 10) {
            textGraphics.putString(2, 9 + (endIndex - startIndex), "Використовуйте ↑↓ для прокручування.");
        }

        screen.refresh();
    }


    private void purchaseTicket(int index) throws IOException {
        if (index >= availableTickets.size()) return;

        Ticket ticket = availableTickets.get(index);
        if (user.getBalance() < ticket.getPrice()) {
            textGraphics.setForegroundColor(TextColor.Factory.fromString("#FF0000"));
            textGraphics.putString(2, 10, "Недостатньо коштів!");
            screen.refresh();
            return;
        }

        user.setBalance(user.getBalance() - ticket.getPrice());
        ticket.setStatus("Продано");
        ticket.setOrderId(user.getId());

        // Оновлюємо базу даних
        // userRepository.updateUser(user);
        // ticketRepository.updateTicket(ticket);

        textGraphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
        textGraphics.putString(2, 10, "Квиток успішно придбано!");
        screen.refresh();
        availableTickets.remove(index);
    }
}
