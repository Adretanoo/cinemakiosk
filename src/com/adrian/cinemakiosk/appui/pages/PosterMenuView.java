package com.adrian.cinemakiosk.appui.pages;

import com.adrian.cinemakiosk.persistence.entity.impl.Movie;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;
import java.util.List;

public class PosterMenuView {

    private static final int MAX_VISIBLE_MOVIES = 5;
    private final Screen screen;
    private final TextGraphics textGraphics;
    private final List<Movie> movieList;
    private int selectedIndex = 0;
    private int topIndex = 0;

    public PosterMenuView(Screen screen, TextGraphics textGraphics, List<Movie> movieList) {
        this.screen = screen;
        this.textGraphics = textGraphics;
        this.movieList = movieList;
    }

    public void displayPosterMenu() throws IOException {
        while (true) {
            renderMovieList();
            var keyStroke = screen.readInput();

            if (keyStroke.getKeyType() == KeyType.Escape) {
                return;
            }

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
            } else if (keyStroke.getKeyType() == KeyType.Enter) {
                showMovieDetails(movieList.get(selectedIndex));
            }
        }
    }

    private void renderMovieList() throws IOException {
        screen.clear();
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        textGraphics.putString(2, 1, "📽 Каталог фільмів:");
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));

        int y = 3;
        for (int i = topIndex; i < Math.min(topIndex + MAX_VISIBLE_MOVIES, movieList.size()); i++) {
            Movie movie = movieList.get(i);
            String indicator = (i == selectedIndex) ? "❯ " : "  ";

            textGraphics.setForegroundColor(i == selectedIndex ?
                TextColor.Factory.fromString("#FFFF00") :
                TextColor.Factory.fromString("#FFFFFF"));

            textGraphics.putString(2, y, indicator + movie.getTitle());
            textGraphics.setForegroundColor(TextColor.Factory.fromString("#888888"));
            textGraphics.putString(4, y + 1, "Жанр: " + movie.getGenre());
            textGraphics.putString(4, y + 2, "Рік: " + movie.getYear());
            textGraphics.putString(4, y + 3, "Рейтинг: " + movie.getRating());

            y += 5;
        }
        
        screen.refresh();
    }

    private void showMovieDetails(Movie movie) throws IOException {
        screen.clear();
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        textGraphics.putString(2, 2, "🎬 Деталі фільму:");
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));

        textGraphics.putString(2, 4, "Назва: " + movie.getTitle());
        textGraphics.putString(2, 5, "Жанр: " + movie.getGenre());
        textGraphics.putString(2, 6, "Рік: " + movie.getYear());
        textGraphics.putString(2, 7, "Режисер: " + movie.getDirector());
        textGraphics.putString(2, 8, "Рейтинг: " + movie.getRating());
        textGraphics.putString(2, 9, "Опис: ");
        textGraphics.putString(2, 10, movie.getDescription().length() > 50 ?
            movie.getDescription().substring(0, 50) + "..." :
            movie.getDescription());

        textGraphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
        textGraphics.putString(2, 14, "Натисніть будь-яку клавішу для повернення");

        screen.refresh();
        screen.readInput();
    }
}