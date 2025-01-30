package com.adrian.cinemakiosk.appui.pages;

import com.adrian.cinemakiosk.appui.MenuInputHandler;
import com.adrian.cinemakiosk.appui.componens.MenuRenderer;
import com.adrian.cinemakiosk.persistence.repository.impl.UserRepository;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;

/**
 * Клас, що відповідає за відображення основного меню програми та обробку вибору користувача.
 * Меню включає варіанти для реєстрації, входу, перегляду інформації про програму та виходу.
 */
public class MenuView {

    private final Screen screen;
    private final TextGraphics textGraphics;
    private final MenuRenderer menuRenderer;
    private final MenuInputHandler inputHandler;

    /**
     * Конструктор для ініціалізації MenuView з необхідними залежностями.
     *
     * @param screen екран для відображення вмісту.
     * @param textGraphics об'єкт для малювання тексту на екрані.
     */
    public MenuView(Screen screen, TextGraphics textGraphics) {
        this.screen = screen;
        this.textGraphics = textGraphics;
        this.menuRenderer = new MenuRenderer(textGraphics, screen);
        this.inputHandler = new MenuInputHandler(4); // 4 варіанти в меню
    }

    /**
     * Відображає основне меню з варіантами: реєстрація, вхід, інформація про програму, вихід.
     * Обробляє введення користувача для вибору опцій.
     *
     * @throws IOException якщо виникне помилка при відображенні екрану або читанні введених даних.
     */
    public void showMainMenu() throws IOException {
        String[] menuOptions = {"Реєстрація", "Вхід", "Про програму", "Вихід"};

        while (true) {
            menuRenderer.renderMenu(menuOptions, inputHandler.getSelectedIndex());
            KeyStroke keyStroke = screen.readInput();
            int selectedIndex = inputHandler.handleInput(keyStroke);

            if (selectedIndex != -1) {
                handleMenuSelection(menuOptions[selectedIndex]);
            }
        }
    }

    /**
     * Обробляє вибір користувача в основному меню і викликає відповідні дії.
     *
     * @param selectedOption вибрана опція в меню.
     * @throws IOException якщо виникне помилка при виклику відповідних методів.
     */
    private void handleMenuSelection(String selectedOption) throws IOException {
        switch (selectedOption) {
            case "Реєстрація":
                UserRepository userRepositoryRegistration = new UserRepository();
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
                new AboutUsView(screen, textGraphics).showAboutUs();
                break;
        }
    }
}
