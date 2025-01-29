package com.adrian.cinemakiosk.appui.pages;

import com.adrian.cinemakiosk.persistence.entity.impl.User;
import com.adrian.cinemakiosk.persistence.repository.impl.UserRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AddBalanceMenuView {

    private static final String USER_FILE = "data/user.json";
    private final Screen screen;
    private final TextGraphics textGraphics;
    private final UserRepository userRepository;
    private final User currentUser;
    private double balance;
    private String inputAmount = "";

    public AddBalanceMenuView(Screen screen, TextGraphics textGraphics,
        UserRepository userRepository, User currentUser) {
        this.screen = screen;
        this.textGraphics = textGraphics;
        this.userRepository = userRepository;
        this.currentUser = currentUser;
        this.balance = currentUser.getBalance();
    }

    public double showMenu() throws IOException {
        while (true) {
            renderMenu(); // Намалювати меню
            var keyStroke = screen.readInput();

            // Якщо натиснута клавіша ESC, оновлюємо баланс і виходимо
            if (keyStroke.getKeyType() == KeyType.Escape) {
                userRepository.updateBalance(currentUser.getEmail(), balance); // Оновлюємо баланс у репозиторії
                return balance; // Виходимо з меню
            }

            // Якщо натиснута клавіша Backspace - видаляємо останній символ
            else if (keyStroke.getKeyType() == KeyType.Backspace && !inputAmount.isEmpty()) {
                inputAmount = inputAmount.substring(0, inputAmount.length() - 1);
            }

            // Якщо натиснута клавіша Enter, перевіряємо введену суму
            else if (keyStroke.getKeyType() == KeyType.Enter) {
                try {
                    double amount = Double.parseDouble(inputAmount); // Перетворюємо введену суму в число
                    if (amount != 0 && balance + amount >= 0) { // Перевірка, чи коректна сума
                        balance += amount; // Оновлюємо баланс
                        inputAmount = ""; // Очищаємо введену суму
                        userRepository.updateBalance(currentUser.getEmail(), balance); // Оновлюємо баланс у репозиторії

                        // Очищаємо область для відображення балансу
                        textGraphics.putString(2, 3, "                        "); // Очищаємо попередній баланс

                        // Виводимо новий баланс
                        textGraphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
                        textGraphics.putString(2, 3, "Поточний баланс: " + balance + " грн");

                        // Оновлюємо екран
                        screen.refresh();
                    }
                } catch (NumberFormatException e) {
                    // Якщо введене значення не є числом
                    inputAmount = "";
                    textGraphics.setForegroundColor(TextColor.Factory.fromString("#FF0000"));
                    textGraphics.putString(2, 4, "Помилка: введіть число.");
                    screen.refresh(); // Оновлюємо екран
                }
            }

            // Якщо натиснута клавіша для введення числа або мінуса
            else if (keyStroke.getCharacter() != null && (
                Character.isDigit(keyStroke.getCharacter()) || keyStroke.getCharacter() == '-')) {
                inputAmount += keyStroke.getCharacter();
            }
        }
    }


    private void renderMenu() throws IOException {
        screen.clear();
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        textGraphics.putString(2, 1, "💰 Поповнення балансу");

        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(2, 3, "Поточний баланс: " + balance + " грн");
        textGraphics.putString(2, 5, "Введіть суму поповнення: ");
        textGraphics.putString(2, 6, inputAmount);

        textGraphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
        textGraphics.putString(2, 8, "Enter - підтвердити | Esc - вихід");

        screen.refresh();
    }

    private void saveBalanceToFile() {
        try {
            Gson gson = new Gson();
            JsonObject userJson = new JsonObject();
            userJson.addProperty("balance", balance);

            try (FileWriter writer = new FileWriter(USER_FILE)) {
                gson.toJson(userJson, writer);
            }
        } catch (IOException e) {
            System.err.println("Помилка збереження балансу: " + e.getMessage());
        }
    }

    private double loadBalanceFromFile() {
        try (FileReader reader = new FileReader(USER_FILE)) {
            Gson gson = new Gson();
            JsonObject userJson = gson.fromJson(reader, JsonObject.class);
            return userJson.has("balance") ? userJson.get("balance").getAsDouble() : 0.0;
        } catch (IOException e) {
            System.err.println("Помилка завантаження балансу: " + e.getMessage());
            return 0.0;
        }
    }
}