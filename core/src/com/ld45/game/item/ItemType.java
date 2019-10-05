package com.ld45.game.item;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.ld45.game.assets.Assets;

public enum ItemType {
    Cookie("cookie.png", "Cookie", "Tasty!")
    ;

    ItemType(String spriteName, String displayName, String description) {
        this.SPRITE = Assets.getInstance().getSprite("entity/food/" + spriteName);
        this.UI_SPRITE = Assets.getInstance().getSprite("ui/food/" + spriteName);
    }

    public final Sprite SPRITE;
    public final Sprite UI_SPRITE;

}
