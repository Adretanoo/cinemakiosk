package com.adrian.cinemakiosk.appui.pages;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;

/**
 * Клас, що відповідає за відображення сторінки "Про програму" в інтерфейсі кіоску.
 * Цей клас надає інформацію про призначення програми та її функціональність.
 */
public class AboutUsView {

    private final Screen screen;
    private final TextGraphics textGraphics;

    /**
     * Конструктор для ініціалізації AboutUsView з об'єктами Screen та TextGraphics.
     *
     * @param screen екран для відображення вмісту.
     * @param textGraphics об'єкт для малювання тексту на екрані.
     */
    public AboutUsView(Screen screen, TextGraphics textGraphics) {
        this.screen = screen;
        this.textGraphics = textGraphics;
    }

    /**
     * Відображає інформацію про програму та її призначення.
     * Після відображення екрану користувач може повернутися в головне меню.
     *
     * @throws IOException якщо виникне помилка при відображенні екрану або читанні введених даних.
     */
    public void showAboutUs() throws IOException {
        screen.clear();
        drawAboutUsFrame();

        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));

        textGraphics.putString(2, 6,
            "Це кіоск самообслуговування для оформлення замовлень у кінотеатрі.");
        textGraphics.putString(2, 7, "Програма дозволяє користувачам вибирати фільми,");
        textGraphics.putString(2, 8, "вибирати місця в залі та оформлювати квитки на сеанси.");
        textGraphics.putString(2, 9, "Користувач може легко додавати квитки до кошика");
        textGraphics.putString(2, 10, "та завершувати замовлення, не звертаючись до каси.");
        textGraphics.putString(2, 11, "Програма створена для полегшення процесу покупки квитків");
        textGraphics.putString(2, 12,
            "та зручного вибору фільмів через інтерфейс самообслуговування.");

        String instructions = "Використовуйте довільну клавішу для повернення в меню.";
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        textGraphics.putString(1, 14, instructions);

        screen.refresh();
        screen.readInput();
    }

    /**
     * Малює рамку для сторінки "Про програму" з титульним рядком.
     */
    private void drawAboutUsFrame() {
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(0, 0, "┌──────────────────────────┐");
        textGraphics.putString(0, 1, "│                          │");
        textGraphics.putString(0, 2, "│      ПРО ПРОГРАМУ        │");
        textGraphics.putString(0, 3, "│                          │");
        textGraphics.putString(0, 4, "└──────────────────────────┘");
    }
}
