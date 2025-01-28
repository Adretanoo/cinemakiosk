package com.adrian.cinemakiosk.appui.pages;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;

public class ScreenSaverView {

    private final Screen screen;
    private final TextGraphics textGraphics;

    public ScreenSaverView(Screen screen, TextGraphics textGraphics) {
        this.screen = screen;
        this.textGraphics = textGraphics;
    }

    public void showGreeting() throws IOException {
        clearScreen();

        String[] message = {
            "╔════════════════════════════════════════════════════════════════╗",
            "║                   ЛАСКАВО ПРОСИМО ДО CINEMAKIOSK!              ║",
            "╚════════════════════════════════════════════════════════════════╝"
        };

        int yStart = (screen.getTerminalSize().getRows() - message.length) / 2;

        for (int i = 0; i < message.length; i++) {
            textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
            textGraphics.putString(
                (screen.getTerminalSize().getColumns() - message[i].length()) / 2,
                yStart + i,
                message[i]
            );
        }

        String continueMessage = "Натисніть будь-яку клавішу, щоб продовжити...";
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        textGraphics.putString(
            (screen.getTerminalSize().getColumns() - continueMessage.length()) / 2,
            yStart + message.length + 2,
            continueMessage
        );

        screen.refresh();
        screen.readInput();
    }

    private void clearScreen() throws IOException {
        screen.clear();
    }
}
