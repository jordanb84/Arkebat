package com.ld45.game.entity.projectile.impl;

import com.badlogic.gdx.math.Vector2;
import com.ld45.game.animation.Animation;
import com.ld45.game.entity.projectile.EntityProjectile;
import com.ld45.game.map.Map;

public class EntityFlame extends EntityProjectile {

    public EntityFlame(Vector2 position, Map parentMap, Vector2 destination) {
        super(position, parentMap, destination, 12);
    }

    @Override
    public Animation setupAnimation() {
        Animation animation = new Animation(0.16f);

        animation.addFrames("entity/flame_0.png", "entity/flame_1.png", "entity/flame_2.png", "entity/flame_3.png");
        animation.addFrames("entity/flame_4.png", "entity/flame_5.png", "entity/flame_6.png");

        return animation;
    }

}
