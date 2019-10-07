package com.ld45.game.entity.enemy.impl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ld45.game.animation.Animation;
import com.ld45.game.animation.DirectionalAnimation;
import com.ld45.game.assets.Assets;
import com.ld45.game.entity.enemy.EnemyEntity;
import com.ld45.game.entity.living.impl.EntityPlayer;
import com.ld45.game.entity.mind.EntityMind;
import com.ld45.game.entity.mind.impl.AttackNearbyMind;
import com.ld45.game.entity.mind.impl.WanderAttackMind;
import com.ld45.game.entity.projectile.impl.EntityFlameProjectile;
import com.ld45.game.map.Map;

public class EntityWorm extends EnemyEntity {

    private Sprite dirt;

    public EntityWorm(Vector2 position, Map parentMap) {
        super(position, parentMap, 0.5f, Color.WHITE, 1);
        this.setSpeed(4, 4);
        this.dirt = Assets.getInstance().getSprite("entity/worm_dirt.png");
        this.setMaxHealth(10);
        this.setHealth(10);
    }

    @Override
    public EntityMind setupMind() {
        return new AttackNearbyMind(this, 1.3f);
    }

    @Override
    public DirectionalAnimation setupAnimation() {
        float animationDuration = 0.18f;

        Animation animation = new Animation(animationDuration);
        animation.addFrames("entity/worm_0.png", "entity/worm_1.png", "entity/worm_2.png", "entity/worm_3.png", "entity/worm_4.png");
        animation.addFrames("entity/worm_5.png", "entity/worm_6.png", "entity/worm_7.png");

        return new DirectionalAnimation(animation, animation, animation, animation);
    }

    @Override
    public DirectionalAnimation setupAttackAnimation() {
        float animationDuration = 0.18f;

        Animation animation = new Animation(animationDuration);
        animation.addFrames("entity/worm_attack_0.png", "entity/worm_attack_1.png", "entity/worm_attack_2.png");

        return new DirectionalAnimation(animation, animation, animation, animation);
    }

    @Override
    public void attackPlayer(EntityPlayer player) {
        this.fireFlame(Color.GREEN, player);
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        this.dirt.setPosition(this.getPosition().x, this.getPosition().y - 4);
        this.dirt.draw(batch);
        super.render(batch, camera);
    }

    @Override
    public void update(OrthographicCamera camera) {
        super.update(camera);
        if(this.isAttacking()) {
            this.getAttackAnimation().update(this.getDirection());
        } else {
            this.getDirectionalAnimation().update(this.getDirection());
        }
    }

    @Override
    public int getDamage() {
        return 6;
    }

}
