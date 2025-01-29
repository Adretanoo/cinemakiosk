package com.adrian.cinemakiosk.appui.pages;

import com.adrian.cinemakiosk.persistence.entity.impl.Movie;
import com.adrian.cinemakiosk.persistence.entity.impl.Ticket;
import com.adrian.cinemakiosk.persistence.entity.impl.User;
import com.adrian.cinemakiosk.persistence.repository.impl.MovieRepository;
import com.adrian.cinemakiosk.persistence.repository.impl.TicketRepository;
import com.adrian.cinemakiosk.persistence.repository.impl.UserRepository;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserMenuView {

    private final Screen screen;
    private final TextGraphics textGraphics;
    private final String username;
    private double balance;
    private UserRepository userRepository;
    private String email;
    private static final String FILE_PATH = "data/tickets.json";

    public UserMenuView(Screen screen, TextGraphics textGraphics, String username,
        String email, double balance, UserRepository userRepository) {
        this.screen = screen;
        this.textGraphics = textGraphics;
        this.username = username;
        this.email = email;
        this.balance = balance;
        this.userRepository = userRepository;
    }

    public void showMenu() throws IOException {
        String[] menuOptions = {
            "1. Перегляд афіш",
            "2. Пошук афіш",
            "3. Поповнити баланс",
            "4. Купівля квитків",
            "5. Налаштування",
            "6. Вийти"
        };

        int selectedIndex = 0;

        while (true) {
            renderMenu(menuOptions, selectedIndex);

            var keyStroke = screen.readInput();
            if (keyStroke.getKeyType() == KeyType.Escape) {
                return;
            }

            if (keyStroke.getKeyType() == KeyType.ArrowUp && selectedIndex > 0) {
                selectedIndex--;
            } else if (keyStroke.getKeyType() == KeyType.ArrowDown
                && selectedIndex < menuOptions.length - 1) {
                selectedIndex++;
            } else if (keyStroke.getKeyType() == KeyType.Enter) {
                handleMenuSelection(selectedIndex);
            }
        }
    }

    private void renderMenu(String[] menuOptions, int selectedIndex) throws IOException {
        screen.clear();

        // Малюємо рамку
        drawMenuFrame();

        // Виводимо ім'я користувача
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(2, 2, "Користувач: " + username);
        textGraphics.putString(2, 3, "Баланс: " + balance);

        // Виведення опцій меню
        for (int i = 0; i < menuOptions.length; i++) {
            if (i == selectedIndex) {
                highlightOption(menuOptions[i], 2, 7 + i);  // Виділяємо вибраний пункт
            } else {
                textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
                textGraphics.putString(4, 7 + i, menuOptions[i]);
            }
        }

        // Інструкція по навігації
        String instructions = "Використовуйте ↑/↓ для навігації, Enter для вибору.";
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        textGraphics.putString(1, 9 + menuOptions.length + 1, instructions);

        screen.refresh();
    }

    private void drawMenuFrame() throws IOException {
        // Очищаємо область для старого балансу
        textGraphics.putString(0, 3, "                        "); // Очищаємо попередній баланс

        // Виводимо рамку меню
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(0, 0, "┌──────────────────────────┐");
        textGraphics.putString(0, 1, "│                          │");
        textGraphics.putString(0, 2, "      " + username + "      ");  // Ім'я користувача

        // Виводимо оновлений баланс
        textGraphics.putString(0, 3, "       " + balance + "      ");

        textGraphics.putString(0, 4, "│                          │");
        textGraphics.putString(0, 5, "└──────────────────────────┘");

        // Виведення іншого вмісту меню
        textGraphics.putString(0, 6, "┌──────────────────────────┐");
        textGraphics.putString(0, 7, "│                          │");
        textGraphics.putString(0, 8, "│                          │");
        textGraphics.putString(0, 9, "│                          │");
        textGraphics.putString(0, 10, "│                          │");
        textGraphics.putString(0, 11, "│                          │");
        textGraphics.putString(0, 12, "│                          │");
        textGraphics.putString(0, 13, "│                          │");
        textGraphics.putString(0, 14, "└──────────────────────────┘");

        // Оновлюємо екран
        screen.refresh();
    }

    private void highlightOption(String option, int x, int y) {
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        textGraphics.putString(x, y, "❯ ");
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(x + 2, y, option);
    }

    private void handleMenuSelection(int selectedIndex) throws IOException {
        switch (selectedIndex) {
            case 0:
                System.out.println("Перейшли до Афіші");

                // Отримуємо список фільмів
                MovieRepository movieRepository = new MovieRepository();
                List<Movie> movieList = movieRepository.getAll();

                // Перевіряємо, чи не порожній список
                if (movieList.isEmpty()) {
                    System.out.println("Список фільмів порожній!");
                } else {
                    System.out.println("Фільми завантажено.");
                }

                // Передаємо список фільмів до PosterMenuView
                PosterMenuView posterMenuView = new PosterMenuView(screen, textGraphics, movieList);
                posterMenuView.displayPosterMenu();
                break;
            case 1:
                System.out.println("Перейшли до Пошуку фільмів");
                MovieRepository searchRepository = new MovieRepository();
                List<Movie> searchMovieList = searchRepository.getAll();
                MovieSearchView movieSearchView = new MovieSearchView(screen, textGraphics, searchMovieList);
                movieSearchView.displaySearchMenu();
                break;
            case 2:
                System.out.println("Поповнення балансу");

                // Отримуємо користувача
                User currentUser = userRepository.findByEmail(email);
                if (currentUser == null) {
                    System.out.println("Помилка: Користувач не авторизований!");
                    break;
                }

                // Створюємо AddBalanceMenuView і передаємо туди поточного користувача
                AddBalanceMenuView addBalanceMenuView = new AddBalanceMenuView(screen, textGraphics, userRepository, currentUser);
                addBalanceMenuView.showMenu();  // Показуємо меню для поповнення балансу

                // Після поповнення балансу, оновлюємо користувача і перерисовуємо меню
                currentUser = userRepository.findByEmail(email);  // Отримуємо оновленого користувача
                balance = currentUser.getBalance();  // Оновлюємо баланс

                // Оновлюємо меню з новим балансом
                renderMenu(new String[]{
                    "1. Перегляд афіш",
                    "2. Пошук афіш",
                    "3. Поповнити баланс",
                    "4. Купівля квитків",
                    "5. Налаштування",
                    "6. Вийти"
                }, 0);  // Оновлюємо меню з новим балансом

                System.out.println("Новий баланс: " + currentUser.getBalance());
                break;

            case 3:
                System.out.println("Перейшли до купівлі квитків");

                TicketRepository ticketRepository = new TicketRepository();
                List<Ticket> availableTickets = ticketRepository.getAvailableTickets(); // Отримуємо доступні квитки

                if (availableTickets.isEmpty()) {
                    System.out.println("Немає доступних квитків.");
                    break;
                }

                currentUser = userRepository.findByEmail(email);
                // Використовуємо наявні screen та textGraphics
                TicketPurchaseView ticketPurchaseView = new TicketPurchaseView(screen, textGraphics, currentUser, ticketRepository, userRepository);
                ticketPurchaseView.showPurchaseMenu();
                break;

            case 4:
                System.out.println("Перейшли до Налаштувань");
                // Налаштування
                break;
            case 5:
                System.out.println("Вийшли з програми");
                screen.clear();
                return;
            default:
                throw new IllegalStateException("Невідомий вибір: " + selectedIndex);
        }
    }


}
