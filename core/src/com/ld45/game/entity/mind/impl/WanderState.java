package com.ld45.game.entity.mind.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ld45.game.entity.Direction;
import com.ld45.game.entity.living.LivingEntity;
import com.ld45.game.entity.mind.EntityMind;
import com.ld45.game.entity.mind.EntityMindState;

import java.util.Random;

public class WanderState extends EntityMindState {

    private float baseDirectionChangeInterval = 1.8f;
    private float directionChangeInterval = baseDirectionChangeInterval;

    private float elapsedSinceDirectionChange;

    private Random directionChangeRandom = new Random();

    public WanderState(EntityMind parentMind) {
        super(parentMind, "wander");
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera, LivingEntity parentEntity) {

    }

    @Override
    public void update(OrthographicCamera camera, LivingEntity parentEntity) {
        this.elapsedSinceDirectionChange += 1 * Gdx.graphics.getDeltaTime();

        if(this.elapsedSinceDirectionChange >= this.directionChangeInterval) {
            this.changeDirection();
        }

        parentEntity.moveAlongCurrentDirection();
    }

    @Override
    public void onCollision(Direction collisionDirection, Vector2 collisionPosition) {
        this.changeDirection();
    }

    private void changeDirection() {
        Direction nextDirection = Direction.values()[this.directionChangeRandom.nextInt(Direction.values().length)];

        this.getParentEntity().setDirection(nextDirection);

        this.directionChangeInterval = this.baseDirectionChangeInterval * this.directionChangeRandom.nextFloat() * 2;
        this.elapsedSinceDirectionChange = 0;
    }

}
