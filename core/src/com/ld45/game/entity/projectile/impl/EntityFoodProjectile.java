package com.ld45.game.entity.projectile.impl;

import com.badlogic.gdx.math.Vector2;
import com.ld45.game.animation.Animation;
import com.ld45.game.entity.projectile.EntityProjectile;
import com.ld45.game.item.ItemType;
import com.ld45.game.map.Map;

public class EntityFoodProjectile extends EntityProjectile {

    public EntityFoodProjectile(ItemType item, Vector2 position, Map parentMap, Vector2 destination, boolean attacksPlayer) {
        super(position, parentMap, destination, 14, attacksPlayer, item.DAMAGE);
        Animation animation = new Animation(1);
        animation.addFrame(item.SPRITE_PATH);

        this.setAnimation(animation);

        this.setMaxLifespan(0.5f);
    }

    @Override
    public Animation setupAnimation() {
        return null;
    }

}
