package com.adrian.cinemakiosk;

import com.adrian.cinemakiosk.appui.pages.MenuView;
import com.adrian.cinemakiosk.appui.pages.ScreenSaverView;
import com.adrian.cinemakiosk.persistence.repository.impl.UserRepository;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

public class Main {

    public static void main(String[] args) {
        try {
            Screen screen = new DefaultTerminalFactory().createScreen();
            screen.startScreen();
            var textGraphics = screen.newTextGraphics();
            UserRepository userRepository = new UserRepository();
            ScreenSaverView screenSaverView = new ScreenSaverView(screen, textGraphics);

            screenSaverView.showGreeting();
            MenuView menuHandler = new MenuView(screen, textGraphics, userRepository);
            menuHandler.showMainMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}


