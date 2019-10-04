package com.ld45.game.entity.mind;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ld45.game.entity.Direction;
import com.ld45.game.map.Map;
import com.ld45.game.entity.living.LivingEntity;

public abstract class EntityMindState {

    private EntityMind parentMind;

    private String identifier;

    public EntityMindState(EntityMind parentMind, String identifier) {
        this.parentMind = parentMind;
        this.identifier = identifier;
    }

    public abstract void render(SpriteBatch batch, OrthographicCamera camera, LivingEntity parentEntity);

    public abstract void update(OrthographicCamera camera, LivingEntity parentEntity);

    public abstract void onCollision(Direction collisionDirection, Vector2 collisionPosition);

    public EntityMind getParentMind() {
        return parentMind;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public LivingEntity getParentEntity() {
        return this.getParentMind().getParentEntity();
    }

    public Map getParentMap() {
        return this.getParentEntity().getParentMap();
    }

}