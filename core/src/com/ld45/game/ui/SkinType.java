package com.ld45.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.ld45.game.assets.Assets;

public enum  SkinType {
    Arcade("ui/arcade/arcade-ui.json"), Clean_Crispy("ui/clean-crispy/clean-crispy-ui.json"),
    Sgx("ui/sgxui/sgx-ui.json")
    ;

    SkinType(String skinPath) {
        this.SKIN = Assets.getInstance().getSkin(skinPath);
    }

    public Skin SKIN;


}
