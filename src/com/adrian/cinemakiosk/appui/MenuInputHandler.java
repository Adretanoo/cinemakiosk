package com.adrian.cinemakiosk.appui;

import com.googlecode.lanterna.input.KeyStroke;

public class MenuInputHandler {

    private final int menuLength;
    private int selectedIndex = 0;

    public MenuInputHandler(int menuLength) {
        this.menuLength = menuLength;
    }

    public int handleInput(KeyStroke keyStroke) {
        switch (keyStroke.getKeyType()) {
            case ArrowDown:
                selectedIndex = (selectedIndex + 1) % menuLength;
                break;
            case ArrowUp:
                selectedIndex = (selectedIndex - 1 + menuLength) % menuLength;
                break;
            case Enter:
                return selectedIndex;
        }
        return -1;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }
}

