package com.ld45.game.entity.mind.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ld45.game.entity.Direction;
import com.ld45.game.entity.living.LivingEntity;
import com.ld45.game.entity.mind.EntityMind;
import com.ld45.game.entity.mind.EntityMindState;

import java.util.Random;

public class AttackState extends EntityMindState {

    private float distanceThreshold = 8;

    public AttackState(EntityMind parentMind) {
        super(parentMind, "attack");
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera, LivingEntity parentEntity) {

    }

    @Override
    public void update(OrthographicCamera camera, LivingEntity parentEntity) {
        Vector2 playerPosition = this.getParentMap().getEntityPlayer().getPosition();

        Direction movementDirection = null;

        float distanceX = Math.abs(parentEntity.getPosition().x - playerPosition.x);

        if(parentEntity.getPosition().x < playerPosition.x && distanceX > this.distanceThreshold) {
            movementDirection = Direction.RIGHT;
        }

        if(parentEntity.getPosition().x > playerPosition.x && distanceX > distanceThreshold) {
            movementDirection = Direction.LEFT;
        }

        float distanceY = Math.abs(parentEntity.getPosition().y - playerPosition.y);

        if(parentEntity.getPosition().y < playerPosition.y && distanceY > this.distanceThreshold) {
            movementDirection = Direction.UP;
        }

        if(parentEntity.getPosition().y > playerPosition.y && distanceY > this.distanceThreshold) {
            movementDirection = Direction.DOWN;
        }

        if(movementDirection != null) {
            parentEntity.move(movementDirection);
        }

        parentEntity.moveAlongCurrentDirection();
        parentEntity.getDirectionalAnimation().update(parentEntity.getDirection());
    }

    @Override
    public void onCollision(Direction collisionDirection, Vector2 collisionPosition) {
        //this.changeDirection();
    }

}
