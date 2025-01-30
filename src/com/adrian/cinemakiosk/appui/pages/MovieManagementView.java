package com.adrian.cinemakiosk.appui.pages;

import com.adrian.cinemakiosk.persistence.entity.impl.Movie;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
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
        String[] menuOptions = {
            "1. Додати фільм",
            "2. Видалити фільм",
            "3. Переглянути фільми",
            "4. Повернутися"
        };
        int selectedIndex = 0;

        while (true) {
            screen.clear();
            drawMenuFrame();
            renderMenu(menuOptions, selectedIndex);
            var keyStroke = screen.readInput();

            if (keyStroke.getKeyType() == KeyType.Escape) {
                return;
            }
            if (keyStroke.getKeyType() == KeyType.Enter) {
                switch (selectedIndex) {
                    case 0 -> handleAddMovie();
                    case 1 -> handleRemoveMovie();
                    case 2 -> handleViewMovies();
                    case 3 -> { return; }
                }
            }
            if (keyStroke.getKeyType() == KeyType.ArrowUp) {
                selectedIndex = (selectedIndex - 1 + menuOptions.length) % menuOptions.length;
            } else if (keyStroke.getKeyType() == KeyType.ArrowDown) {
                selectedIndex = (selectedIndex + 1) % menuOptions.length;
            }
        }
    }

    private void drawMenuFrame() throws IOException {
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(0, 0, "┌────────────────────────────────┐");
        textGraphics.putString(0, 1, "│                                │");
        textGraphics.putString(0, 2, "       Управління фільмами      ");
        textGraphics.putString(0, 3, "├────────────────────────────────┤");
        textGraphics.putString(0, 4, "│                                │");
        textGraphics.putString(0, 5, "│                                │");
        textGraphics.putString(0, 6, "│                                │");
        textGraphics.putString(0, 7, "│                                │");
        textGraphics.putString(0, 8, "│                                │");
        textGraphics.putString(0, 9, "│                                │");
        textGraphics.putString(0, 10, "└────────────────────────────────┘");
        screen.refresh();
    }

    private void renderMenu(String[] menuOptions, int selectedIndex) throws IOException {
        for (int i = 0; i < menuOptions.length; i++) {
            textGraphics.setForegroundColor(i == selectedIndex ? TextColor.Factory.fromString("#FFFF00") : TextColor.Factory.fromString("#FFFFFF"));
            textGraphics.putString(2, 4 + i, (i == selectedIndex ? "❯ " : "  ") + menuOptions[i]);
        }
        screen.refresh();
    }

    private void handleAddMovie() throws IOException {
        screen.clear();
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
                    textGraphics.putString(2, 25, "Рейтинг повинен бути між 0 і 10.");
                }
            } catch (NumberFormatException e) {
                textGraphics.putString(2, 25, "Невірний формат! Введіть число.");
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

        textGraphics.putString(2, 31, "Фільм успішно додано!");
        screen.refresh();
        screen.readInput();
    }

    private void handleRemoveMovie() throws IOException {
        screen.clear();
        textGraphics.putString(2, 1, "Введіть ID фільму для видалення:");
        String movieIdInput = getInput(3);
        if (movieIdInput == null) return;

        try {
            int movieId = Integer.parseInt(movieIdInput);
            List<Movie> movies = readMoviesFromFile();
            movies.removeIf(movie -> movie.getId() == movieId);
            saveMoviesToFile(movies);
        } catch (NumberFormatException e) {
            return;
        }
    }

    private void handleViewMovies() throws IOException {
        screen.clear();
        List<Movie> movies = readMoviesFromFile();

        int selectedIndex = 0;
        int startIndex = 0;
        int itemsPerPage = 10; // Кількість фільмів на одній сторінці

        while (true) {
            screen.clear();
            drawMoviesFrame(); // Малюємо рамку для фільмів

            // Вивести фільми на екран
            for (int i = startIndex; i < Math.min(startIndex + itemsPerPage, movies.size()); i++) {
                Movie movie = movies.get(i);
                textGraphics.setForegroundColor(i == selectedIndex ? TextColor.Factory.fromString("#FFFF00") : TextColor.Factory.fromString("#FFFFFF"));
                textGraphics.putString(2, 3 + (i - startIndex), String.format("%-5d %-30s %-5d %-10.1f %-15s",
                    movie.getId(),
                    movie.getTitle(),
                    movie.getYear(),
                    movie.getRating(),
                    movie.getGenre()));
            }

            screen.refresh();

            // Читання введення користувача
            KeyStroke keyStroke = screen.readInput();
            if (keyStroke.getKeyType() == KeyType.Escape) {
                return;
            } else if (keyStroke.getKeyType() == KeyType.ArrowDown) {
                if (selectedIndex < Math.min(startIndex + itemsPerPage, movies.size()) - 1) {
                    selectedIndex++;
                } else if (startIndex + itemsPerPage < movies.size()) {
                    // Якщо на кінці сторінки, прокручувати вниз
                    startIndex++;
                }
            } else if (keyStroke.getKeyType() == KeyType.ArrowUp) {
                if (selectedIndex > 0) {
                    selectedIndex--;
                } else if (startIndex > 0) {
                    // Якщо на початку сторінки, прокручувати вгору
                    startIndex--;
                }
            }
        }
    }

    private void drawMoviesFrame() throws IOException {
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(0, 0, "┌───────────────────────────────────────────────────────────────────────────────────────┐");
        textGraphics.putString(0, 1, "│                         Список фільмів                                                │");
        textGraphics.putString(0, 2, "├───────────────────────────────────────────────────────────────────────────────────────┤");

    }






    private String getInput(int inputLine) throws IOException {
        StringBuilder input = new StringBuilder();

        while (true) {
            textGraphics.putString(2, inputLine, input.toString() + " ");
            screen.refresh();

            KeyStroke keyStroke = screen.readInput();

            if (keyStroke.getKeyType() == KeyType.Enter) {
                return input.toString().trim();
            } else if (keyStroke.getKeyType() == KeyType.Backspace) {
                if (input.length() > 0) {
                    input.deleteCharAt(input.length() - 1);
                }
            } else if (keyStroke.getKeyType() == KeyType.Escape) {
                return null;
            } else if (keyStroke.getCharacter() != null) {
                input.append(keyStroke.getCharacter());
            }
        }
    }


    private List<Movie> readMoviesFromFile() throws IOException {
        Gson gson = new Gson();
        Type movieListType = new TypeToken<List<Movie>>(){}.getType();
        try (Reader reader = new FileReader(movieFilePath)) {
            return gson.fromJson(reader, movieListType);
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }
    }

    private void saveMoviesToFile(List<Movie> movies) throws IOException {
        Gson gson = new Gson();
        try (Writer writer = new FileWriter(movieFilePath)) {
            gson.toJson(movies, writer);
        }
    }
}