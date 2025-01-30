package com.adrian.cinemakiosk.appui.pages;

import com.adrian.cinemakiosk.persistence.entity.impl.Movie;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class MovieManagementView {

    private final Screen screen;
    private final TextGraphics textGraphics;
    private final String movieFilePath = "data/movies.json";

    public MovieManagementView(Screen screen, TextGraphics textGraphics) {
        this.screen = screen;
        this.textGraphics = textGraphics;
    }

    public void showMenu() throws IOException {
        String[] menuOptions = {"1. Додати фільм", "2. Видалити фільм", "3. Переглянути фільми", "4. Повернутися до меню"};
        int selectedIndex = 0;

        while (true) {
            screen.clear();
            renderMenu(menuOptions, selectedIndex);
            screen.refresh();
            var keyStroke = screen.readInput();

            if (keyStroke.getKeyType() == KeyType.Escape || selectedIndex == 3) {
                return;
            }
            if (keyStroke.getKeyType() == KeyType.Enter) {
                switch (selectedIndex) {
                    case 0 -> handleAddMovie();
                    case 1 -> handleRemoveMovie();
                    case 2 -> handleViewMovies();
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

            textGraphics.setForegroundColor(TextColor.ANSI.WHITE); // Білий колір для тексту
            textGraphics.putString(2, inputLine, input.toString() + " ");
            screen.refresh();
        }
    }

    private void handleAddMovie() throws IOException {
        screen.clear();
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE); // Білий колір для тексту
        textGraphics.putString(2, 1, "Введіть назву фільму (Esc для виходу): ");
        String title = getInput(3);
        if (title == null) return;

        textGraphics.putString(2, 5, "Введіть опис фільму: ");
        String description = getInput(7);
        if (description == null) return;

        textGraphics.putString(2, 9, "Введіть рік фільму: ");
        String year = getInput(11);
        if (year == null) return;

        textGraphics.putString(2, 13, "Введіть жанр фільму: ");
        String genre = getInput(15);
        if (genre == null) return;

        textGraphics.putString(2, 17, "Введіть режисера: ");
        String director = getInput(19);
        if (director == null) return;

        double rating = -1;
        while (rating < 0 || rating > 10) {
            textGraphics.putString(2, 21, "Введіть рейтинг фільму (0-10): ");
            String ratingInput = getInput(23);
            if (ratingInput == null) return;

            try {
                rating = Double.parseDouble(ratingInput);
                if (rating < 0 || rating > 10) {
                    textGraphics.putString(2, 25, "❌ Рейтинг повинен бути між 0 і 10.");
                }
            } catch (NumberFormatException e) {
                textGraphics.putString(2, 25, "❌ Невірний формат! Введіть число.");
            }
            screen.refresh();
        }

        textGraphics.putString(2, 27, "Введіть URL постеру: ");
        String posterUrl = getInput(29);
        if (posterUrl == null) return;

        Movie movie = new Movie(title, description, Integer.parseInt(year), rating, posterUrl, genre, director);
        List<Movie> movies = readMoviesFromFile();
        int newId = movies.stream().mapToInt(Movie::getId).max().orElse(0) + 1;
        movie.setId(newId);
        movies.add(movie);
        saveMoviesToFile(movies);

        textGraphics.putString(2, 31, "✅ Фільм успішно додано!");
        screen.refresh();
        screen.readInput();
    }

    private void handleRemoveMovie() throws IOException {
        screen.clear();
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE); // Білий колір для тексту
        textGraphics.putString(2, 1, "Введіть ID фільму для видалення (Esc для виходу): ");
        String movieIdInput = getInput(3);
        if (movieIdInput == null) return;  // Вихід при натисканні Esc

        try {
            int movieId = Integer.parseInt(movieIdInput);
            List<Movie> movies = readMoviesFromFile();
            Movie movieToRemove = movies.stream()
                .filter(movie -> movie.getId() == movieId)
                .findFirst()
                .orElse(null);

            if (movieToRemove != null) {
                movies.remove(movieToRemove);
                saveMoviesToFile(movies);
                textGraphics.setForegroundColor(TextColor.ANSI.YELLOW); // Жовтий колір для успішного повідомлення
                textGraphics.putString(2, 5, "✅ Фільм успішно видалено!");
            } else {
                textGraphics.setForegroundColor(TextColor.ANSI.YELLOW); // Жовтий колір для повідомлення про помилку
                textGraphics.putString(2, 5, "❌ Фільм з таким ID не знайдено.");
            }
        } catch (NumberFormatException e) {
            textGraphics.setForegroundColor(TextColor.ANSI.YELLOW); // Жовтий колір для повідомлення про помилку
            textGraphics.putString(2, 5, "❌ Невірний формат ID!");
        }

        screen.refresh();
        screen.readInput();
    }

    private List<Movie> readMoviesFromFile() throws IOException {
        Gson gson = new Gson();
        Type movieListType = new TypeToken<List<Movie>>(){}.getType();
        try (Reader reader = new FileReader(movieFilePath)) {
            return gson.fromJson(reader, movieListType);
        } catch (FileNotFoundException e) {
            return List.of();
        }
    }

    private void saveMoviesToFile(List<Movie> movies) throws IOException {
        Gson gson = new Gson();
        try (Writer writer = new FileWriter(movieFilePath)) {
            gson.toJson(movies, writer);
        }
    }

    private void renderMenu(String[] menuOptions, int selectedIndex) {
        for (int i = 0; i < menuOptions.length; i++) {
            if (i == selectedIndex) {
                textGraphics.setForegroundColor(TextColor.ANSI.YELLOW);
            } else {
                textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
            }
            textGraphics.putString(2, i + 1, menuOptions[i]);
        }
    }
    private void handleViewMovies() throws IOException {
        screen.clear();
        List<Movie> movies = readMoviesFromFile();
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
        textGraphics.putString(2, 1, "🎬 Список фільмів:");

        if (movies.isEmpty()) {
            textGraphics.putString(2, 3, "❌ Немає доступних фільмів.");
        } else {
            int line = 3;
            for (Movie movie : movies) {
                textGraphics.putString(2, line++, "ID: " + movie.getId() + " | Назва: " + movie.getTitle() + " | Жанр: " + movie.getGenre());
            }
        }

        screen.refresh();
        screen.readInput();
    }

}
