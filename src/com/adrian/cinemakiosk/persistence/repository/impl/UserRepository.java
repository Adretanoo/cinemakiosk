package com.adrian.cinemakiosk.persistence.repository.impl;


import com.adrian.cinemakiosk.persistence.entity.impl.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends BaseRepository<User> {

    private final List<User> users;
    private FileDescriptor USER_FILE;

    public UserRepository() throws IOException {
        super("data/users.json", new TypeToken<List<User>>() {
        }.getType());
        this.users = load();  // Завантажуємо користувачів при ініціалізації
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

    // Перевірка наявності користувача за іменем
    public boolean existsByUsername(String username) {
        return users.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    // Перевірка наявності користувача за електронною поштою
    public boolean existsByEmail(String email) {
        return users.stream().anyMatch(user -> user.getEmail().equals(email));
    }

    // Генерація ID для нового користувача
    private int generateNewId() {
        if (users.isEmpty()) {
            return 1;  // Якщо список порожній, ID буде 1
        }
        // Якщо список не порожній, беремо найбільший ID і збільшуємо на 1
        return users.stream().mapToInt(User::getId).max().orElse(0) + 1;
    }

    // Збереження користувача
    public void save(User user) {
        try {
            // Перевірка чи вже існує користувач з таким username або email
            if (existsByUsername(user.getUsername())) {
                System.out.println(
                    "Error: User with username '" + user.getUsername() + "' already exists.");
                return;  // Виходимо, якщо користувач з таким ім'ям вже існує
            }

            if (existsByEmail(user.getEmail())) {
                System.out.println(
                    "Error: User with email '" + user.getEmail() + "' already exists.");
                return;  // Виходимо, якщо користувач з таким email вже існує
            }

            // Генерація нового ID для користувача
            int newId = generateNewId();
            user.setId(newId);

            // Якщо користувача немає, додаємо нового
            users.add(user);

            // Перезаписуємо файл з новим списком користувачів
            try (FileWriter writer = new FileWriter(getFilePath())) {
                Gson gson = new Gson();
                gson.toJson(users, writer); // Перетворюємо список користувачів на JSON та записуємо
            } catch (IOException e) {
                System.out.println("Error while saving user to file: " + e.getMessage());
            }
        } catch (JsonSyntaxException e) {
            System.out.println("Error while loading users from file: " + e.getMessage());
        }
    }

    // Завантаження користувачів з файлу
    private List<User> load() throws JsonSyntaxException, IOException {
        FileReader reader = new FileReader(getFilePath());
        Gson gson = new Gson();
        List<User> users = gson.fromJson(reader, new TypeToken<List<User>>() {
        }.getType());
        if (users == null) {
            users = new ArrayList<>();  // Якщо файл порожній, ініціалізуємо порожній список
        }
        return users;
    }

    private String getFilePath() {
        return "data/users.json";  // Шлях до файлу
    }

    public void update(User user) {
        try (FileReader reader = new FileReader(USER_FILE)) {
            Gson gson = new Gson();
            JsonArray users = gson.fromJson(reader, JsonArray.class);

            for (int i = 0; i < users.size(); i++) {
                JsonObject obj = users.get(i).getAsJsonObject();
                if (obj.get("email").getAsString().equals(user.getEmail())) {
                    obj.addProperty("balance", user.getBalance()); // Оновлюємо баланс
                }
            }

            try (FileWriter writer = new FileWriter(USER_FILE)) {
                gson.toJson(users, writer); // Зберігаємо оновлений файл
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
