package com.adrian.cinemakiosk.domain.session;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SessionManager {

    private static final String BASE_DIRECTORY = "users";

    public String checkActiveSession() {
        try {
            File userDir = new File(BASE_DIRECTORY);
            if (userDir.exists()) {
                for (File userFolder : userDir.listFiles()) {
                    File sessionFile = new File(userFolder, "session.txt");
                    if (sessionFile.exists()) {
                        return Files.readString(sessionFile.toPath()).trim();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createSession(String username) {
        try {
            File userFolder = new File(BASE_DIRECTORY, "user_" + username);
            if (!userFolder.exists()) {
                userFolder.mkdirs();
            }
            Files.writeString(Paths.get(userFolder.getPath(), "session.txt"), username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteSession(String username) {
        try {
            File sessionFile = new File(BASE_DIRECTORY, "user_" + username + "/session.txt");
            if (sessionFile.exists()) {
                sessionFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

