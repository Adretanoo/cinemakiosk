package com.adrian.cinemakiosk.appui.pages;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;

public class AboutUsView {

    private final Screen screen;
    private final TextGraphics textGraphics;

    public AboutUsView(Screen screen, TextGraphics textGraphics) {
        this.screen = screen;
        this.textGraphics = textGraphics;
    }

    public void showAboutUs() throws IOException {
        screen.clear(); // Очищаємо екран перед відображенням нового контенту
        drawAboutUsFrame();

        // Встановлюємо білий колір для основного тексту
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));

        // Текст інформації про програму
        textGraphics.putString(2, 6,
            "Це кіоск самообслуговування для оформлення замовлень у кінотеатрі.");
        textGraphics.putString(2, 7, "Програма дозволяє користувачам вибирати фільми,");
        textGraphics.putString(2, 8, "вибирати місця в залі та оформлювати квитки на сеанси.");
        textGraphics.putString(2, 9, "Користувач може легко додавати квитки до кошика");
        textGraphics.putString(2, 10, "та завершувати замовлення, не звертаючись до каси.");
        textGraphics.putString(2, 11, "Програма створена для полегшення процесу покупки квитків");
        textGraphics.putString(2, 12,
            "та зручного вибору фільмів через інтерфейс самообслуговування.");

        // Інструкції для навігації
        String instructions = "Використовуйте довільну клавішу для повернення в меню.";
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        textGraphics.putString(1, 14, instructions); // Інструкції для навігації

        screen.refresh(); // Оновлюємо екран
        screen.readInput(); // Очікуємо натискання клавіші для повернення
    }


    private void drawAboutUsFrame() {
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(0, 0, "┌──────────────────────────┐");
        textGraphics.putString(0, 1, "│                          │");
        textGraphics.putString(0, 2, "│      ПРО ПРОГРАМУ        │");
        textGraphics.putString(0, 3, "│                          │");
        textGraphics.putString(0, 4, "└──────────────────────────┘");
    }
}
