package com.ld45.game.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Assets {

    private static final Assets instance = new Assets();

    private AssetManager assetManager = new AssetManager();

    public Assets() {
        this.load();
    }

    public void load() {
        this.loadTexture("tile/tileset.png");

        this.loadTexture("tile/overlay.png");

        this.loadTexture("entity/bat_right_0.png");
        this.loadTexture("entity/bat_right_1.png");
        this.loadTexture("entity/bat_right_2.png");
        this.loadTexture("entity/bat_right_3.png");
        this.loadTexture("entity/bat_right_4.png");
        this.loadTexture("entity/bat_left_0.png");
        this.loadTexture("entity/bat_left_1.png");
        this.loadTexture("entity/bat_left_2.png");
        this.loadTexture("entity/bat_left_3.png");
        this.loadTexture("entity/bat_left_4.png");

        this.loadTexture("entity/imp_right_0.png");
        this.loadTexture("entity/imp_right_1.png");
        this.loadTexture("entity/imp_right_2.png");
        this.loadTexture("entity/imp_right_3.png");
        this.loadTexture("entity/imp_right_4.png");
        this.loadTexture("entity/imp_right_5.png");
        this.loadTexture("entity/imp_right_6.png");

        this.loadTexture("entity/imp_left_0.png");
        this.loadTexture("entity/imp_left_1.png");
        this.loadTexture("entity/imp_left_2.png");
        this.loadTexture("entity/imp_left_3.png");
        this.loadTexture("entity/imp_left_4.png");
        this.loadTexture("entity/imp_left_5.png");
        this.loadTexture("entity/imp_left_6.png");

        this.loadTexture("entity/flame_0.png");
        this.loadTexture("entity/flame_1.png");
        this.loadTexture("entity/flame_2.png");
        this.loadTexture("entity/flame_3.png");
        this.loadTexture("entity/flame_4.png");
        this.loadTexture("entity/flame_5.png");
        this.loadTexture("entity/flame_6.png");

        this.assetManager.finishLoading();
    }

    public void loadTexture(String path) {
        this.assetManager.load(path, Texture.class);
    }

    public Texture getTexture(String path) {
        return this.assetManager.get(path, Texture.class);
    }

    public Sprite getSprite(String path) {
        return new Sprite(this.getTexture(path));
    }

    public static Assets getInstance() {
        return instance;
    }

}