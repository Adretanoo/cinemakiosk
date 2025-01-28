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
            "3. Огляд статистики",
            "4. Управління квитками",
            "5. Вийти"
        };

        int selectedIndex = 0; // Початково вибраний пункт меню

        while (true) {
            screen.clear();
            drawMenuFrame();
            renderMenu(menuOptions, selectedIndex);

            var keyStroke = screen.readInput();
            if (keyStroke.getKeyType() == KeyType.Escape) {
                break; // Вихід з меню
            }

            if (keyStroke.getKeyType() == KeyType.Enter) {
                // Обробка вибору
                if (selectedIndex == 0) {
                    // Управління фільмами
                    handleMovieManagement();
                } else if (selectedIndex == 1) {
                    // Управління користувачами
                    handleUserManagement();
                } else if (selectedIndex == 2) {
                    // Огляд статистики
                    handleStatisticsReview();
                } else if (selectedIndex == 3) {
                    // Управління квитками
                    handleTicketManagement();
                } else if (selectedIndex == 4) {
                    // Вихід
                    break;
                }
            }

            // Навігація по меню
            if (keyStroke.getKeyType() == KeyType.ArrowUp) {
                selectedIndex = (selectedIndex - 1 + menuOptions.length) % menuOptions.length;
            } else if (keyStroke.getKeyType() == KeyType.ArrowDown) {
                selectedIndex = (selectedIndex + 1) % menuOptions.length;
            }
        }
    }

    private void drawMenuFrame() {
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(0, 0, "┌──────────────────────────┐");
        textGraphics.putString(0, 1, "│                          │");
        textGraphics.putString(0, 2, "│      Адмін Меню          │");
        textGraphics.putString(0, 3, "│                          │");
        textGraphics.putString(0, 4, "└──────────────────────────┘");
    }

    private void renderMenu(String[] menuOptions, int selectedIndex) throws IOException {
        for (int i = 0; i < menuOptions.length; i++) {
            if (i == selectedIndex) {
                highlightOption(menuOptions[i], 2, 7 + i);
            } else {
                textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
                textGraphics.putString(4, 7 + i, menuOptions[i]);
            }
        }

        String instructions = "Використовуйте ↑/↓ для навігації, Enter для вибору.";
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        textGraphics.putString(1, 6 + menuOptions.length + 5, instructions);
        screen.refresh();
    }

    private void highlightOption(String option, int x, int y) {
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        textGraphics.putString(x, y, "❯ ");
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(x + 2, y, option);
    }

    private void handleMovieManagement() throws IOException {
        // Обробка управління фільмами
        textGraphics.putString(2, 17, "Обробка управління фільмами...");
        screen.refresh();
        screen.readInput();
    }

    private void handleUserManagement() throws IOException {
        // Обробка управління користувачами
        textGraphics.putString(2, 17, "Обробка управління користувачами...");
        screen.refresh();
        screen.readInput();
    }

    private void handleStatisticsReview() throws IOException {
        // Обробка огляду статистики
        textGraphics.putString(2, 17, "Обробка огляду статистики...");
        screen.refresh();
        screen.readInput();
    }

    private void handleTicketManagement() throws IOException {
        // Обробка управління квитками
        textGraphics.putString(2, 17, "Обробка управління квитками...");
        screen.refresh();
        screen.readInput();
    }
}
