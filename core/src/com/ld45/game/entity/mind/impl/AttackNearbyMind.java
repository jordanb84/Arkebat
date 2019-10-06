package com.ld45.game.entity.mind.impl;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ld45.game.entity.living.LivingEntity;
import com.ld45.game.entity.mind.EntityMind;

public class AttackNearbyMind extends EntityMind {

    public AttackNearbyMind(LivingEntity parentEntity, float attackInterval) {
        super(parentEntity);
        AttackState attackState = new AttackState(this, false, attackInterval);
        this.registerState(attackState);
        this.setState("attack");
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
        this.setState("attack");
    }

}
