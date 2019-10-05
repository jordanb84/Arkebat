package com.ld45.game.entity.enemy.impl;

import com.badlogic.gdx.math.Vector2;
import com.ld45.game.animation.Animation;
import com.ld45.game.animation.DirectionalAnimation;
import com.ld45.game.entity.enemy.EnemyEntity;
import com.ld45.game.entity.mind.EntityMind;
import com.ld45.game.entity.mind.impl.WanderAttackMind;
import com.ld45.game.map.Map;

public class EntityImp extends EnemyEntity {

    public EntityImp(Vector2 position, Map parentMap) {
        super(position, parentMap, 0.5f);
        this.setSpeed(4, 4);
    }

    @Override
    public EntityMind setupMind() {
        return new WanderAttackMind(this);
    }

    @Override
    public DirectionalAnimation setupAnimation() {
        float animationDuration = 0.25f;

        Animation rightAnimation = new Animation(animationDuration);
        rightAnimation.addFrames("entity/imp_right_0.png", "entity/imp_right_1.png", "entity/imp_right_2.png", "entity/imp_right_3.png");
        rightAnimation.addFrames("entity/imp_right_4.png", "entity/imp_right_5.png", "entity/imp_right_6.png");

        Animation leftAnimation = new Animation(animationDuration);
        leftAnimation.addFrames("entity/imp_left_0.png", "entity/imp_left_1.png", "entity/imp_left_2.png", "entity/imp_left_3.png");
        leftAnimation.addFrames("entity/imp_left_4.png", "entity/imp_left_5.png", "entity/imp_left_6.png");

        return new DirectionalAnimation(rightAnimation, leftAnimation, rightAnimation, leftAnimation);
    }

}