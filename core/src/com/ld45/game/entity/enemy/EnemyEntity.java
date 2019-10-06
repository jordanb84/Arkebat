package com.ld45.game.entity.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ld45.game.animation.DirectionalAnimation;
import com.ld45.game.entity.impl.HealEntity;
import com.ld45.game.entity.living.LivingEntity;
import com.ld45.game.entity.living.impl.EntityPlayer;
import com.ld45.game.entity.projectile.impl.EntityFlameProjectile;
import com.ld45.game.map.Map;

public abstract class EnemyEntity extends LivingEntity {

    private DirectionalAnimation attackAnimation;

    private boolean attacking;

    private Color attackColor;
    private float attackAlpha;

    public EnemyEntity(Vector2 position, Map parentMap, float weight, Color attackColor, float attackAlpha) {
        super(position, parentMap, weight);
        this.attackColor = attackColor;
        this.attackAlpha = attackAlpha;
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
            Sprite attackSprite = this.attackAnimation.getActiveSprite(this.getDirection());

            attackSprite.setColor(this.attackColor);
            attackSprite.setAlpha(this.attackAlpha);
            this.attackAnimation.render(batch, this.getPosition(), this.getDirection());
            attackSprite.setColor(Color.WHITE);
            attackSprite.setAlpha(1);

            this.renderDamage(batch, this.attackAnimation.getActiveSprite(this.getDirection()));
        } else {
            this.getDirectionalAnimation().render(batch, this.getPosition(), this.getDirection());

            this.renderDamage(batch, this.getDirectionalAnimation().getActiveSprite(this.getDirection()));
        }

    }

    @Override
    public void update(OrthographicCamera camera) {
        this.updateBody();
        boolean withinCamera = camera.frustum.pointInFrustum(this.getPosition().x, this.getPosition().y, 0);

        if(withinCamera) {
            this.applyVelocity(true);

            if (this.hasLight()) {
                this.getLight().setPosition(this.getPosition().x + this.getWidth() / 2, this.getPosition().y + this.getHeight() / 2);
            }

            this.getMind().update(camera);

            if (this.hasPhysicsBody()) {
                this.updatePhysicsBody();
            }

            if (this.isMoving()) {
                if (this.attacking) {
                    this.attackAnimation.update(this.getDirection());
                } else {
                    this.getDirectionalAnimation().update(this.getDirection());
                }
            }

            if (this.getHealth() <= 0) {
                this.explode();
                this.onDeath();
            }
        }
    }

    public void fireFlame(Color tint, EntityPlayer player) {
        Vector2 destination = new Vector2(player.getPosition().x + player.getWidth() / 2, player.getPosition().y + player.getHeight() / 2);

        EntityFlameProjectile flame = new EntityFlameProjectile(new Vector2(this.getPosition().x, this.getPosition().y), this.getParentMap(), destination, true, this.getDamage());

        flame.setTint(tint);

        this.getParentMap().spawnEntity(flame);
    }

    public boolean isAttacking() {
        return this.attacking;
    }

    public DirectionalAnimation getAttackAnimation() {
        return this.attackAnimation;
    }

    @Override
    public void onDeath() {
        super.onDeath();
        HealEntity healEntity = new HealEntity(new Vector2(this.getPosition()), this.getParentMap(), this.getDamage());

        this.getParentMap().spawnEntity(healEntity);
    }

    public abstract int getDamage();

}
