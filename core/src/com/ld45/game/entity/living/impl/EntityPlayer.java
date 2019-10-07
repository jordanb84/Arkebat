package com.ld45.game.entity.living.impl;

import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.ld45.game.animation.Animation;
import com.ld45.game.animation.DirectionalAnimation;
import com.ld45.game.entity.living.LivingEntity;
import com.ld45.game.entity.mind.EntityMind;
import com.ld45.game.entity.mind.impl.PlayerMind;
import com.ld45.game.inventory.Inventory;
import com.ld45.game.map.Map;

public class EntityPlayer extends LivingEntity {

    private Inventory inventory;

    public EntityPlayer(Vector2 position, Map parentMap) {
        super(position, parentMap, 0.5f);
        this.setSpeed(9, 9);
        this.inventory = new Inventory(this);
        this.setHasShadow(true);
        this.setMaxHealth(3);
        this.setHealth(3);

        this.setLight(new PointLight(parentMap.getRayHandler(), parentMap.getLightRayCount(), Color.WHITE, 60, this.getPosition().x, this.getPosition().y));
    }

    @Override
    public EntityMind setupMind() {
        return new PlayerMind(this);
    }

    @Override
    public DirectionalAnimation setupAnimation() {
        float animationDuration = 0.2f;

        Animation rightAnimation = new Animation(animationDuration);
        rightAnimation.addFrames("entity/bat_right_0.png", "entity/bat_right_1.png", "entity/bat_right_2.png", "entity/bat_right_3.png", "entity/bat_right_4.png");

        Animation leftAnimation = new Animation(animationDuration);
        leftAnimation.addFrames("entity/bat_left_0.png", "entity/bat_left_1.png", "entity/bat_left_2.png", "entity/bat_left_3.png", "entity/bat_left_4.png");

        return new DirectionalAnimation(rightAnimation, leftAnimation, rightAnimation, leftAnimation);
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public void update(OrthographicCamera camera) {
        super.update(camera);
        this.inventory.update(camera);
        this.updateLightPosition();

        this.flickerLight(22, 70, 40);
    }

    @Override
    public void onDeath() {
        super.onDeath();
        this.getParentMap().die();
    }

}
