package com.adrian.cinemakiosk.appui;

import com.googlecode.lanterna.input.KeyStroke;

/**
 * Клас, що обробляє введення з клавіатури для навігації по меню.
 * Користувач може використовувати стрілки для переміщення по елементах меню та Enter для вибору.
 */
public class MenuInputHandler {

    private final int menuLength;
    private int selectedIndex = 0;

    /**
     * Конструктор, що ініціалізує обробник вводу з вказаною кількістю елементів меню.
     *
     * @param menuLength кількість елементів меню.
     */
    public MenuInputHandler(int menuLength) {
        this.menuLength = menuLength;
    }

    /**
     * Обробляє введені клавіші для навігації по меню.
     * Користувач може переміщатися вгору і вниз по меню та вибрати елемент за допомогою клавіші Enter.
     *
     * @param keyStroke клавіша, натиснута користувачем.
     * @return індекс вибраного елемента меню або -1, якщо вибір ще не був зроблений.
     */
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

    /**
     * Повертає індекс вибраного елемента меню.
     *
     * @return індекс вибраного елемента меню.
     */
    public int getSelectedIndex() {
        return selectedIndex;
    }
}
