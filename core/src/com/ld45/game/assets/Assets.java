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

        this.loadTexture("entity/worm_0.png");
        this.loadTexture("entity/worm_1.png");
        this.loadTexture("entity/worm_2.png");
        this.loadTexture("entity/worm_3.png");
        this.loadTexture("entity/worm_4.png");
        this.loadTexture("entity/worm_5.png");
        this.loadTexture("entity/worm_6.png");
        this.loadTexture("entity/worm_7.png");
        this.loadTexture("entity/worm_dirt.png");
        this.loadTexture("entity/worm_attack_0.png");
        this.loadTexture("entity/worm_attack_1.png");
        this.loadTexture("entity/worm_attack_2.png");
        this.loadTexture("entity/worm_attack_3.png");
        this.loadTexture("entity/worm_attack_4.png");
        this.loadTexture("entity/worm_attack_5.png");

        this.loadTexture("entity/ice_right_0.png");
        this.loadTexture("entity/ice_right_1.png");
        this.loadTexture("entity/ice_right_2.png");
        this.loadTexture("entity/ice_right_3.png");
        this.loadTexture("entity/ice_left_0.png");
        this.loadTexture("entity/ice_left_1.png");
        this.loadTexture("entity/ice_left_2.png");
        this.loadTexture("entity/ice_left_3.png");

        this.loadTexture("entity/ice_spin_right_0.png");
        this.loadTexture("entity/ice_spin_right_1.png");
        this.loadTexture("entity/ice_spin_right_2.png");
        this.loadTexture("entity/ice_spin_right_3.png");
        this.loadTexture("entity/ice_spin_right_4.png");
        this.loadTexture("entity/ice_spin_right_5.png");
        this.loadTexture("entity/ice_spin_left_0.png");
        this.loadTexture("entity/ice_spin_left_1.png");
        this.loadTexture("entity/ice_spin_left_2.png");
        this.loadTexture("entity/ice_spin_left_3.png");
        this.loadTexture("entity/ice_spin_left_4.png");
        this.loadTexture("entity/ice_spin_left_5.png");

        this.loadTexture("entity/ice_hop_right_0.png");
        this.loadTexture("entity/ice_hop_right_1.png");
        this.loadTexture("entity/ice_hop_right_2.png");
        this.loadTexture("entity/ice_hop_right_3.png");
        this.loadTexture("entity/ice_hop_right_4.png");
        this.loadTexture("entity/ice_hop_right_5.png");
        this.loadTexture("entity/ice_hop_right_6.png");
        this.loadTexture("entity/ice_hop_right_7.png");
        this.loadTexture("entity/ice_hop_right_8.png");
        this.loadTexture("entity/ice_hop_left_0.png");
        this.loadTexture("entity/ice_hop_left_1.png");
        this.loadTexture("entity/ice_hop_left_2.png");
        this.loadTexture("entity/ice_hop_left_3.png");
        this.loadTexture("entity/ice_hop_left_4.png");
        this.loadTexture("entity/ice_hop_left_5.png");
        this.loadTexture("entity/ice_hop_left_6.png");
        this.loadTexture("entity/ice_hop_left_7.png");
        this.loadTexture("entity/ice_hop_left_8.png");

        this.loadTexture("entity/flame_0.png");
        this.loadTexture("entity/flame_1.png");
        this.loadTexture("entity/flame_2.png");
        this.loadTexture("entity/flame_3.png");
        this.loadTexture("entity/flame_4.png");
        this.loadTexture("entity/flame_5.png");
        this.loadTexture("entity/flame_6.png");

        this.loadTexture("entity/food/cookie.png");
        this.loadTexture("entity/food/bacon.png");
        this.loadTexture("entity/food/eggs.png");
        this.loadTexture("entity/food/pig.png");
        this.loadTexture("entity/food/beer.png");
        this.loadTexture("entity/food/jam.png");
        this.loadTexture("entity/food/pretzel.png");
        this.loadTexture("entity/food/apple_worm.png");
        this.loadTexture("entity/food/cantaloupe.png");
        this.loadTexture("entity/food/pie.png");
        this.loadTexture("entity/food/fish.png");
        this.loadTexture("entity/food/syrup.png");
        this.loadTexture("entity/food/pineapple.png");
        this.loadTexture("entity/food/pickle.png");
        this.loadTexture("entity/food/sausage.png");
        this.loadTexture("entity/food/steak.png");

        this.loadTexture("ui/food/cookie.png");
        this.loadTexture("ui/food/bacon.png");
        this.loadTexture("ui/food/eggs.png");
        this.loadTexture("ui/food/pig.png");
        this.loadTexture("ui/food/beer.png");
        this.loadTexture("ui/food/jam.png");
        this.loadTexture("ui/food/pretzel.png");
        this.loadTexture("ui/food/apple_worm.png");
        this.loadTexture("ui/food/cantaloupe.png");
        this.loadTexture("ui/food/pie.png");
        this.loadTexture("ui/food/fish.png");
        this.loadTexture("ui/food/syrup.png");
        this.loadTexture("ui/food/pineapple.png");
        this.loadTexture("ui/food/pickle.png");
        this.loadTexture("ui/food/sausage.png");
        this.loadTexture("ui/food/steak.png");

        this.loadTexture("entity/heal.png");

        this.loadTexture("ui/menu.png");

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