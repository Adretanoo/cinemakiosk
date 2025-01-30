package com.adrian.cinemakiosk.appui.pages;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;

public class AdminMenuView {
    private final Screen screen;
    private final TextGraphics textGraphics;
    private final String username;

    public AdminMenuView(Screen screen, TextGraphics textGraphics, String username) {
        this.screen = screen;
        this.textGraphics = textGraphics;
        this.username = username;
    }

    public void showMenu() throws IOException {
        String[] menuOptions = {
            "1. Управління фільмами",
            "2. Управління користувачами",
            "3. Управління квитками",
            "4. Вийти"
        };
        int selectedIndex = 0;

        while (true) {
            screen.clear();
            drawMenuFrame();
            renderMenu(menuOptions, selectedIndex);
            var keyStroke = screen.readInput();

            if (keyStroke.getKeyType() == KeyType.Enter) {
                handleMenuSelection(selectedIndex);
            }
            if (keyStroke.getKeyType() == KeyType.ArrowUp) {
                selectedIndex = (selectedIndex - 1 + (menuOptions.length - 1)) % (menuOptions.length - 1);
            } else if (keyStroke.getKeyType() == KeyType.ArrowDown) {
                selectedIndex = (selectedIndex + 1) % menuOptions.length;
            }
        }
    }

    private void drawMenuFrame() throws IOException {
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(0, 0, "┌────────────────────────────────┐");
        textGraphics.putString(0, 1, "│                                │");
        textGraphics.putString(0, 2, "        Admin: " + username + "   ");
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
            textGraphics.putString(2, 5 + i, (i == selectedIndex ? "❯ " : "  ") + menuOptions[i]);
        }
        screen.refresh();
    }

    private void handleMenuSelection(int selectedIndex) throws IOException {
        switch (selectedIndex) {
            case 0 -> handleMovieManagement();
            case 1 -> handleUserManagement();
            case 2 -> handleTicketManagement();
            case 3 -> new MenuView(screen,textGraphics).showMainMenu();
            default -> throw new IllegalStateException("Невідомий вибір: " + selectedIndex);
        }
    }

    private void handleMovieManagement() throws IOException {
        MovieManagementView movieManagementView = new MovieManagementView(screen, textGraphics);
        movieManagementView.showMenu();
    }

    private void handleUserManagement() throws IOException {
        UserManagementView userManagementView = new UserManagementView(screen, textGraphics);
        userManagementView.showMenu();
    }

    private void handleTicketManagement() throws IOException {
        TicketManagementView ticketManagementView = new TicketManagementView(screen, textGraphics);
        ticketManagementView.showMenu();
    }
}