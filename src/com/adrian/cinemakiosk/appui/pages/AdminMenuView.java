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
            renderMenu(menuOptions, selectedIndex);
            var keyStroke = screen.readInput();

            if (keyStroke.getKeyType() == KeyType.Escape || selectedIndex == 3) {
                return; // Повернення в попереднє меню
            }
            if (keyStroke.getKeyType() == KeyType.Enter) {
                switch (selectedIndex) {
                    case 0 -> handleMovieManagement();
                    case 1 -> handleUserManagement();
                    case 2 -> handleTicketManagement();
                }
            }
            if (keyStroke.getKeyType() == KeyType.ArrowUp) {
                selectedIndex = (selectedIndex - 1 + menuOptions.length) % menuOptions.length;
            } else if (keyStroke.getKeyType() == KeyType.ArrowDown) {
                selectedIndex = (selectedIndex + 1) % menuOptions.length;
            }
        }
    }

    private void renderMenu(String[] menuOptions, int selectedIndex) throws IOException {
        screen.clear();
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        textGraphics.putString(2, 1, "🔧 Адмін-меню: " + username);
        for (int i = 0; i < menuOptions.length; i++) {
            textGraphics.setForegroundColor(i == selectedIndex ? TextColor.Factory.fromString("#FFFF00") : TextColor.Factory.fromString("#FFFFFF"));
            textGraphics.putString(2, 3 + i, (i == selectedIndex ? "❯ " : "  ") + menuOptions[i]);
        }
        screen.refresh();
    }

    private void handleMovieManagement() throws IOException {
        MovieManagementView movieManagementView = new MovieManagementView(screen, textGraphics);
        movieManagementView.showMenu();
    }

    private void handleUserManagement() throws IOException {
        UserManagementView userManagementView = new UserManagementView(screen, textGraphics);
        userManagementView.showMenu();  // Викликаємо метод showMenu() для UserManagementView
    }

    private void handleTicketManagement() throws IOException {
        // Реалізуйте управління квитками
        textGraphics.putString(2, 15, "Управління квитками не реалізовано.");
        screen.refresh();
        screen.readInput();
    }
}
