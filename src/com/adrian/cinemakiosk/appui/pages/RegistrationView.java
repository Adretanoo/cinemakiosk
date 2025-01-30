package com.adrian.cinemakiosk.appui.pages;

import com.adrian.cinemakiosk.domain.servise.EmailService;
import com.adrian.cinemakiosk.domain.servise.validators.UserValidator;
import com.adrian.cinemakiosk.persistence.entity.impl.User;
import com.adrian.cinemakiosk.persistence.repository.impl.UserRepository;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;
import org.mindrot.bcrypt.BCrypt;

/**
 * Клас для відображення форми реєстрації користувача в системі.
 */
public class RegistrationView {

    private final Screen screen;
    private final TextGraphics textGraphics;
    private final UserRepository userRepository;

    /**
     * Конструктор для ініціалізації RegistrationView.
     *
     * @param screen екран для відображення
     * @param textGraphics графічний контекст для малювання тексту
     * @param userRepository репозиторій для збереження користувачів
     */
    public RegistrationView(Screen screen, TextGraphics textGraphics, UserRepository userRepository) {
        this.screen = screen;
        this.textGraphics = textGraphics;
        this.userRepository = userRepository;
    }

    /**
     * Показує форму реєстрації користувача і обробляє введення даних.
     *
     * @throws IOException якщо виникнуть проблеми при відображенні на екрані
     */
    public void showRegistrationForm() throws IOException {
        while (true) {
            screen.clear(); // Очищення екрану

            // Виведення заголовку форми реєстрації
            textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
            textGraphics.putString(0, 0, "┌──────────────────────────┐");
            textGraphics.putString(0, 1, "│                          │");
            textGraphics.putString(0, 2, "│      РЕЄСТРАЦІЯ          │");
            textGraphics.putString(0, 3, "│                          │");
            textGraphics.putString(0, 4, "└──────────────────────────┘");

            // Виведення підказки для завершення реєстрації
            textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
            textGraphics.putString(2, 18, "Натисніть ESC, щоб завершити реєстрацію.");
            textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));

            // Введення ім'я користувача
            String username = promptInput("Введіть ім'я користувача:", 6);
            if (username == null) {
                screen.clear();
                return;
            }

            try {
                UserValidator.validateUsername(username);
            } catch (IllegalArgumentException e) {
                displayError(e.getMessage(), 13);
                continue;
            }

            // Введення електронної пошти
            String email = promptInput("Введіть електронну пошту:", 8);
            if (email == null) {
                screen.clear();
                return;
            }

            try {
                UserValidator.validateEmail(email);
            } catch (IllegalArgumentException e) {
                displayError(e.getMessage(), 13);
                continue;
            }

            if (userRepository.existsByEmail(email)) {
                displayError("Користувач з такою поштою вже існує.", 13);
                continue;
            }

            // Відправка коду підтвердження
            String verificationCode = EmailService.generateAndSendVerificationCode(email);

            // Підтвердження коду
            if (!promptVerificationCode(verificationCode, 10)) {
                displayError("Невірний код підтвердження.", 13);
                continue;
            }

            // Введення пароля
            String password = promptPassword("Введіть пароль:", 12);
            if (password == null) {
                screen.clear();
                return;
            }

            try {
                UserValidator.validatePassword(password);
            } catch (IllegalArgumentException e) {
                displayError(e.getMessage(), 13);
                continue;
            }

            // Хешування пароля
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            // Вибір ролі користувача
            String role = promptRole("Виберіть роль (admin/user):", 14);
            if (role == null) {
                screen.clear();
                return;
            }

            User newUser = new User(0, username, email, hashedPassword, role);

            try {
                userRepository.save(newUser);

                textGraphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
                textGraphics.putString(2, 17, "Реєстрація успішна! Натисніть будь-яку клавішу для продовження.");
                textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
                screen.refresh();
                screen.readInput();
                return;
            } catch (Exception e) {
                displayError("Помилка збереження користувача: " + e.getMessage(), 13);
            }
        }
    }

    /**
     * Запитує введення коду підтвердження у користувача.
     *
     * @param verificationCode код підтвердження для порівняння
     * @param yPos позиція для виведення на екрані
     * @return true, якщо код підтвердження введений правильно
     * @throws IOException якщо виникнуть проблеми при відображенні на екрані
     */
    private boolean promptVerificationCode(String verificationCode, int yPos) throws IOException {
        textGraphics.putString(2, yPos, "Введіть код підтвердження:");
        screen.refresh();

        StringBuilder input = new StringBuilder();
        while (true) {
            var keyStroke = screen.readInput();
            if (keyStroke.getKeyType() == KeyType.Escape) {
                return false;
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

        return input.toString().trim().equals(verificationCode);
    }

    /**
     * Запитує користувача на введення тексту (наприклад, ім'я користувача, пошту).
     *
     * @param prompt підказка для введення
     * @param yPos позиція для виведення на екрані
     * @return введений текст або null, якщо ESC натиснуто
     * @throws IOException якщо виникнуть проблеми при відображенні на екрані
     */
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

    /**
     * Запитує користувача на введення пароля (пароль буде прихований зірочками).
     *
     * @param prompt підказка для введення пароля
     * @param yPos позиція для виведення на екрані
     * @return введений пароль
     * @throws IOException якщо виникнуть проблеми при відображенні на екрані
     */
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

    /**
     * Запитує користувача на введення ролі (admin або user).
     *
     * @param prompt підказка для введення ролі
     * @param yPos позиція для виведення на екрані
     * @return введена роль
     * @throws IOException якщо виникнуть проблеми при відображенні на екрані
     */
    private String promptRole(String prompt, int yPos) throws IOException {
        textGraphics.putString(2, yPos, prompt);
        screen.refresh();

        while (true) {
            String role = promptInput("(admin/user)", yPos + 1);
            if (role == null || role.isEmpty()) {
                return null;
            }
            if (role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("user")) {
                return role.toLowerCase();
            } else {
                displayError("Невірна роль. Оберіть admin або user.", yPos + 2);
            }
        }
    }

    /**
     * Очищає область введення для наступного введеного значення.
     *
     * @param yPos позиція на екрані, де слід очистити текст
     * @throws IOException якщо виникнуть проблеми при відображенні на екрані
     */
    private void clearInputArea(int yPos) throws IOException {
        textGraphics.putString(2, yPos + 1, " ".repeat(50));
        screen.refresh();
    }

    /**
     * Виводить повідомлення про помилку.
     *
     * @param message повідомлення про помилку
     * @param yPos позиція для виведення на екрані
     * @throws IOException якщо виникнуть проблеми при відображенні на екрані
     */
    private void displayError(String message, int yPos) throws IOException {
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FF0000"));
        textGraphics.putString(2, yPos, "Помилка: " + message);
        screen.refresh();
        screen.readInput();
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
    }
}
