package com.adrian.cinemakiosk.appui.componens;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;

public class MenuRenderer {

    private final TextGraphics textGraphics;
    private final Screen screen;

    public MenuRenderer(TextGraphics textGraphics, Screen screen) {
        this.textGraphics = textGraphics;
        this.screen = screen;
    }

    public void renderMenu(String[] menuOptions, int selectedIndex) throws IOException {
        clearScreen();
        drawMenuFrame();

        for (int i = 0; i < menuOptions.length; i++) {
            if (i == selectedIndex) {
                highlightOption(menuOptions[i], 2, 7 + i); // Зміщення пунктів меню трохи нижче
            } else {
                textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
                textGraphics.putString(4, 7 + i, menuOptions[i]); // Теж зрушуємо вниз
            }
        }

        String instructions = "Використовуйте ↑/↓ для навігації, Enter для вибору.";
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        textGraphics.putString(1, 6 + menuOptions.length + 5, instructions);

        screen.refresh();
    }

    private void clearScreen() throws IOException {
        screen.clear();
    }

    private void drawMenuFrame() {
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(0, 0, "┌──────────────────────────┐");
        textGraphics.putString(0, 1, "│                          │");
        textGraphics.putString(0, 2, "│      ГОЛОВНЕ МЕНЮ        │");
        textGraphics.putString(0, 3, "│                          │");
        textGraphics.putString(0, 4, "└──────────────────────────┘");

        textGraphics.putString(0, 5, "┌──────────────────────────┐");
        textGraphics.putString(0, 6, "│                          │");
        textGraphics.putString(0, 7, "│                          │");
        textGraphics.putString(0, 8, "│                          │");
        textGraphics.putString(0, 9, "│                          │");
        textGraphics.putString(0, 10, "│                          │");
        textGraphics.putString(0, 11, "│                          │");
        textGraphics.putString(0, 12, "│                          │");
        textGraphics.putString(0, 13, "└──────────────────────────┘");
    }

    private void highlightOption(String option, int x, int y) {
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        textGraphics.putString(x, y, "❯ ");
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(x + 2, y, option);
    }
}
