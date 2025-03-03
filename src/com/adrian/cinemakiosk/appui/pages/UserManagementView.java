package com.adrian.cinemakiosk.appui.pages;

import com.adrian.cinemakiosk.persistence.entity.impl.User;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Iterator;

/**
 * Клас для управління користувачами у системі.
 * Він дозволяє переглядати список користувачів, видаляти користувачів за ID та повертатися до головного меню.
 */
public class UserManagementView {

    private final Screen screen;
    private final TextGraphics textGraphics;
    private final String userFilePath = "data/users.json";

    /**
     * Конструктор для ініціалізації екрану та графічного контексту.
     *
     * @param screen         екран для відображення
     * @param textGraphics   графічний контекст для малювання на екрані
     */
    public UserManagementView(Screen screen, TextGraphics textGraphics) {
        this.screen = screen;
        this.textGraphics = textGraphics;
    }

    /**
     * Відображає головне меню для управління користувачами з трьома опціями:
     * перегляд користувачів, видалення користувача, повернення до головного меню.
     *
     * @throws IOException в разі проблем з відображенням на екрані
     */
    public void showMenu() throws IOException {
        String[] menuOptions = {
            "1. Переглянути користувачів",
            "2. Видалити користувача",
            "3. Повернутися до меню"
        };
        int selectedIndex = 0;

        while (true) {
            screen.clear();
            drawMenuFrame();
            renderMenu(menuOptions, selectedIndex);
            var keyStroke = screen.readInput();

            if (keyStroke.getKeyType() == KeyType.Escape) {
                return;
            }
            if (keyStroke.getKeyType() == KeyType.Enter) {
                switch (selectedIndex) {
                    case 0 -> showAllUsers();
                    case 1 -> deleteUserById();
                    case 2 -> { return; }
                }
            }
            if (keyStroke.getKeyType() == KeyType.ArrowUp) {
                selectedIndex = (selectedIndex - 1 + menuOptions.length) % menuOptions.length;
            } else if (keyStroke.getKeyType() == KeyType.ArrowDown) {
                selectedIndex = (selectedIndex + 1) % menuOptions.length;
            }
        }
    }

    /**
     * Малює рамку меню для управління користувачами.
     *
     * @throws IOException в разі проблем з відображенням на екрані
     */
    private void drawMenuFrame() throws IOException {
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(0, 0, "┌────────────────────────────────┐");
        textGraphics.putString(0, 1, "│                                │");
        textGraphics.putString(0, 2, "      Управління користувачами   ");
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
     * Відображає меню з опціями для управління користувачами.
     *
     * @param menuOptions список опцій меню
     * @param selectedIndex індекс вибраної опції
     * @throws IOException в разі проблем з відображенням на екрані
     */
    private void renderMenu(String[] menuOptions, int selectedIndex) throws IOException {
        for (int i = 0; i < menuOptions.length; i++) {
            textGraphics.setForegroundColor(i == selectedIndex ? TextColor.Factory.fromString("#FFFF00") : TextColor.Factory.fromString("#FFFFFF"));
            textGraphics.putString(2, 4 + i, (i == selectedIndex ? "❯ " : "  ") + menuOptions[i]);
        }
        screen.refresh();
    }

    /**
     * Виводить список усіх користувачів з файлу.
     * Якщо користувачів немає, виводить повідомлення про відсутність даних.
     *
     * @throws IOException в разі проблем з відображенням на екрані
     */
    private void showAllUsers() throws IOException {
        screen.clear();
        List<User> users = readUsersFromFile();

        // Заголовок таблиці
        textGraphics.putString(2, 1, "┌───────┬──────────────────────┐");
        textGraphics.putString(2, 2, "│  ID   │      Ім'я            │");
        textGraphics.putString(2, 3, "├───────┼──────────────────────┤");

        // Виведення користувачів
        int row = 4;
        for (User user : users) {
            textGraphics.putString(2, row++, String.format("│ %-5d │ %-18s   │", user.getId(), user.getUsername()));
        }

        // Нижній кінець таблиці
        textGraphics.putString(2, row, "└───────┴──────────────────────┘");

        screen.refresh();
        screen.readInput();
    }

    /**
     * Видаляє користувача за його ID.
     * Якщо користувач не знайдений, виводить відповідне повідомлення.
     *
     * @throws IOException в разі проблем з відображенням на екрані
     */
    private void deleteUserById() throws IOException {
        screen.clear();
        textGraphics.putString(2, 1, "Введіть ID користувача для видалення (Esc для виходу): ");
        String userIdInput = getInput(3);
        if (userIdInput == null) return;

        try {
            int userId = Integer.parseInt(userIdInput);
            List<User> users = readUsersFromFile();
            Iterator<User> iterator = users.iterator();
            boolean userFound = false;

            while (iterator.hasNext()) {
                User user = iterator.next();
                if (user.getId() == userId) {
                    iterator.remove();
                    userFound = true;
                    break;
                }
            }

            if (userFound) {
                saveUsersToFile(users);
                textGraphics.setForegroundColor(TextColor.ANSI.YELLOW);
                textGraphics.putString(2, 5, "Користувач з ID " + userId + " видалений.");
            } else {
                textGraphics.setForegroundColor(TextColor.ANSI.RED);
                textGraphics.putString(2, 5, "Користувача з таким ID не знайдено.");
            }
        } catch (NumberFormatException e) {
            textGraphics.setForegroundColor(TextColor.ANSI.RED);
            textGraphics.putString(2, 5, "Невірний формат ID!");
        }

        screen.refresh();
        screen.readInput();
    }

    /**
     * Отримує введення від користувача для певної опції.
     *
     * @param inputLine рядок, на якому буде відображатися введення
     * @return введення користувача
     * @throws IOException в разі проблем з відображенням на екрані
     */
    private String getInput(int inputLine) throws IOException {
        StringBuilder input = new StringBuilder();
        screen.refresh();
        while (true) {
            var keyStroke = screen.readInput();

            if (keyStroke.getKeyType() == KeyType.Escape) {
                return null;
            }

            if (keyStroke.getKeyType() == KeyType.Enter) {
                return input.toString().trim();
            } else if (keyStroke.getKeyType() == KeyType.Backspace && input.length() > 0) {
                input.deleteCharAt(input.length() - 1);
            } else if (keyStroke.getCharacter() != null && keyStroke.getCharacter() != '\u0008') {
                input.append(keyStroke.getCharacter());
            }

            textGraphics.putString(2, inputLine, input.toString() + " ");
            screen.refresh();
        }
    }
    /**
     * Читає список користувачів з файлу.
     *
     * @return список користувачів
     * @throws IOException в разі помилки при читанні з файлу
     */
    private List<User> readUsersFromFile() throws IOException {
        Gson gson = new Gson();
        Type userListType = new TypeToken<List<User>>(){}.getType();
        try (Reader reader = new FileReader(userFilePath)) {
            return gson.fromJson(reader, userListType);
        } catch (FileNotFoundException e) {
            return List.of();
        }
    }

    /**
     * Зберігає список користувачів до файлу.
     *
     * @param users список користувачів для збереження
     * @throws IOException в разі помилки при записі у файл
     */
    private void saveUsersToFile(List<User> users) throws IOException {
        Gson gson = new Gson();
        try (Writer writer = new FileWriter(userFilePath)) {
            gson.toJson(users, writer);
        }
    }
}