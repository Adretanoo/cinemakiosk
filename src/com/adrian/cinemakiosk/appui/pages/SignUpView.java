package com.adrian.cinemakiosk.appui.pages;

import com.adrian.cinemakiosk.persistence.entity.impl.User;
import com.adrian.cinemakiosk.persistence.repository.impl.UserRepository;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;
import org.mindrot.bcrypt.BCrypt;

public class SignUpView {

    private static String loggedInUserEmail;
    private final Screen screen;
    private final TextGraphics textGraphics;
    private final UserRepository userRepository;

    public SignUpView(Screen screen, TextGraphics textGraphics, UserRepository userRepository) {
        this.screen = screen;
        this.textGraphics = textGraphics;
        this.userRepository = userRepository;
    }

    public void showLoginForm() throws IOException {
        while (true) {
            screen.clear();

            textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
            textGraphics.putString(0, 0, "┌──────────────────────────┐");
            textGraphics.putString(0, 1, "│                          │");
            textGraphics.putString(0, 2, "│      АВТОРИЗАЦІЯ         │");
            textGraphics.putString(0, 3, "│                          │");
            textGraphics.putString(0, 4, "└──────────────────────────┘");

            textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
            textGraphics.putString(2, 15, "Натисніть ESC, щоб завершити авторизацію.");
            textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));

            String email = promptInput("Введіть електронну пошту:", 6);
            if (email == null) {
                screen.clear();
                return;
            }

            User user = userRepository.findByEmail(email);
            if (user == null) {
                displayError("Користувача з такою поштою не знайдено.", 13);
                continue;
            }

            String password = promptPassword("Введіть пароль:", 8);
            if (password == null) {
                screen.clear();
                return;
            }

            // Перевірка паролю
            if (!BCrypt.checkpw(password, user.getPassword())) {
                displayError("Невірний пароль.", 13);
                continue;
            }

            // Авторизація успішна
            textGraphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
            textGraphics.putString(2, 17,
                "Авторизація успішна! Натисніть будь-яку клавішу для продовження.");
            textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
            screen.refresh();
            screen.readInput();

            // Перевірка ролі користувача і запуск відповідного меню
            if ("admin".equals(user.getRole())) {
                AdminMenuView adminMenuView = new AdminMenuView(screen, textGraphics,
                    user.getUsername());
                adminMenuView.showMenu();
            } else if ("user".equals(user.getRole())) {
                UserMenuView userMenuView = new UserMenuView(screen, textGraphics,
                    user.getUsername(), user.getEmail(), user.getBalance(), userRepository);
                userMenuView.showMenu();
            }

            return;
        }
    }

    private String promptInput(String prompt, int yPos) throws IOException {
        textGraphics.putString(2, yPos, prompt);
        screen.refresh();

        StringBuilder input = new StringBuilder();
        while (true) {
            var keyStroke = screen.readInput();
            if (keyStroke.getKeyType() == KeyType.Escape) {
                return null;
            }
            if (keyStroke.getCharacter() != null) {
                char c = keyStroke.getCharacter();
                if (c == '\r' || c == '\n') {
                    break;
                }
                if (c == '\b' || c == 127) {
                    if (input.length() > 0) {
                        input.deleteCharAt(input.length() - 1);
                    }
                } else {
                    input.append(c);
                }
            }

            clearInputArea(yPos);
            textGraphics.putString(2, yPos + 1, input.toString());
            screen.refresh();
        }

        return input.toString().trim();
    }

    private String promptPassword(String prompt, int yPos) throws IOException {
        textGraphics.putString(2, yPos, prompt);
        screen.refresh();

        StringBuilder input = new StringBuilder();
        while (true) {
            var keyStroke = screen.readInput();
            if (keyStroke.getKeyType() == KeyType.Escape) {
                return null;
            }

            if (keyStroke.getCharacter() != null) {
                char c = keyStroke.getCharacter();
                if (c == '\r' || c == '\n') {
                    break;
                }
                if (c == '\b' || c == 127) {
                    if (input.length() > 0) {
                        input.deleteCharAt(input.length() - 1);
                    }
                } else {
                    input.append(c);
                }
            }

            clearInputArea(yPos);
            textGraphics.putString(2, yPos + 1, "*".repeat(input.length()));
            screen.refresh();
        }

        return input.toString().trim();
    }

    private void clearInputArea(int yPos) throws IOException {
        textGraphics.putString(2, yPos + 1, " ".repeat(50));
        screen.refresh();
    }

    private void displayError(String message, int yPos) throws IOException {
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FF0000"));
        textGraphics.putString(2, yPos, "Помилка: " + message);
        screen.refresh();
        screen.readInput();
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
    }
}
