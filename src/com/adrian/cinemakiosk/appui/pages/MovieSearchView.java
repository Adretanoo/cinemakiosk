package com.adrian.cinemakiosk.appui.pages;

import com.adrian.cinemakiosk.persistence.entity.impl.Movie;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Клас для відображення та обробки пошуку фільмів.
 */
public class MovieSearchView {

    private static final int MAX_VISIBLE_RESULTS = 5; // Максимальна кількість видимих результатів
    private final Screen screen; // Екран для відображення інтерфейсу
    private final TextGraphics textGraphics; // Текстові графіки для малювання
    private final List<Movie> movieList; // Список всіх фільмів
    private int selectedIndex = 0; // Індекс вибраного фільму
    private int topIndex = 0; // Індекс верхнього фільму, що відображається

    /**
     * Конструктор для ініціалізації MovieSearchView.
     *
     * @param screen екран для відображення
     * @param textGraphics графічний контекст для малювання тексту
     * @param movieList список доступних фільмів
     */
    public MovieSearchView(Screen screen, TextGraphics textGraphics, List<Movie> movieList) {
        this.screen = screen;
        this.textGraphics = textGraphics;
        this.movieList = movieList;
    }

    /**
     * Відображає меню пошуку фільмів і обробляє введення користувача.
     *
     * @throws IOException якщо виникнуть проблеми при введенні або виведенні на екран
     */
    public void displaySearchMenu() throws IOException {
        String searchQuery = "";
        while (true) {
            renderSearchMenu(searchQuery);
            var keyStroke = screen.readInput();

            if (keyStroke.getKeyType() == KeyType.Escape) {
                return; // Вихід з пошуку
            } else if (keyStroke.getKeyType() == KeyType.Backspace && !searchQuery.isEmpty()) {
                searchQuery = searchQuery.substring(0, searchQuery.length() - 1); // Видалення символа
            } else if (keyStroke.getKeyType() == KeyType.Enter) {
                List<Movie> searchResults = searchMovies(searchQuery); // Пошук фільмів
                displaySearchResults(searchResults); // Відображення результатів
                searchQuery = ""; // Очищення запиту
            } else if (keyStroke.getCharacter() != null) {
                searchQuery += keyStroke.getCharacter(); // Додавання символа до запиту
            }
        }
    }

    /**
     * Відображає інтерфейс для введення запиту пошуку.
     *
     * @param searchQuery поточний запит пошуку
     * @throws IOException якщо виникнуть проблеми при малюванні на екрані
     */
    private void renderSearchMenu(String searchQuery) throws IOException {
        screen.clear();
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        textGraphics.putString(2, 1, "Пошук фільмів");
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(2, 3, "Введіть назву або жанр фільму: ");
        textGraphics.putString(2, 4, searchQuery);
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
        textGraphics.putString(2, 6, "Enter - пошук | Esc - вихід");
        screen.refresh();
    }

    /**
     * Виконує пошук фільмів за запитом користувача.
     *
     * @param query запит для пошуку
     * @return список знайдених фільмів
     */
    private List<Movie> searchMovies(String query) {
        return movieList.stream()
            .filter(movie -> movie.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                movie.getGenre().toLowerCase().contains(query.toLowerCase()))
            .collect(Collectors.toList());
    }

    /**
     * Відображає результати пошуку на екрані.
     *
     * @param searchResults список знайдених фільмів
     * @throws IOException якщо виникнуть проблеми при відображенні результатів
     */
    private void displaySearchResults(List<Movie> searchResults) throws IOException {
        if (searchResults.isEmpty()) {
            screen.clear();
            textGraphics.setForegroundColor(TextColor.Factory.fromString("#FF0000"));
            textGraphics.putString(2, 3, "Фільми не знайдені.");
            screen.refresh();
            screen.readInput();
            return;
        }

        selectedIndex = 0;
        topIndex = 0;
        while (true) {
            renderSearchResults(searchResults);
            var keyStroke = screen.readInput();

            if (keyStroke.getKeyType() == KeyType.Escape) {
                return; // Вихід із перегляду результатів
            } else if (keyStroke.getKeyType() == KeyType.ArrowUp && selectedIndex > 0) {
                selectedIndex--;
                if (selectedIndex < topIndex) {
                    topIndex--; // Переміщення прокрутки вгору
                }
            } else if (keyStroke.getKeyType() == KeyType.ArrowDown
                && selectedIndex < searchResults.size() - 1) {
                selectedIndex++;
                if (selectedIndex >= topIndex + MAX_VISIBLE_RESULTS) {
                    topIndex++; // Переміщення прокрутки вниз
                }
            } else if (keyStroke.getKeyType() == KeyType.Enter) {
                showMovieDetails(searchResults.get(selectedIndex)); // Показати деталі фільму
            }
        }
    }

    /**
     * Відображає знайдені фільми у вигляді списку з можливістю прокручування.
     *
     * @param searchResults список знайдених фільмів
     * @throws IOException якщо виникнуть проблеми при відображенні результатів
     */
    private void renderSearchResults(List<Movie> searchResults) throws IOException {
        screen.clear();
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        textGraphics.putString(2, 1, "Результати пошуку:");

        int y = 3;
        for (int i = topIndex; i < Math.min(topIndex + MAX_VISIBLE_RESULTS, searchResults.size()); i++) {
            Movie movie = searchResults.get(i);
            String indicator = (i == selectedIndex) ? "❯ " : "  ";

            textGraphics.setForegroundColor(i == selectedIndex ?
                TextColor.Factory.fromString("#FFFF00") :
                TextColor.Factory.fromString("#FFFFFF"));

            textGraphics.putString(2, y,
                indicator + movie.getTitle() + " (" + movie.getYear() + ")");
            textGraphics.putString(4, y + 1, "Жанр: " + movie.getGenre());
            textGraphics.putString(4, y + 2, "Рейтинг: " + movie.getRating());
            y += 4;
        }
        screen.refresh();
    }

    /**
     * Відображає деталі обраного фільму.
     *
     * @param movie вибраний фільм для перегляду деталей
     * @throws IOException якщо виникнуть проблеми при відображенні інформації
     */
    private void showMovieDetails(Movie movie) throws IOException {
        screen.clear();
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        textGraphics.putString(2, 2, "Деталі фільму:");
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));

        textGraphics.putString(2, 4, "Назва: " + movie.getTitle());
        textGraphics.putString(2, 5, "Жанр: " + movie.getGenre());
        textGraphics.putString(2, 6, "Рік: " + movie.getYear());
        textGraphics.putString(2, 7, "Режисер: " + movie.getDirector());
        textGraphics.putString(2, 8, "Рейтинг: " + movie.getRating());
        textGraphics.putString(2, 9, "Опис: ");
        String description = movie.getDescription();
        for (int i = 0; i < description.length(); i += 50) {
            textGraphics.putString(2, 10 + i / 50,
                description.substring(i, Math.min(i + 50, description.length())));
        }
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
        textGraphics.putString(2, 14, "Натисніть будь-яку клавішу для повернення");
        screen.refresh();
        screen.readInput();
    }
}
