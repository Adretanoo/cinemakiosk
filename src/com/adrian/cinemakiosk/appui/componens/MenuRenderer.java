package com.adrian.cinemakiosk.appui.componens;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;

/**
 * Клас, що відповідає за візуальне відображення меню на екрані.
 * Меню складається з кількох опцій, одна з яких виділяється при навігації.
 */
public class MenuRenderer {

    private final TextGraphics textGraphics;
    private final Screen screen;

    /**
     * Конструктор для ініціалізації MenuRenderer з об'єктами TextGraphics і Screen.
     *
     * @param textGraphics об'єкт для малювання тексту на екрані.
     * @param screen об'єкт екрана для відображення вмісту.
     */
    public MenuRenderer(TextGraphics textGraphics, Screen screen) {
        this.textGraphics = textGraphics;
        this.screen = screen;
    }

    /**
     * Відображає меню з кількома опціями, підсвічуючи вибрану опцію.
     *
     * @param menuOptions масив рядків, що містить опції меню.
     * @param selectedIndex індекс вибраної опції.
     * @throws IOException якщо виникне помилка при оновленні екрану.
     */
    public void renderMenu(String[] menuOptions, int selectedIndex) throws IOException {
        clearScreen();
        drawMenuFrame();

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

    /**
     * Очищає екран перед відображенням нового контенту.
     *
     * @throws IOException якщо виникне помилка при очищенні екрану.
     */
    private void clearScreen() throws IOException {
        screen.clear();
    }

    /**
     * Малює рамку навколо меню для покращення візуального сприйняття.
     */
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

    /**
     * Виділяє опцію меню, додаючи символ "❯" перед текстом опції та змінюючи її колір.
     *
     * @param option опція меню.
     * @param x координата по осі X для відображення опції.
     * @param y координата по осі Y для відображення опції.
     */
    private void highlightOption(String option, int x, int y) {
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        textGraphics.putString(x, y, "❯ ");
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(x + 2, y, option);
    }
}
