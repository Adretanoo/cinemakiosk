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

/**
 * Клас, що відповідає за відображення екрану покупки квитків та управління цією операцією.
 * Він дозволяє користувачеві вибирати квитки, переглядати їх деталі, а також здійснювати покупку.
 */
public class TicketPurchaseView {

    private final Screen screen;
    private final TextGraphics textGraphics;
    private final User user;
    private List<Ticket> availableTickets;
    private int selectedIndex = 0;

    /**
     * Конструктор для ініціалізації екрана покупки квитків.
     *
     * @param screen            екран для відображення
     * @param textGraphics      графічний контекст для малювання на екрані
     * @param user              користувач, що здійснює покупку
     * @param ticketRepository  репозиторій для роботи з квитками
     * @param userRepository    репозиторій для роботи з користувачами
     */
    public TicketPurchaseView(Screen screen, TextGraphics textGraphics, User user,
        TicketRepository ticketRepository, UserRepository userRepository) {
        this.screen = screen;
        this.textGraphics = textGraphics;
        this.user = user;
        this.availableTickets = loadTicketsFromJson("data/tickets.json");
    }

    /**
     * Завантажує список доступних квитків з JSON файлу.
     *
     * @param filePath шлях до файлу з квитками
     * @return список доступних квитків
     */
    private List<Ticket> loadTicketsFromJson(String filePath) {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(filePath);
            Type ticketListType = new TypeToken<List<Ticket>>(){}.getType();
            return gson.fromJson(reader, ticketListType);
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Відображає меню покупки квитків і обробляє введення користувача.
     *
     * @throws IOException в разі проблем з відображенням на екрані
     */
    public void showPurchaseMenu() throws IOException {
        while (true) {
            renderMenu();

            var keyStroke = screen.readInput();
            if (keyStroke.getKeyType() == KeyType.Escape) {
                return;  // Вихід з меню при натисканні Escape
            } else if (keyStroke.getKeyType() == KeyType.ArrowUp && selectedIndex > 0) {
                selectedIndex--;  // Переміщення вгору по списку
            } else if (keyStroke.getKeyType() == KeyType.ArrowDown && selectedIndex < availableTickets.size() - 1) {
                selectedIndex++;  // Переміщення вниз по списку
            } else if (keyStroke.getKeyType() == KeyType.Enter) {
                purchaseTicket(selectedIndex);  // Купівля вибраного квитка
            }
        }
    }

    /**
     * Відображає список квитків і оновлює екран.
     *
     * @throws IOException в разі проблем з відображенням на екрані
     */
    private void renderMenu() throws IOException {
        screen.clear();

        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(2, 2, "Баланс: " + user.getBalance());

        textGraphics.putString(2, 3, "Оберіть квиток:");

        String header = String.format("%-4s %-12s %-20s %-12s", "№", "Ціна", "Фільм", "Час");
        textGraphics.putString(2, 5, header);

        textGraphics.putString(2, 6, "───────────────────────────────────────────────────────────────");

        int startIndex = selectedIndex;
        int endIndex = Math.min(startIndex + 10, availableTickets.size());

        // Відображення квитків на екрані
        for (int i = startIndex; i < endIndex; i++) {
            Ticket ticket = availableTickets.get(i);
            String ticketInfo = String.format("%-4d %-12.2f %-20s %-12s", i + 1, ticket.getPrice(), ticket.getMovie(), ticket.getTime());

            // Підсвічування вибраного квитка
            if (i == selectedIndex) {
                textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
                textGraphics.putString(2, 7 + (i - startIndex), "❯ " + ticketInfo);
            } else {
                textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
                textGraphics.putString(4, 7 + (i - startIndex), ticketInfo);
            }
        }

        textGraphics.putString(2, 7 + (endIndex - startIndex), "───────────────────────────────────────────────────────────────");

        // Підказка для прокручування списку
        if (availableTickets.size() > 10) {
            textGraphics.putString(2, 9 + (endIndex - startIndex), "Використовуйте ↑↓ для прокручування.");
        }

        screen.refresh();
    }

    /**
     * Виконує покупку квитка на основі вибраного індексу.
     *
     * @param index індекс вибраного квитка
     * @throws IOException в разі проблем з відображенням на екрані
     */
    private void purchaseTicket(int index) throws IOException {
        if (index >= availableTickets.size()) return;

        Ticket ticket = availableTickets.get(index);
        // Перевірка балансу користувача
        if (user.getBalance() < ticket.getPrice()) {
            textGraphics.setForegroundColor(TextColor.Factory.fromString("#FF0000"));
            textGraphics.putString(2, 10, "Недостатньо коштів!");
            screen.refresh();
            return;
        }

        // Віднімання вартості квитка від балансу користувача
        user.setBalance(user.getBalance() - ticket.getPrice());
        ticket.setStatus("Продано");
        ticket.setOrderId(user.getId());

        textGraphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
        textGraphics.putString(2, 10, "Квиток успішно придбано!");
        screen.refresh();
        availableTickets.remove(index);  // Видалення купленого квитка зі списку
    }
}
