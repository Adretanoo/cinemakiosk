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

/**
 * Клас для відображення меню користувача в терміналі.
 * Забезпечує функціональність навігації між різними пунктами меню користувача.
 */
public class UserMenuView {

    private final Screen screen;
    private final TextGraphics textGraphics;
    private final String username;
    private double balance;
    private UserRepository userRepository;
    private String email;
    private static final String FILE_PATH = "data/tickets.json";

    /**
     * Конструктор для ініціалізації екземпляра меню користувача.
     *
     * @param screen           екземпляр екрану для відображення тексту.
     * @param textGraphics     екземпляр графічного тексту для відображення.
     * @param username         ім'я користувача.
     * @param email            електронна адреса користувача.
     * @param balance          баланс користувача.
     * @param userRepository   репозиторій для доступу до користувачів.
     */
    public UserMenuView(Screen screen, TextGraphics textGraphics, String username,
        String email, double balance, UserRepository userRepository) {
        this.screen = screen;
        this.textGraphics = textGraphics;
        this.username = username;
        this.email = email;
        this.balance = balance;
        this.userRepository = userRepository;
    }

    /**
     * Відображає меню користувача та обробляє вибір користувача.
     *
     * @throws IOException Якщо виникає помилка при відображенні на екрані.
     */
    public void showMenu() throws IOException {
        String[] menuOptions = {
            "1. Перегляд афіш",
            "2. Пошук афіш",
            "3. Поповнити баланс",
            "4. Купівля квитків",
            "5. Мої квитки",
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


    /**
     * Відображає пункти меню на екрані.
     *
     * @param menuOptions     масив рядків з пунктами меню.
     * @param selectedIndex   індекс вибраного пункту меню.
     * @throws IOException    Якщо виникає помилка при відображенні на екрані.
     */
    private void renderMenu(String[] menuOptions, int selectedIndex) throws IOException {
        screen.clear();

        drawMenuFrame();

        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(2, 2, "Користувач: " + username);
        textGraphics.putString(2, 3, "Баланс: " + balance);

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
        textGraphics.putString(1, 9 + menuOptions.length + 1, instructions);

        screen.refresh();
    }

    /**
     * Малює рамку меню.
     *
     * @throws IOException Якщо виникає помилка при відображенні на екрані.
     */
    private void drawMenuFrame() throws IOException {
        textGraphics.putString(0, 3, "                        "); // Очищаємо попередній баланс

        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(0, 0, "┌──────────────────────────┐");
        textGraphics.putString(0, 1, "│                          │");
        textGraphics.putString(0, 2, "      " + username + "      ");  // Ім'я користувача

        textGraphics.putString(0, 3, "       " + balance + "      ");

        textGraphics.putString(0, 4, "│                          │");
        textGraphics.putString(0, 5, "└──────────────────────────┘");

        textGraphics.putString(0, 6, "┌──────────────────────────┐");
        textGraphics.putString(0, 7, "│                          │");
        textGraphics.putString(0, 8, "│                          │");
        textGraphics.putString(0, 9, "│                          │");
        textGraphics.putString(0, 10, "│                          │");
        textGraphics.putString(0, 11, "│                          │");
        textGraphics.putString(0, 12, "│                          │");
        textGraphics.putString(0, 13, "│                          │");
        textGraphics.putString(0, 14, "└──────────────────────────┘");

        screen.refresh();
    }

    /**
     * Виділяє вибраний пункт меню жовтим кольором.
     *
     * @param option   вибраний пункт меню.
     * @param x        координата по осі X для відображення.
     * @param y        координата по осі Y для відображення.
     */
    private void highlightOption(String option, int x, int y) {
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        textGraphics.putString(x, y, "❯ ");
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(x + 2, y, option);
    }

    /**
     * Обробляє вибір пункту меню та викликає відповідні методи для кожної опції.
     *
     * @param selectedIndex індекс вибраного пункту меню.
     * @throws IOException Якщо виникає помилка при обробці вибору.
     */
    private void handleMenuSelection(int selectedIndex) throws IOException {
        switch (selectedIndex) {
            case 0:
                MovieRepository movieRepository = new MovieRepository();
                List<Movie> movieList = movieRepository.getAll();

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
                User currentUser = userRepository.findByEmail(email);
                AddBalanceMenuView addBalanceMenuView = new AddBalanceMenuView(screen, textGraphics, userRepository, currentUser);
                addBalanceMenuView.showMenu();
                currentUser = userRepository.findByEmail(email);
                balance = currentUser.getBalance();

                // Оновлюємо меню з новим балансом
                renderMenu(new String[]{
                    "1. Перегляд афіш",
                    "2. Пошук афіш",
                    "3. Поповнити баланс",
                    "4. Купівля квитків",
                    "5. Мої квитки",
                    "6. Вийти"
                }, 0);  // Оновлюємо меню з новим балансом

                System.out.println("Новий баланс: " + currentUser.getBalance());
                break;

            case 3:

                TicketRepository ticketRepository = new TicketRepository();
                List<Ticket> availableTickets = ticketRepository.getAvailableTickets();
                currentUser = userRepository.findByEmail(email);
                TicketPurchaseView ticketPurchaseView = new TicketPurchaseView(screen, textGraphics, currentUser, ticketRepository, userRepository);
                ticketPurchaseView.showPurchaseMenu();
                break;

            case 4:
                new MyTicketView(screen, textGraphics).showTickets();
                break;
            case 5:
                new MenuView(screen,textGraphics).showMainMenu();
                break;

            default:
                throw new IllegalStateException("Невідомий вибір: " + selectedIndex);
        }
    }



}
