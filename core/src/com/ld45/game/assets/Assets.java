package com.ld45.game.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

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

        this.loadTexture("entity/imp_attack_right_0.png");
        this.loadTexture("entity/imp_attack_right_1.png");
        this.loadTexture("entity/imp_attack_right_2.png");
        this.loadTexture("entity/imp_attack_right_3.png");

        this.loadTexture("entity/imp_attack_left_0.png");
        this.loadTexture("entity/imp_attack_left_1.png");
        this.loadTexture("entity/imp_attack_left_2.png");
        this.loadTexture("entity/imp_attack_left_3.png");

        this.loadTexture("entity/flame_0.png");
        this.loadTexture("entity/flame_1.png");
        this.loadTexture("entity/flame_2.png");
        this.loadTexture("entity/flame_3.png");
        this.loadTexture("entity/flame_4.png");
        this.loadTexture("entity/flame_5.png");
        this.loadTexture("entity/flame_6.png");

        this.loadTexture("entity/food/cookie.png");

        this.loadTexture("ui/food/cookie.png");

        this.loadSkin("ui/arcade/arcade-ui.json");
        this.loadSkin("ui/clean-crispy/clean-crispy-ui.json");
        this.loadSkin("ui/sgxui/sgx-ui.json");

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

    public void loadSkin(String path) {
        this.assetManager.load(path, Skin.class);
    }

    public Skin getSkin(String path) {
        return this.assetManager.get(path, Skin.class);
    }

    public static Assets getInstance() {
        return instance;
    }

}