package com.adrian.cinemakiosk.persistence.repository.impl;

import com.adrian.cinemakiosk.persistence.entity.impl.Ticket;
import com.adrian.cinemakiosk.persistence.entity.impl.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends BaseRepository<User> {

    private final List<User> users;
    private static final String USER_FILE = "data/users.json";
    private Gson gson;



    public UserRepository() throws IOException {
        super(USER_FILE, new TypeToken<List<User>>() {}.getType());
        this.users = load();
        gson = new Gson();
    }

    @Override
    protected int getId(User user) {
        return user.getId();
    }

    @Override
    protected boolean matchesField(User user, String fieldName, Object value) {
        switch (fieldName) {
            case "name":
                return user.getUsername().equals(value);
            case "email":
                return user.getEmail().equals(value);
            default:
                return false;
        }
    }

    public User findByEmail(String email) {
        return users.stream()
            .filter(user -> user.getEmail().equals(email))
            .findFirst()
            .orElse(null);  // Повертає null, якщо користувача не знайдено
    }

    public boolean existsByEmail(String email) {
        return users.stream().anyMatch(user -> user.getEmail().equals(email));
    }

    public void save(User user) {
        // Перевірка наявності користувача
        if (existsByEmail(user.getEmail())) {
            System.out.println("Користувач з такою поштою вже існує.");
            return;
        }

        // Генерація нового ID
        int newId = users.isEmpty() ? 1 : users.stream().mapToInt(User::getId).max().orElse(0) + 1;
        user.setId(newId);
        users.add(user);
        saveToFile();
    }

    private void saveToFile() {
        try (FileWriter writer = new FileWriter(USER_FILE)) {
            Gson gson = new Gson();
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.out.println("Помилка при збереженні користувачів: " + e.getMessage());
        }
    }

    public void updateBalance(String email, double newBalance) {

        users.stream()
            .filter(user -> user.getEmail().equals(email))
            .findFirst()
            .ifPresent(user -> user.setBalance(newBalance)); // Оновлюємо баланс

        saveToFile();

        User user = findByEmail(email);
        if (user != null) {
            user.setBalance(newBalance);
            save(user); // Тут потрібно реалізувати збереження в базу або JSON
        }
    }

    private List<User> load() throws JsonSyntaxException, IOException {
        try (FileReader reader = new FileReader(USER_FILE)) {
            Gson gson = new Gson();
            List<User> users = gson.fromJson(reader, new TypeToken<List<User>>() {}.getType());
            return users != null ? users : new ArrayList<>();
        }
    }
    public User getUserByEmail(String email) {
        // Логіка для пошуку користувача за email (наприклад, з файлу або бази даних)
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;  // Якщо користувача не знайдено
    }

    public void updateUser(User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(updatedUser.getEmail())) {
                users.set(i, updatedUser);  // Оновлюємо користувача в списку
                saveToFile();  // Зберігаємо оновлені дані в файл
                System.out.println("Дані користувача оновлено: " + updatedUser);
                return;
            }
        }
        System.out.println("Користувача з такою електронною поштою не знайдено.");
    }


    public List<Ticket> getUserTickets(String email) {
        User user = findByEmail(email);
        if (user != null) {
            return user.getTickets();
        } else {
            return new ArrayList<>();
        }
    }
}
