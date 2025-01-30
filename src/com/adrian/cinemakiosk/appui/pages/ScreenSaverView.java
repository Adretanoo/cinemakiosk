package com.adrian.cinemakiosk.appui.pages;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;

/**
 * Клас для відображення заставки з привітанням на екрані.
 */
public class ScreenSaverView {

    private final Screen screen;
    private final TextGraphics textGraphics;

    /**
     * Конструктор для ініціалізації ScreenSaverView.
     *
     * @param screen екран для відображення
     * @param textGraphics графічний контекст для малювання тексту
     */
    public ScreenSaverView(Screen screen, TextGraphics textGraphics) {
        this.screen = screen;
        this.textGraphics = textGraphics;
    }

    /**
     * Показує екран привітання для користувача.
     *
     * @throws IOException якщо виникнуть проблеми при відображенні на екрані
     */
    public void showGreeting() throws IOException {
        clearScreen();

        // Повідомлення для привітання користувача
        String[] message = {
            "╔════════════════════════════════════════════════════════════════╗",
            "║                   ЛАСКАВО ПРОСИМО ДО CINEMAKIOSK!              ║",
            "╚════════════════════════════════════════════════════════════════╝"
        };

        // Обчислюємо початкову позицію для вертикального центрування повідомлення
        int yStart = (screen.getTerminalSize().getRows() - message.length) / 2;

        // Виведення кожного рядка повідомлення на екран
        for (int i = 0; i < message.length; i++) {
            textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
            textGraphics.putString(
                (screen.getTerminalSize().getColumns() - message[i].length()) / 2,
                yStart + i,
                message[i]
            );
        }

        // Повідомлення про продовження
        String continueMessage = "Натисніть будь-яку клавішу, щоб продовжити...";
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(
            (screen.getTerminalSize().getColumns() - continueMessage.length()) / 2,
            yStart + message.length + 2,
            continueMessage
        );

        screen.refresh();  // Оновлення екрану
        screen.readInput();  // Очікування введення від користувача
    }

    /**
     * Очищає екран перед відображенням нового контенту.
     *
     * @throws IOException якщо виникнуть проблеми при очищенні екрану
     */
    private void clearScreen() throws IOException {
        screen.clear();
    }
}
