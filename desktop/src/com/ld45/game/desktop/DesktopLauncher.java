package com.ld45.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ld45.game.Game;
import com.ld45.game.util.Screen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Screen.SCREEN_WIDTH;
		config.height = Screen.SCREEN_HEIGHT;
		new LwjglApplication(new Game(), config);
	}
}
