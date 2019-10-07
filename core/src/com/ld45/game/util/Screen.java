package com.ld45.game.util;

public class Screen {

    public static final int SCREEN_WIDTH = 1366;
    public static final int SCREEN_HEIGHT = 768;

    public static float centerX(float sourceWidth) {
        return SCREEN_WIDTH / 2 - sourceWidth / 2;
    }

    public static float centerY(float sourceHeight) {
        return SCREEN_HEIGHT / 2 - sourceHeight / 2;
    }

}
