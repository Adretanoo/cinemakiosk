package com.adrian.cinemakiosk.appui.pages;

import com.adrian.cinemakiosk.persistence.entity.impl.User;
import com.adrian.cinemakiosk.persistence.repository.impl.UserRepository;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;
import org.mindrot.bcrypt.BCrypt;

/**
 * Клас SignUpView відповідає за інтерфейс користувача для сторінки входу,
 * де користувачі можуть авторизуватися за допомогою електронної пошти та пароля.
 * Цей клас взаємодіє з UserRepository для отримання даних користувачів
 * та перевіряє їхні облікові дані під час авторизації.
 */
public class SignUpView {

    private static String loggedInUserEmail;
    private final Screen screen;
    private final TextGraphics textGraphics;
    private final UserRepository userRepository;

    /**
     * Конструктор для ініціалізації SignUpView з необхідними залежностями.
     *
     * @param screen екран, що використовується для відображення інтерфейсу користувача
     * @param textGraphics об'єкт графіки для відображення тексту на екрані
     * @param userRepository репозиторій, який керує даними користувачів
     */
    public SignUpView(Screen screen, TextGraphics textGraphics, UserRepository userRepository) {
        this.screen = screen;
        this.textGraphics = textGraphics;
        this.userRepository = userRepository;
    }

    /**
     * Відображає форму входу та дозволяє користувачу ввести свої дані для авторизації.
     * Якщо дані вірні, користувач авторизується та буде перенаправлений у відповідне меню.
     * Якщо авторизація не вдалася, показується повідомлення про помилку.
     *
     * @throws IOException якщо виникає помилка при зчитуванні вводу з екрану
     */
    public void showLoginForm() throws IOException {
        while (true) {
            screen.clear();

            // Відображення рамки авторизації
            textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
            textGraphics.putString(0, 0, "┌──────────────────────────┐");
            textGraphics.putString(0, 1, "│                          │");
            textGraphics.putString(0, 2, "│      АВТОРИЗАЦІЯ         │");
            textGraphics.putString(0, 3, "│                          │");
            textGraphics.putString(0, 4, "└──────────────────────────┘");

            // Інструкція для виходу
            textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
            textGraphics.putString(2, 15, "Натисніть ESC, щоб завершити авторизацію.");
            textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));

            // Отримання електронної пошти від користувача
            String email = promptInput("Введіть електронну пошту:", 6);
            if (email == null) {
                screen.clear();
                return;
            }

            // Перевірка, чи існує користувач у репозиторії
            User user = userRepository.findByEmail(email);
            if (user == null) {
                displayError("Користувача з такою поштою не знайдено.", 13);
                continue;
            }

            // Отримання пароля від користувача
            String password = promptPassword("Введіть пароль:", 8);
            if (password == null) {
                screen.clear();
                return;
            }

            // Перевірка пароля з хешованим значенням
            if (!BCrypt.checkpw(password, user.getPassword())) {
                displayError("Невірний пароль.", 13);
                continue;
            }

            // Повідомлення про успішну авторизацію
            textGraphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
            textGraphics.putString(2, 17, "Авторизація успішна! Натисніть будь-яку клавішу для продовження.");
            textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
            screen.refresh();
            screen.readInput();

            // Перевірка ролі користувача та відображення відповідного меню
            if ("admin".equals(user.getRole())) {
                AdminMenuView adminMenuView = new AdminMenuView(screen, textGraphics, user.getUsername());
                adminMenuView.showMenu();
            } else if ("user".equals(user.getRole())) {
                UserMenuView userMenuView = new UserMenuView(screen, textGraphics, user.getUsername(), user.getEmail(), user.getBalance(), userRepository);
                userMenuView.showMenu();
            }

            return;
        }
    }

    /**
     * Запитує користувача на введення тексту та повертає введене значення.
     *
     * @param prompt повідомлення, яке буде відображено користувачу
     * @param yPos вертикальна позиція на екрані для відображення запиту
     * @return введений рядок
     * @throws IOException якщо виникає помилка при зчитуванні вводу з екрану
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
     * Запитує користувача на введення пароля. Введене значення маскується зірочками.
     *
     * @param prompt повідомлення, яке буде відображено користувачу
     * @param yPos вертикальна позиція на екрані для відображення запиту
     * @return введений пароль
     * @throws IOException якщо виникає помилка при зчитуванні вводу з екрану
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
     * Очищає область вводу на екрані.
     *
     * @param yPos вертикальна позиція на екрані, де потрібно очистити область вводу
     * @throws IOException якщо виникає помилка при зчитуванні вводу з екрану
     */
    private void clearInputArea(int yPos) throws IOException {
        textGraphics.putString(2, yPos + 1, " ".repeat(50));
        screen.refresh();
    }

    /**
     * Відображає повідомлення про помилку на екрані.
     *
     * @param message повідомлення про помилку
     * @param yPos вертикальна позиція на екрані для відображення помилки
     * @throws IOException якщо виникає помилка при зчитуванні вводу з екрану
     */
    private void displayError(String message, int yPos) throws IOException {
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FF0000"));
        textGraphics.putString(2, yPos, "Помилка: " + message);
        screen.refresh();
        screen.readInput();
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
    }
}
