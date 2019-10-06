package com.ld45.game.item;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.ld45.game.assets.Assets;

public enum ItemType {
    Cookie("cookie.png", "Cookie", "Lots of crumbs!", 3, 3),
    Bacon("bacon.png", "Bacon", "Very Greasy!", 5, 5),
    Eggs("eggs.png", "Eggs", "Non rubbery!", 6, 6),
    Pig("pig.png", "Pig", "... *Oink*", 11, 11)
    ;

    ItemType(String spriteName, String displayName, String description, int damage, int hungerRestoration) {
        this.SPRITE_PATH = ("entity/food/" + spriteName);
        this.SPRITE = Assets.getInstance().getSprite(this.SPRITE_PATH);
        this.UI_SPRITE = Assets.getInstance().getSprite("ui/food/" + spriteName);
        this.DISPLAY_NAME = displayName;
        this.DESCRIPTION = description;
        this.DAMAGE = damage;
        this.HUNGER_RESTORATION = hungerRestoration;
    }

    public final Sprite SPRITE;
    public final String SPRITE_PATH;
    public final Sprite UI_SPRITE;
    public final String DISPLAY_NAME;
    public final String DESCRIPTION;
    public final int DAMAGE;
    public final int HUNGER_RESTORATION;

}
