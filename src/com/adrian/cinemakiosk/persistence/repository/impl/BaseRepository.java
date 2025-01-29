package com.adrian.cinemakiosk.persistence.repository.impl;

import com.adrian.cinemakiosk.persistence.repository.Repository;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

abstract class BaseRepository<T> implements Repository<T> {

    private final String filePath;
    private final List<T> cache;
    private final Gson gson;

    public BaseRepository(String filePath, Type type) {
        this.filePath = filePath;
        this.cache = new ArrayList<>();
        this.gson = new Gson();
        loadFromFile(type);
    }

    private void loadFromFile(Type type) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                saveToFile();
            } catch (IOException e) {
                System.err.println("Failed to create file: " + e.getMessage());
            }
        }
        try (Reader reader = new FileReader(filePath)) {
            List<T> loadedItems = gson.fromJson(reader, type);
            if (loadedItems != null) {
                cache.clear();
                cache.addAll(loadedItems);
            }
        } catch (IOException e) {
            System.err.println("Failed to load items: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(cache, writer);
        } catch (IOException e) {
            System.err.println("Failed to save items: " + e.getMessage());
        }
    }

    @Override
    public void add(T item) {
        cache.add(item);
        saveToFile();
    }

    @Override
    public T getById(int id) {
        return cache.stream().filter(item -> getId(item) == id).findFirst().orElse(null);
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(cache);
    }

    // Інтегрований метод для отримання елементів
    public List<T> getItems() {
        return getAll();  // Використовуємо вже існуючий метод getAll
    }

    @Override
    public void update(T updatedItem) {
        T existingItem = getById(getId(updatedItem));
        if (existingItem != null) {
            cache.remove(existingItem);
            cache.add(updatedItem);
            saveToFile();
        }
    }

    @Override
    public void deleteById(int id) {
        cache.removeIf(item -> getId(item) == id);
        saveToFile();
    }

    @Override
    public List<T> filter(String fieldName, Object value) {
        return cache.stream().filter(item -> matchesField(item, fieldName, value))
            .collect(Collectors.toList());
    }

    protected abstract int getId(T item);

    protected abstract boolean matchesField(T item, String fieldName, Object value);
}
