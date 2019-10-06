package com.ld45.game.item;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.ld45.game.assets.Assets;

public enum ItemType {
    Cookie("cookie.png", "Cookie", "Lots of crumbs!", 5, 5),
    Bacon("bacon.png", "Bacon", "Very Greasy!", 8, 6),
    Eggs("eggs.png", "Eggs", "Non rubbery!", 6, 6),
    Jam("jam.png", "Jam", "Sticky!", 4, 3),
    Pretzel("pretzel.png", "Salted Pretzel", "...Someone overcooked this", 5, 3),
    Beer("beer.png", "Beer", "Is this really a good time for beer?", 10, 2),
    Pig("pig.png", "Pig", "... *Oink*", 13, 11),
    Apple_Worm("apple_worm.png", "Wormy Apply", "", 2, 1),
    Cantaloupe("cantaloupe.png", "Cantaloupe", "Juicy! Don't eat too many seeds!", 7, 8),
    Fish("fish.png", "Fish", "Smelly... but nutritious?", 3, 4),
    Pie("pie.png", "Pie", "Time for dessert!", 16, 6),
    Syrup("syrup.png", "Maple Syrup", "Eh?", 10, 8)
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
