package com.adrian.cinemakiosk;

import com.adrian.cinemakiosk.domain.entitys.impl.User;
import com.adrian.cinemakiosk.domain.validators.UserValidator;

public class Main {

//    public static void main(String[] args) {
//        Faker faker = new Faker();
//        List<User> users = new ArrayList<>();
//
//        for (int i = 0; i < 10; i++) {
//            User user = new User(
//                i + 1,
//                faker.name().fullName(),
//                faker.internet().emailAddress(),
//                faker.internet().password(),
//                faker.bool().bool() ? "user" : "admin"
//            );
//            users.add(user);
//
//            System.out.println(user);
//        }
//        saveToJsonFile("data/users.json", users);
//    }
//
//    private static void saveToJsonFile(String filePath, Object data) {
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        try (FileWriter writer = new FileWriter(filePath)) {
//            gson.toJson(data, writer);
//            System.out.println("Дані збережено до файлу: " + filePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    public static void main(String[] args) {
        User user = new User(1, "", "", "", "");
        UserValidator.validate(user);
    }

}
