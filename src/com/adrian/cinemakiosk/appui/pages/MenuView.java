package com.adrian.cinemakiosk.appui.pages;

import com.adrian.cinemakiosk.persistence.repository.impl.UserRepository;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;

public class MenuHandler {

    private final Screen screen;
    private final TextGraphics textGraphics;

    public MenuHandler(Screen screen, TextGraphics textGraphics, UserRepository userRepository) {
        this.screen = screen;
        this.textGraphics = textGraphics;
    }

    public void showGreeting() throws IOException {
        clearScreen();
        String[] message = {
            "╔════════════════════════════════════════════════════════════════╗",
            "║                        WELCOME TO CINEMAKIOSK!                  ║",
            "╚════════════════════════════════════════════════════════════════╝"
        };

        int yStart = (screen.getTerminalSize().getRows() - message.length) / 2;

        for (int i = 0; i < message.length; i++) {
            textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFA500"));
            textGraphics.putString(
                (screen.getTerminalSize().getColumns() - message[i].length()) / 2,
                yStart + i,
                message[i]
            );
        }

        String continueMessage = "Press any key to continue...";
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(
            (screen.getTerminalSize().getColumns() - continueMessage.length()) / 2,
            yStart + message.length + 2,
            continueMessage
        );

        screen.refresh();
        screen.readInput();
    }

    public void showMainMenu() throws IOException {
        String[] menuOptions = {"Register", "Login", "Exit"};
        int selectedIndex = 0;

        while (true) {
            clearScreen();

            textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFD700"));
            textGraphics.putString(2, 1, "MAIN MENU");

            for (int i = 0; i < menuOptions.length; i++) {
                if (i == selectedIndex) {
                    highlightOption(menuOptions[i], 2, 3 + i * 2);
                } else {
                    textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
                    textGraphics.putString(2, 3 + i * 2, menuOptions[i]);
                }
            }

            String instructions = "Use ↑/↓ to navigate, Enter to select.";
            textGraphics.setForegroundColor(TextColor.Factory.fromString("#00CED1"));
            textGraphics.putString(2, 3 + menuOptions.length * 2 + 2, instructions);

            screen.refresh();
            KeyStroke keyStroke = screen.readInput();

            switch (keyStroke.getKeyType()) {
                case ArrowDown:
                    selectedIndex = (selectedIndex + 1) % menuOptions.length;
                    break;
                case ArrowUp:
                    selectedIndex = (selectedIndex - 1 + menuOptions.length) % menuOptions.length;
                    break;
                case Enter:
                    if (menuOptions[selectedIndex].equals("Register")) {
                        showRegistrationWindow();
                    } else if (menuOptions[selectedIndex].equals("Login")) {
                        // Implement login functionality
                    } else if (menuOptions[selectedIndex].equals("Exit")) {
                        screen.stopScreen();
                        System.exit(0);
                    }
                    break;
            }
        }
    }

    private void showRegistrationWindow() throws IOException {
        clearScreen();

        String[] fields = {"Username", "Email", "Password"};
        String[] inputs = {"", "", ""};
        int selectedFieldIndex = 0;

        while (true) {
            clearScreen();
            textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFD700"));
            textGraphics.putString(2, 1, "REGISTRATION");

            for (int i = 0; i < fields.length; i++) {
                String fieldText = fields[i] + ": " + inputs[i];
                if (i == selectedFieldIndex) {
                    highlightOption(fieldText, 2, 3 + i * 2);
                } else {
                    textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
                    textGraphics.putString(2, 3 + i * 2, fieldText);
                }
            }

            String instructions = "Use ↑/↓ to switch fields, Enter to confirm, Esc to exit.";
            textGraphics.setForegroundColor(TextColor.Factory.fromString("#00CED1"));
            textGraphics.putString(2, 3 + fields.length * 2 + 2, instructions);

            screen.refresh();

            KeyStroke keyStroke = screen.readInput();
            switch (keyStroke.getKeyType()) {
                case ArrowDown:
                    selectedFieldIndex = (selectedFieldIndex + 1) % fields.length;
                    break;
                case ArrowUp:
                    selectedFieldIndex = (selectedFieldIndex - 1 + fields.length) % fields.length;
                    break;
                case Enter:
                    textGraphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
                    textGraphics.putString(2, 3 + fields.length * 2 + 4,
                        "Registration successful!");
                    screen.refresh();
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                case Escape:
                    return;
                case Backspace:
                    if (inputs[selectedFieldIndex].length() > 0) {
                        inputs[selectedFieldIndex] = inputs[selectedFieldIndex].substring(0,
                            inputs[selectedFieldIndex].length() - 1);
                    }
                    break;
                default:
                    if (keyStroke.getCharacter() != null) {
                        inputs[selectedFieldIndex] += keyStroke.getCharacter();
                    }
                    break;
            }
        }
    }

    private void clearScreen() throws IOException {
        screen.clear();
    }

    private void highlightOption(String option, int x, int y) {
        textGraphics.setBackgroundColor(TextColor.Factory.fromString("#005f87"));
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(x, y, option);
        textGraphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));
    }
}