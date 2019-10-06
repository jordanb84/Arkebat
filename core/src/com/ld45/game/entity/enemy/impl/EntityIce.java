package com.ld45.game.entity.enemy.impl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ld45.game.animation.Animation;
import com.ld45.game.animation.DirectionalAnimation;
import com.ld45.game.entity.enemy.EnemyEntity;
import com.ld45.game.entity.living.impl.EntityPlayer;
import com.ld45.game.entity.mind.EntityMind;
import com.ld45.game.entity.mind.impl.WanderAttackMind;
import com.ld45.game.entity.projectile.impl.EntityFlameProjectile;
import com.ld45.game.map.Map;

public class EntityIce extends EnemyEntity {

    public EntityIce(Vector2 position, Map parentMap) {
        super(position, parentMap, 0.5f, Color.ORANGE, 1); //orange or yellow
        this.setSpeed(4, 4);
        this.setHealth(90);
    }

    @Override
    public EntityMind setupMind() {
        return new WanderAttackMind(this);
    }

    @Override
    public DirectionalAnimation setupAnimation() {
        float animationDuration = 0.25f;

        Animation rightAnimation = new Animation(animationDuration);
        rightAnimation.addFrames("entity/ice_right_0.png", "entity/ice_right_1.png", "entity/ice_right_2.png", "entity/ice_right_3.png");

        Animation leftAnimation = new Animation(animationDuration);
        leftAnimation.addFrames("entity/ice_left_0.png", "entity/ice_left_1.png", "entity/ice_left_2.png", "entity/ice_left_3.png");

        return new DirectionalAnimation(rightAnimation, leftAnimation, rightAnimation, leftAnimation);
    }

    @Override
    public DirectionalAnimation setupAttackAnimation() {
        float animationDuration = 0.1f;

        Animation rightAnimation = new Animation(animationDuration);
        rightAnimation.addFrames("entity/ice_hop_right_0.png", "entity/ice_hop_right_1.png", "entity/ice_hop_right_2.png", "entity/ice_hop_right_3.png");
        rightAnimation.addFrames("entity/ice_hop_right_4.png", "entity/ice_hop_right_5.png", "entity/ice_hop_right_6.png", "entity/ice_hop_right_7.png");
        rightAnimation.addFrame("entity/ice_hop_right_8.png");

        //rightAnimation.addFrames("entity/ice_spin_right_0.png", "entity/ice_spin_right_1.png", "entity/ice_spin_right_2.png");
        //rightAnimation.addFrames("entity/ice_spin_right_3.png", "entity/ice_spin_right_4.png", "entity/ice_spin_right_5.png");

        Animation leftAnimation = new Animation(animationDuration);
        leftAnimation.addFrames("entity/ice_hop_left_0.png", "entity/ice_hop_left_1.png", "entity/ice_hop_left_2.png", "entity/ice_hop_left_3.png");
        leftAnimation.addFrames("entity/ice_hop_left_4.png", "entity/ice_hop_left_5.png", "entity/ice_hop_left_6.png", "entity/ice_hop_left_7.png");
        leftAnimation.addFrame("entity/ice_hop_left_8.png");
        //leftAnimation.addFrames("entity/ice_spin_left_0.png", "entity/ice_spin_left_1.png", "entity/ice_spin_left_2.png");
        //leftAnimation.addFrames("entity/ice_spin_left_3.png", "entity/ice_spin_left_4.png", "entity/ice_spin_left_5.png");

        return new DirectionalAnimation(rightAnimation, leftAnimation, rightAnimation, leftAnimation);
    }

    @Override
    public void attackPlayer(EntityPlayer player) {
        this.fireFlame(Color.CHARTREUSE, player); //TODO maybe cyan or something
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        super.render(batch, camera);
    }
}
