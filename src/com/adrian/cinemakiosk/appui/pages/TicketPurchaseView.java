package com.adrian.cinemakiosk.appui.pages;

import com.adrian.cinemakiosk.persistence.entity.impl.Ticket;
import com.adrian.cinemakiosk.persistence.entity.impl.User;
import com.adrian.cinemakiosk.persistence.repository.impl.TicketRepository;
import com.adrian.cinemakiosk.persistence.repository.impl.UserRepository;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.List;

public class TicketPurchaseView {

    private final Screen screen;
    private final TextGraphics textGraphics;
    private final User user;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private List<Ticket> availableTickets;
    private int selectedIndex = 0;

    public TicketPurchaseView(Screen screen, TextGraphics textGraphics, User user,
        TicketRepository ticketRepository, UserRepository userRepository) {
        this.screen = screen;
        this.textGraphics = textGraphics;
        this.user = user;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.availableTickets = ticketRepository.getAvailableTickets();
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
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(2, 2, "Баланс: " + user.getBalance());
        textGraphics.putString(2, 3, "Оберіть квиток:");

        for (int i = 0; i < availableTickets.size(); i++) {
            Ticket ticket = availableTickets.get(i);
            String ticketInfo = String.format("%d. Ціна: %.2f | Тип: %s | Статус: %s",
                i + 1, ticket.getPrice(), ticket.getTicketType(), ticket.getStatus());
            if (i == selectedIndex) {
                textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
                textGraphics.putString(2, 5 + i, "❯ " + ticketInfo);
            } else {
                textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
                textGraphics.putString(4, 5 + i, ticketInfo);
            }
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
        userRepository.updateUser(user);
        ticketRepository.updateTicket(ticket);

        textGraphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
        textGraphics.putString(2, 10, "Квиток успішно придбано!");
        screen.refresh();
        availableTickets.remove(index);
    }
}
