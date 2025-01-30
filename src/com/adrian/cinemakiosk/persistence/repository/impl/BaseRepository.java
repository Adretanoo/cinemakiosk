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

/**
 * Абстрактна реалізація інтерфейсу Repository для роботи з даними, які зберігаються у файлі.
 * Використовує бібліотеку Gson для серіалізації та десеріалізації об'єктів.
 *
 * @param <T> тип елементів, які зберігаються в репозиторії.
 */
abstract class BaseRepository<T> implements Repository<T> {

    private final String filePath;
    private final List<T> cache;
    private final Gson gson;

    /**
     * Конструктор для ініціалізації репозиторію з зазначеним шляхом до файлу і типом даних.
     *
     * @param filePath шлях до файлу, в якому зберігаються дані.
     * @param type тип даних, що зберігаються в репозиторії.
     */
    public BaseRepository(String filePath, Type type) {
        this.filePath = filePath;
        this.cache = new ArrayList<>();
        this.gson = new Gson();
        loadFromFile(type);
    }

    /**
     * Завантажує дані з файлу і зберігає їх в кеші.
     * Якщо файл не існує, створює його.
     *
     * @param type тип даних, що зберігаються.
     */
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

    /**
     * Зберігає поточний стан кешу в файл.
     */
    private void saveToFile() {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(cache, writer);
        } catch (IOException e) {
            System.err.println("Failed to save items: " + e.getMessage());
        }
    }

    /**
     * Додає новий елемент до репозиторію і зберігає його у файл.
     *
     * @param item елемент, який потрібно додати.
     */
    @Override
    public void add(T item) {
        cache.add(item);
        saveToFile();
    }

    /**
     * Отримує елемент за його ідентифікатором.
     *
     * @param id ідентифікатор елемента.
     * @return знайдений елемент або {@code null}, якщо елемент не знайдений.
     */
    @Override
    public T getById(int id) {
        return cache.stream().filter(item -> getId(item) == id).findFirst().orElse(null);
    }

    /**
     * Отримує всі елементи з репозиторію.
     *
     * @return список всіх елементів.
     */
    @Override
    public List<T> getAll() {
        return new ArrayList<>(cache);
    }

    /**
     * Отримує список всіх елементів.
     *
     * @return список всіх елементів.
     */
    public List<T> getItems() {
        return getAll();
    }

    /**
     * Оновлює існуючий елемент у репозиторії.
     *
     * @param updatedItem оновлений елемент.
     */
    @Override
    public void update(T updatedItem) {
        T existingItem = getById(getId(updatedItem));
        if (existingItem != null) {
            cache.remove(existingItem);
            cache.add(updatedItem);
            saveToFile();
        }
    }

    /**
     * Видаляє елемент за його ідентифікатором.
     *
     * @param id ідентифікатор елемента.
     */
    @Override
    public void deleteById(int id) {
        cache.removeIf(item -> getId(item) == id);
        saveToFile();
    }

    /**
     * Фільтрує елементи за заданим полем і значенням.
     *
     * @param fieldName ім'я поля для фільтрації.
     * @param value значення для порівняння.
     * @return список елементів, що задовольняють умови фільтрації.
     */
    @Override
    public List<T> filter(String fieldName, Object value) {
        return cache.stream().filter(item -> matchesField(item, fieldName, value))
            .collect(Collectors.toList());
    }

    /**
     * Отримує ідентифікатор елемента.
     * Метод має бути реалізований у підкласах.
     *
     * @param item елемент, для якого потрібно отримати ідентифікатор.
     * @return ідентифікатор елемента.
     */
    protected abstract int getId(T item);

    /**
     * Перевіряє, чи відповідає поле елемента заданому значенню.
     * Метод має бути реалізований у підкласах.
     *
     * @param item елемент, для якого перевіряється поле.
     * @param fieldName ім'я поля для перевірки.
     * @param value значення для порівняння.
     * @return {@code true}, якщо поле відповідає значенню, {@code false} в іншому випадку.
     */
    protected abstract boolean matchesField(T item, String fieldName, Object value);
}
