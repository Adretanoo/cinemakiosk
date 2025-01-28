package com.adrian.cinemakiosk.appui.pages;

import com.adrian.cinemakiosk.persistence.repository.impl.UserRepository;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;

public class MenuView {

    private final Screen screen;
    private final TextGraphics textGraphics;

    public MenuView(Screen screen, TextGraphics textGraphics, UserRepository userRepository) {
        this.screen = screen;
        this.textGraphics = textGraphics;
    }

    public void showMainMenu() throws IOException {
        String[] menuOptions = {"Реєстрація", "Вхід", "Про програму", "Вихід"};
        int selectedIndex = 0;

        while (true) {
            clearScreen();
            drawMenuFrame();

            for (int i = 0; i < menuOptions.length; i++) {
                if (i == selectedIndex) {
                    highlightOption(menuOptions[i], 2, 7 + i); // Зміщення пунктів меню трохи нижче
                } else {
                    textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
                    textGraphics.putString(4, 7 + i, menuOptions[i]); // Теж зрушуємо вниз
                }
            }

            String instructions = "Використовуйте ↑/↓ для навігації, Enter для вибору.";
            textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
            textGraphics.putString(1, 6 + menuOptions.length + 5,
                instructions);

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
                    handleMenuSelection(menuOptions[selectedIndex]);
                    break;
            }
        }
    }

    private void highlightOption(String option, int x, int y) {
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        textGraphics.putString(x, y, "❯ ");
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(x + 2, y, option);
    }

    private void clearScreen() throws IOException {
        screen.clear();
    }

    private void drawMenuFrame() {
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(0, 0, "┌──────────────────────────┐");
        textGraphics.putString(0, 1, "│                          │");
        textGraphics.putString(0, 2, "│      ГОЛОВНЕ МЕНЮ        │");
        textGraphics.putString(0, 3, "│                          │");
        textGraphics.putString(0, 4, "└──────────────────────────┘");

        textGraphics.putString(0, 5, "┌──────────────────────────┐");
        textGraphics.putString(0, 6, "│                          │");
        textGraphics.putString(0, 7, "│                          │");
        textGraphics.putString(0, 8, "│                          │");
        textGraphics.putString(0, 9, "│                          │");
        textGraphics.putString(0, 10, "│                          │");
        textGraphics.putString(0, 11, "│                          │");
        textGraphics.putString(0, 12, "│                          │");
        textGraphics.putString(0, 13, "└──────────────────────────┘");
    }

    private void handleMenuSelection(String selectedOption) throws IOException {
        switch (selectedOption) {
            case "Реєстрація":
                // Переконайтесь, що userRepository ініціалізовано
                UserRepository userRepositoryRegistration = new UserRepository(); // або отримати через залежність
                RegistrationView registrationView = new RegistrationView(screen, textGraphics,
                    userRepositoryRegistration);
                registrationView.showRegistrationForm();
                break;

            case "Вхід":
                UserRepository userRepositorySign = new UserRepository();
                SignUpView signUpView = new SignUpView(screen, textGraphics, userRepositorySign);
                signUpView.showLoginForm();

                break;
            case "Вихід":
                screen.stopScreen();
                System.exit(0);
                break;
            case "Про програму":
                new AboutUsView(screen,
                    textGraphics).showAboutUs(); // Відображаємо вікно "Про програму"
                break;
        }
    }


}
