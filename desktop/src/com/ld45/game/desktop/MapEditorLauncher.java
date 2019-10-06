package com.ld45.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ld45.game.desktop.editor.MapEditor;
import com.ld45.game.util.Screen;

public class MapEditorLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.backgroundFPS = 60;
		config.foregroundFPS = 60;
		config.width = Screen.SCREEN_WIDTH;
		config.height = Screen.SCREEN_HEIGHT;
		new LwjglApplication(new MapEditor(), config);
	}
}
