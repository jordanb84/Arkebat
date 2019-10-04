package com.ld45.game.entity.mind;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ld45.game.entity.living.LivingEntity;

import java.util.HashMap;

public abstract class EntityMind {

    private HashMap<String, EntityMindState> states = new HashMap<String, EntityMindState>();

    private EntityMindState activeState;

    private LivingEntity parentEntity;

    public EntityMind(LivingEntity parentEntity) {
        this.parentEntity = parentEntity;
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        this.renderActiveState(batch, camera);
    }

    public void update(OrthographicCamera camera) {
        this.updateActiveState(camera);
        this.recalculateState(this.getParentEntity());
    }

    public void renderActiveState(SpriteBatch batch, OrthographicCamera camera) {
        this.getActiveState().render(batch, camera, this.getParentEntity());
    }

    public void updateActiveState(OrthographicCamera camera) {
        this.getActiveState().update(camera, this.getParentEntity());
    }

    public void setState(String stateKey) {
        this.activeState = this.states.get(stateKey);
    }

    public void registerState(EntityMindState state) {
        this.states.put(state.getIdentifier(), state);
    }

    public EntityMindState getActiveState() {
        return this.activeState;
    }

    public LivingEntity getParentEntity() {
        return parentEntity;
    }

    public abstract void recalculateState(LivingEntity parentEntity);

}