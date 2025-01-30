package com.adrian.cinemakiosk.appui.pages;

import com.adrian.cinemakiosk.persistence.entity.impl.Movie;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;
import java.util.List;

/**
 * Клас для відображення меню з постерами фільмів та їх детальним переглядом.
 */
public class PosterMenuView {

    private static final int MAX_VISIBLE_MOVIES = 5; // Максимальна кількість фільмів, що відображаються одночасно
    private final Screen screen; // Екран для відображення інтерфейсу
    private final TextGraphics textGraphics; // Графічний контекст для малювання тексту
    private final List<Movie> movieList; // Список фільмів
    private int selectedIndex = 0; // Індекс вибраного фільму
    private int topIndex = 0; // Індекс першого фільму на екрані

    /**
     * Конструктор для ініціалізації PosterMenuView.
     *
     * @param screen екран для відображення
     * @param textGraphics графічний контекст для малювання тексту
     * @param movieList список фільмів
     */
    public PosterMenuView(Screen screen, TextGraphics textGraphics, List<Movie> movieList) {
        this.screen = screen;
        this.textGraphics = textGraphics;
        this.movieList = movieList;
    }

    /**
     * Відображає меню постерів фільмів та обробляє введення користувача.
     *
     * @throws IOException якщо виникнуть проблеми при відображенні на екрані
     */
    public void displayPosterMenu() throws IOException {
        while (true) {
            renderMovieList(); // Відображення списку фільмів
            var keyStroke = screen.readInput(); // Зчитування введення користувача

            // Обробка клавіші Escape (вихід з меню)
            if (keyStroke.getKeyType() == KeyType.Escape) {
                return;
            }

            // Обробка навігації по списку фільмів
            if (keyStroke.getKeyType() == KeyType.ArrowUp && selectedIndex > 0) {
                selectedIndex--;
                if (selectedIndex < topIndex) {
                    topIndex--;
                }
            } else if (keyStroke.getKeyType() == KeyType.ArrowDown
                && selectedIndex < movieList.size() - 1) {
                selectedIndex++;
                if (selectedIndex >= topIndex + MAX_VISIBLE_MOVIES) {
                    topIndex++;
                }
            }
            // Обробка вибору фільму
            else if (keyStroke.getKeyType() == KeyType.Enter) {
                showMovieDetails(movieList.get(selectedIndex)); // Показ деталей вибраного фільму
            }
        }
    }

    /**
     * Відображає список фільмів на екрані.
     *
     * @throws IOException якщо виникнуть проблеми при відображенні на екрані
     */
    private void renderMovieList() throws IOException {
        screen.clear(); // Очищення екрану
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        textGraphics.putString(2, 1, "Каталог фільмів:"); // Заголовок
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));

        int y = 3; // Початкове положення для відображення фільмів
        // Виведення списку фільмів з можливістю прокручування
        for (int i = topIndex; i < Math.min(topIndex + MAX_VISIBLE_MOVIES, movieList.size()); i++) {
            Movie movie = movieList.get(i);
            String indicator = (i == selectedIndex) ? "❯ " : "  "; // Індикатор для вибраного фільму

            textGraphics.setForegroundColor(i == selectedIndex ?
                TextColor.Factory.fromString("#FFFF00") : // Жовтий для вибраного фільму
                TextColor.Factory.fromString("#FFFFFF"));

            textGraphics.putString(2, y, indicator + movie.getTitle()); // Виведення назви фільму
            textGraphics.setForegroundColor(TextColor.Factory.fromString("#888888"));
            textGraphics.putString(4, y + 1, "Жанр: " + movie.getGenre()); // Жанр
            textGraphics.putString(4, y + 2, "Рік: " + movie.getYear()); // Рік
            textGraphics.putString(4, y + 3, "Рейтинг: " + movie.getRating()); // Рейтинг

            y += 5; // Перехід до наступного фільму
        }

        screen.refresh(); // Оновлення екрану
    }

    /**
     * Відображає деталі вибраного фільму.
     *
     * @param movie фільм, для якого потрібно відобразити деталі
     * @throws IOException якщо виникнуть проблеми при відображенні на екрані
     */
    private void showMovieDetails(Movie movie) throws IOException {
        screen.clear(); // Очищення екрану
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        textGraphics.putString(2, 2, "Деталі фільму:"); // Заголовок
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));

        // Виведення детальної інформації про фільм
        textGraphics.putString(2, 4, "Назва: " + movie.getTitle());
        textGraphics.putString(2, 5, "Жанр: " + movie.getGenre());
        textGraphics.putString(2, 6, "Рік: " + movie.getYear());
        textGraphics.putString(2, 7, "Режисер: " + movie.getDirector());
        textGraphics.putString(2, 8, "Рейтинг: " + movie.getRating());
        textGraphics.putString(2, 9, "Опис: ");
        textGraphics.putString(2, 10, movie.getDescription().length() > 50 ?
            movie.getDescription().substring(0, 50) + "..." : // Виведення частини опису
            movie.getDescription());

        textGraphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
        textGraphics.putString(2, 14, "Натисніть будь-яку клавішу для повернення"); // Підказка для повернення

        screen.refresh(); // Оновлення екрану
        screen.readInput(); // Очікування введення користувача
    }
}
