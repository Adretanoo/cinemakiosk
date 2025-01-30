package com.adrian.cinemakiosk.appui.pages;

import com.adrian.cinemakiosk.persistence.entity.impl.Ticket;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.List;

/**
 * Клас, що відповідає за відображення списку доступних квитків і детальної інформації про них.
 * Клас дозволяє користувачеві переглядати доступні квитки, вибирати їх для перегляду деталей.
 */
public class TicketView {

    private static final int MAX_VISIBLE_RESULTS = 5;
    private final Screen screen;
    private final TextGraphics textGraphics;
    private final List<Ticket> tickets;
    private int selectedIndex = 0;
    private int topIndex = 0;

    /**
     * Конструктор для ініціалізації екрана відображення квитків.
     *
     * @param screen         екран для відображення
     * @param textGraphics   графічний контекст для малювання на екрані
     * @param tickets        список доступних квитків
     */
    public TicketView(Screen screen, TextGraphics textGraphics, List<Ticket> tickets) {
        this.screen = screen;
        this.textGraphics = textGraphics;
        this.tickets = tickets;
    }

    /**
     * Відображає список доступних квитків і дозволяє користувачеві переглядати їх.
     * Також дозволяє вибирати квитки для перегляду деталей або повернення до попереднього меню.
     *
     * @throws IOException в разі проблем з відображенням на екрані
     */
    public void displayTickets() throws IOException {
        if (tickets.isEmpty()) {
            screen.clear();
            textGraphics.setForegroundColor(TextColor.Factory.fromString("#FF0000"));
            textGraphics.putString(2, 3, "Немає доступних квитків.");
            screen.refresh();
            screen.readInput();
            return;
        }

        selectedIndex = 0;
        topIndex = 0;
        while (true) {
            renderTickets();
            var keyStroke = screen.readInput();

            if (keyStroke.getKeyType() == KeyType.Escape) {
                return;  // Вихід з меню при натисканні Escape
            } else if (keyStroke.getKeyType() == KeyType.ArrowUp && selectedIndex > 0) {
                selectedIndex--;  // Переміщення вгору по списку квитків
                if (selectedIndex < topIndex) {
                    topIndex--;  // Прокручування списку вгору
                }
            } else if (keyStroke.getKeyType() == KeyType.ArrowDown && selectedIndex < tickets.size() - 1) {
                selectedIndex++;  // Переміщення вниз по списку квитків
                if (selectedIndex >= topIndex + MAX_VISIBLE_RESULTS) {
                    topIndex++;  // Прокручування списку вниз
                }
            } else if (keyStroke.getKeyType() == KeyType.Enter) {
                showTicketDetails(tickets.get(selectedIndex));  // Відображення деталей вибраного квитка
            }
        }
    }

    /**
     * Відображає список квитків з обмеженням на кількість видимих результатів (MAX_VISIBLE_RESULTS).
     *
     * @throws IOException в разі проблем з відображенням на екрані
     */
    private void renderTickets() throws IOException {
        screen.clear();
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        textGraphics.putString(2, 1, "🎟 Доступні квитки:");

        int y = 3;
        // Відображає квитки на екрані
        for (int i = topIndex; i < Math.min(topIndex + MAX_VISIBLE_RESULTS, tickets.size()); i++) {
            Ticket ticket = tickets.get(i);
            String indicator = (i == selectedIndex) ? "❯ " : "  ";

            textGraphics.setForegroundColor(i == selectedIndex ?
                TextColor.Factory.fromString("#FFFF00") : TextColor.Factory.fromString("#FFFFFF"));

            textGraphics.putString(2, y, indicator + ticket.getMovie() + " | " + ticket.getTime());
            textGraphics.putString(4, y + 1, "Ціна: " + ticket.getPrice() + " грн");
            textGraphics.putString(4, y + 2, "Місця: " + ticket.getQuantity());
            y += 4;
        }
        screen.refresh();
    }

    /**
     * Відображає деталі вибраного квитка на екрані.
     *
     * @param ticket вибраний квиток для перегляду
     * @throws IOException в разі проблем з відображенням на екрані
     */
    private void showTicketDetails(Ticket ticket) throws IOException {
        screen.clear();
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        textGraphics.putString(2, 2, "🎟 Деталі квитка:");
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));

        textGraphics.putString(2, 4, "Фільм: " + ticket.getMovie());
        textGraphics.putString(2, 5, "Час: " + ticket.getTime());
        textGraphics.putString(2, 6, "Ціна: " + ticket.getPrice() + " грн");
        textGraphics.putString(2, 7, "Кількість: " + ticket.getQuantity());

        textGraphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
        textGraphics.putString(2, 9, "Натисніть будь-яку клавішу для повернення");
        screen.refresh();
        screen.readInput();
    }
}
