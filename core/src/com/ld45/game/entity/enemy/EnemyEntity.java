package com.ld45.game.entity.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ld45.game.animation.DirectionalAnimation;
import com.ld45.game.entity.living.LivingEntity;
import com.ld45.game.entity.living.impl.EntityPlayer;
import com.ld45.game.map.Map;

public abstract class EnemyEntity extends LivingEntity {

    private DirectionalAnimation attackAnimation;

    private boolean attacking;

    public EnemyEntity(Vector2 position, Map parentMap, float weight) {
        super(position, parentMap, weight);
        this.attackAnimation = this.setupAttackAnimation();
    }

    public abstract void attackPlayer(EntityPlayer player);

    public abstract DirectionalAnimation setupAttackAnimation();

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        if(this.attacking) {
            this.attackAnimation.getActiveSprite(this.getDirection()).setColor(Color.RED);
            this.attackAnimation.render(batch, this.getPosition(), this.getDirection());
            this.attackAnimation.getActiveSprite(this.getDirection()).setColor(Color.WHITE);

            this.renderDamage(batch, this.attackAnimation.getActiveSprite(this.getDirection()));
        } else {
            this.getDirectionalAnimation().render(batch, this.getPosition(), this.getDirection());

            this.renderDamage(batch, this.getDirectionalAnimation().getActiveSprite(this.getDirection()));
        }

    }

    @Override
    public void update(OrthographicCamera camera) {
        this.updateBody();

        this.applyVelocity(true);

        if(this.hasLight()) {
            this.getLight().setPosition(this.getPosition().x + this.getWidth() / 2, this.getPosition().y + this.getHeight() / 2);
        }

        this.getMind().update(camera);

        if(this.hasPhysicsBody()) {
            this.updatePhysicsBody();
        }

        if(this.isMoving()) {
            if (this.attacking) {
                this.attackAnimation.update(this.getDirection());
            } else {
                this.getDirectionalAnimation().update(this.getDirection());
            }
        }

        if(this.getHealth() <= 0) {
            this.explode();
        }
    }

}
