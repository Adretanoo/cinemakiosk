package com.adrian.cinemakiosk.appui.pages;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;

/**
 * Клас, що відповідає за відображення та управління меню адміністратора.
 * Адміністратор може вибирати різні опції для управління фільмами, користувачами, квитками або виходу з меню.
 */
public class AdminMenuView {
    private final Screen screen;
    private final TextGraphics textGraphics;
    private final String username;

    /**
     * Конструктор для ініціалізації AdminMenuView з необхідними залежностями.
     *
     * @param screen екран для відображення вмісту.
     * @param textGraphics об'єкт для малювання тексту на екрані.
     * @param username ім'я адміністратора, яке буде відображено в меню.
     */
    public AdminMenuView(Screen screen, TextGraphics textGraphics, String username) {
        this.screen = screen;
        this.textGraphics = textGraphics;
        this.username = username;
    }

    /**
     * Відображає меню адміністратора, надаючи можливість вибору різних опцій.
     * Користувач може вибрати опцію за допомогою клавіш стрілок і підтвердити вибір клавішею Enter.
     *
     * @throws IOException якщо виникне помилка при відображенні екрану або читанні введених даних.
     */
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

    /**
     * Малює рамку меню на екрані.
     *
     * @throws IOException якщо виникне помилка при відображенні екрану.
     */
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

    /**
     * Відображає варіанти меню, підсвічуючи вибрану опцію.
     *
     * @param menuOptions масив рядків з варіантами меню.
     * @param selectedIndex індекс вибраної опції.
     * @throws IOException якщо виникне помилка при відображенні екрану.
     */
    private void renderMenu(String[] menuOptions, int selectedIndex) throws IOException {
        for (int i = 0; i < menuOptions.length; i++) {
            textGraphics.setForegroundColor(i == selectedIndex ? TextColor.Factory.fromString("#FFFF00") : TextColor.Factory.fromString("#FFFFFF"));
            textGraphics.putString(2, 5 + i, (i == selectedIndex ? "❯ " : "  ") + menuOptions[i]);
        }
        screen.refresh();
    }

    /**
     * Обробляє вибір користувача в меню та викликає відповідні дії.
     *
     * @param selectedIndex індекс вибраної опції в меню.
     * @throws IOException якщо виникне помилка при виклику відповідних методів.
     */
    private void handleMenuSelection(int selectedIndex) throws IOException {
        switch (selectedIndex) {
            case 0 -> handleMovieManagement();
            case 1 -> handleUserManagement();
            case 2 -> handleTicketManagement();
            case 3 -> new MenuView(screen,textGraphics).showMainMenu();
            default -> throw new IllegalStateException("Невідомий вибір: " + selectedIndex);
        }
    }

    /**
     * Викликає меню для управління фільмами.
     *
     * @throws IOException якщо виникне помилка при виклику меню управління фільмами.
     */
    private void handleMovieManagement() throws IOException {
        MovieManagementView movieManagementView = new MovieManagementView(screen, textGraphics);
        movieManagementView.showMenu();
    }

    /**
     * Викликає меню для управління користувачами.
     *
     * @throws IOException якщо виникне помилка при виклику меню управління користувачами.
     */
    private void handleUserManagement() throws IOException {
        UserManagementView userManagementView = new UserManagementView(screen, textGraphics);
        userManagementView.showMenu();
    }

    /**
     * Викликає меню для управління квитками.
     *
     * @throws IOException якщо виникне помилка при виклику меню управління квитками.
     */
    private void handleTicketManagement() throws IOException {
        TicketManagementView ticketManagementView = new TicketManagementView(screen, textGraphics);
        ticketManagementView.showMenu();
    }
}
