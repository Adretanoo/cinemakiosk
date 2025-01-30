package com.adrian.cinemakiosk;

import com.adrian.cinemakiosk.appui.pages.MenuView;
import com.adrian.cinemakiosk.appui.pages.ScreenSaverView;
import com.adrian.cinemakiosk.persistence.repository.impl.UserRepository;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

/**
 * Головний клас програми, який відповідає за ініціалізацію та запуск екрану,
 * показ привітання та меню.
 */
public class Main {

    /**
     * Головний метод програми.
     * Створює екран, ініціалізує репозиторій користувачів, показує привітання
     * та основне меню.
     *
     * @param args аргументи командного рядка (не використовуються).
     */
    public static void main(String[] args) {
        try {
            // Створення екрану
            Screen screen = new DefaultTerminalFactory().createScreen();
            screen.startScreen();
            var textGraphics = screen.newTextGraphics();

            // Ініціалізація репозиторію користувачів
            UserRepository userRepository = new UserRepository();

            // Ініціалізація екрану заставки
            ScreenSaverView screenSaverView = new ScreenSaverView(screen, textGraphics);

            // Показ привітання
            screenSaverView.showGreeting();

            // Ініціалізація та показ основного меню
            MenuView menuHandler = new MenuView(screen, textGraphics);
            menuHandler.showMainMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
