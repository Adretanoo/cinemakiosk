package com.adrian.cinemakiosk.domain.util;

import com.adrian.cinemakiosk.domain.entitys.impl.User;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    public static void main(String[] args) {
        Faker faker = new Faker();
        List<User> users = new ArrayList<>();

        // Генерація 10 користувачів
        for (int i = 0; i < 10; i++) {
            User user = new User(
                i + 1,
                faker.name().fullName(),
                faker.internet().emailAddress(),
                faker.internet().password(),
                faker.bool().bool() ? "user" : "admin"
            );
            users.add(user);
        }

        // Збереження до JSON
        saveToJsonFile("data/users.json", users);
    }

    private static void saveToJsonFile(String filePath, Object data) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
            System.out.println("Дані збережено до файлу: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

