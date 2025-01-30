package com.adrian.cinemakiosk.domain.servise;

import com.adrian.cinemakiosk.domain.util.FileManager;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.util.Map;

public class MovieService {
    private final FileManager<String> fileManager;

    public MovieService() {
        Type type = new TypeToken<Map<String, String>>() {}.getType();
        this.fileManager = new FileManager<>("data/movies.json", type);
    }

    public void addMovie(String movieId, String movieTitle) {
        Map<String, String> movies = fileManager.readFromFile();
        movies.put(movieId, movieTitle);
        fileManager.writeToFile(movies);
    }

    public String getMovie(String movieId) {
        Map<String, String> movies = fileManager.readFromFile();
        return movies.getOrDefault(movieId, "Movie not found");
    }

    public void removeMovie(String movieId) {
        Map<String, String> movies = fileManager.readFromFile();
        movies.remove(movieId);
        fileManager.writeToFile(movies);
    }
}
