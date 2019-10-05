package com.ld45.game.entity.mind.impl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ld45.game.entity.living.LivingEntity;
import com.ld45.game.entity.mind.EntityMind;

public class WanderAttackMind extends EntityMind {

    private float attackRadius = 128;

    private WanderState wanderState;

    public WanderAttackMind(LivingEntity parentEntity) {
        super(parentEntity);
        this.wanderState = new WanderState(this);
        this.registerState(this.wanderState);

        AttackState attackState = new AttackState(this);
        this.registerState(attackState);

        this.setState("wander");
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        super.render(batch, camera);
    }

    @Override
    public void update(OrthographicCamera camera) {
        super.update(camera);
    }

    @Override
    public void recalculateState(LivingEntity parentEntity) {
        Vector2 playerPosition = parentEntity.getParentMap().getEntityPlayer().getPosition();

        float distanceFromPlayer = parentEntity.getPosition().dst(playerPosition);

        if(distanceFromPlayer <= this.attackRadius) {
            this.setState("attack");
            parentEntity.getSprite().setColor(Color.RED);
        } else {
            if(this.getActiveState() instanceof AttackState) {
                this.wanderState.changeDirection();
            }

            this.setState("wander");
            parentEntity.getSprite().setColor(Color.WHITE);
        }
    }

}
