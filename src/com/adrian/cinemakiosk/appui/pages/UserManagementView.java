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

public class UserManagementView {

    private final Screen screen;
    private final TextGraphics textGraphics;
    private final String userFilePath = "data/users.json";

    public UserManagementView(Screen screen, TextGraphics textGraphics) {
        this.screen = screen;
        this.textGraphics = textGraphics;
    }

    public void showMenu() throws IOException {
        String[] menuOptions = {
            "1. Переглянути всіх користувачів",
            "2. Видалити користувача за ID",
            "3. Повернутися до меню"
        };
        int selectedIndex = 0;

        while (true) {
            screen.clear();
            renderMenu(menuOptions, selectedIndex);
            screen.refresh();
            var keyStroke = screen.readInput();

            if (keyStroke.getKeyType() == KeyType.Escape || selectedIndex == 2) {
                return;
            }
            if (keyStroke.getKeyType() == KeyType.Enter) {
                switch (selectedIndex) {
                    case 0 -> showAllUsers();
                    case 1 -> deleteUserById();
                }
            }
            if (keyStroke.getKeyType() == KeyType.ArrowUp) {
                selectedIndex = (selectedIndex - 1 + menuOptions.length) % menuOptions.length;
            } else if (keyStroke.getKeyType() == KeyType.ArrowDown) {
                selectedIndex = (selectedIndex + 1) % menuOptions.length;
            }
        }
    }

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

            textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
            textGraphics.putString(2, inputLine, input.toString() + " ");
            screen.refresh();
        }
    }

    private void showAllUsers() throws IOException {
        screen.clear();
        List<User> users = readUsersFromFile();
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
        int row = 1;
        for (User user : users) {
            textGraphics.putString(2, row++, "ID: " + user.getId() + " | Ім'я: " + user.getUsername());
        }
        screen.refresh();
        screen.readInput();
    }

    private void deleteUserById() throws IOException {
        screen.clear();
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE); // Білий колір для тексту
        textGraphics.putString(2, 1, "Введіть ID користувача для видалення (Esc для виходу): ");
        String userIdInput = getInput(3);
        if (userIdInput == null) return;  // Вихід при натисканні Esc

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
                // Оновлюємо файл після видалення
                saveUsersToFile(users);
                textGraphics.setForegroundColor(TextColor.ANSI.YELLOW); // Жовтий колір для успішного повідомлення
                textGraphics.putString(2, 5, "✔️ Користувач з ID " + userId + " видалений.");
            } else {
                textGraphics.setForegroundColor(TextColor.ANSI.RED); // Червоний колір для помилки
                textGraphics.putString(2, 5, "❌ Користувача з таким ID не знайдено.");
            }
        } catch (NumberFormatException e) {
            textGraphics.setForegroundColor(TextColor.ANSI.RED); // Червоний колір для помилки
            textGraphics.putString(2, 5, "❌ Невірний формат ID!");
        }

        screen.refresh();
        screen.readInput();
    }

    private List<User> readUsersFromFile() throws IOException {
        Gson gson = new Gson();
        Type userListType = new TypeToken<List<User>>(){}.getType();
        try (Reader reader = new FileReader(userFilePath)) {
            return gson.fromJson(reader, userListType);
        } catch (FileNotFoundException e) {
            return List.of();
        }
    }

    private void saveUsersToFile(List<User> users) throws IOException {
        Gson gson = new Gson();
        try (Writer writer = new FileWriter(userFilePath)) {
            gson.toJson(users, writer);
        }
    }

    private void renderMenu(String[] menuOptions, int selectedIndex) {
        for (int i = 0; i < menuOptions.length; i++) {
            if (i == selectedIndex) {
                textGraphics.setForegroundColor(TextColor.ANSI.YELLOW); // Жовтий колір для виділеного елементу
            } else {
                textGraphics.setForegroundColor(TextColor.ANSI.WHITE); // Білий колір для решти
            }
            textGraphics.putString(2, i + 1, menuOptions[i]);
        }
    }
}
