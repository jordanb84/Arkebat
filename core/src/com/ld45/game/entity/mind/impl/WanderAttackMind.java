package com.ld45.game.entity.mind.impl;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ld45.game.entity.living.LivingEntity;
import com.ld45.game.entity.mind.EntityMind;

public class WanderAttackMind extends EntityMind {

    public WanderAttackMind(LivingEntity parentEntity) {
        super(parentEntity);
        WanderState wanderState = new WanderState(this);
        this.registerState(wanderState);

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
        this.setState("wander");
    }

}
