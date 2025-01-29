package com.adrian.cinemakiosk.appui.pages;

import com.adrian.cinemakiosk.persistence.entity.impl.Movie;
import com.adrian.cinemakiosk.persistence.repository.impl.MovieRepository;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;
import java.util.List;

public class UserMenuView {

    private final Screen screen;
    private final TextGraphics textGraphics;
    private final String username;
    private double balance;

    public UserMenuView(Screen screen, TextGraphics textGraphics, String username, double balance) {
        this.screen = screen;
        this.textGraphics = textGraphics;
        this.username = username;
        this.balance = balance;
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

    private void drawMenuFrame() {
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
                MovieSearchView movieSearchView = new MovieSearchView(screen, textGraphics,
                    searchMovieList);
                movieSearchView.displaySearchMenu();
                break;
            case 2:
                System.out.println("Поповнення балансу");
                AddBalanceMenuView addBalanceMenuView = new AddBalanceMenuView(screen, textGraphics,
                    balance);
                balance = addBalanceMenuView.showMenu();
                System.out.println("Новий баланс: " + balance);
                break;
            case 3:
                System.out.println("Перейшли до Пошуку фільмів");
                // Пошук фільмів
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
