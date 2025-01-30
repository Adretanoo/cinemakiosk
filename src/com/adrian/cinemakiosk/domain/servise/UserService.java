package com.adrian.cinemakiosk.domain.servise;
import com.adrian.cinemakiosk.domain.util.FileManager;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.util.Map;

public class UserService {
    private final FileManager<String> fileManager;

    public UserService() {
        Type type = new TypeToken<Map<String, String>>() {}.getType();
        this.fileManager = new FileManager<>("data/users.json", type);
    }

    public void addUser(String userId, String userName) {
        Map<String, String> users = fileManager.readFromFile();
        users.put(userId, userName);
        fileManager.writeToFile(users);
    }

    public String getUser(String userId) {
        Map<String, String> users = fileManager.readFromFile();
        return users.getOrDefault(userId, "User not found");
    }

    public void removeUser(String userId) {
        Map<String, String> users = fileManager.readFromFile();
        users.remove(userId);
        fileManager.writeToFile(users);
    }
}
