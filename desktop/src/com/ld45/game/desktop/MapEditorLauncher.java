package com.ld45.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ld45.game.desktop.editor.MapEditor;

public class MapEditorLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.backgroundFPS = 60;
		config.foregroundFPS = 60;
		config.width = 1680;
		config.height = 945;
		new LwjglApplication(new MapEditor(), config);
	}
}
