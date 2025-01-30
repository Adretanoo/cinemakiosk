package com.adrian.cinemakiosk.domain.util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FileManager<T> {
    private final String filePath;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Type typeToken;

    public FileManager(String filePath, Type typeToken) {
        this.filePath = filePath;
        this.typeToken = typeToken;
        createFileIfNotExists();
    }

    private void createFileIfNotExists() {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                writeToFile(new HashMap<String, T>());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, T> readFromFile() {
        try {
            String json = new String(Files.readAllBytes(Paths.get(filePath)));
            return gson.fromJson(json, typeToken);
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public void writeToFile(Map<String, T> data) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
