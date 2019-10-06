package com.ld45.game.entity.mind.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ld45.game.entity.Direction;
import com.ld45.game.entity.enemy.EnemyEntity;
import com.ld45.game.entity.living.LivingEntity;
import com.ld45.game.entity.mind.EntityMind;
import com.ld45.game.entity.mind.EntityMindState;

import java.util.Random;

public class AttackState extends EntityMindState {

    private float distanceThreshold = 8;

    private float baseAttackInterval;
    private float attackInterval = baseAttackInterval;

    private float elapsedSinceAttacked;

    private Random attackIntervalRandom = new Random();

    private boolean followTarget;

    private float attackRadius = 160;

    public AttackState(EntityMind parentMind, boolean followTarget, float attackInterval) {
        super(parentMind, "attack");
        this.followTarget = followTarget;
        this.baseAttackInterval = attackInterval;
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera, LivingEntity parentEntity) {

    }

    @Override
    public void update(OrthographicCamera camera, LivingEntity parentEntity) {
        Vector2 playerPosition = this.getParentMap().getEntityPlayer().getPosition();

        float distanceX = Math.abs(parentEntity.getPosition().x - playerPosition.x);

        float distance = parentEntity.getPosition().dst(playerPosition);

        if(this.followTarget) {
            Direction movementDirection = null;

            if (distance >= 24) {
                if (parentEntity.getPosition().x < playerPosition.x && distanceX > this.distanceThreshold) {
                    movementDirection = Direction.RIGHT;
                }

                if (parentEntity.getPosition().x > playerPosition.x && distanceX > distanceThreshold) {
                    movementDirection = Direction.LEFT;
                }

                float distanceY = Math.abs(parentEntity.getPosition().y - playerPosition.y);

                if (parentEntity.getPosition().y < playerPosition.y && distanceY > this.distanceThreshold) {
                    movementDirection = Direction.UP;
                }

                if (parentEntity.getPosition().y > playerPosition.y && distanceY > this.distanceThreshold) {
                    movementDirection = Direction.DOWN;
                }

                if (movementDirection != null) {
                    parentEntity.move(movementDirection);
                }


                parentEntity.moveAlongCurrentDirection();
                parentEntity.getDirectionalAnimation().update(parentEntity.getDirection());

            }

        }

        ((EnemyEntity) parentEntity).setAttacking(distance < this.attackRadius);

        this.elapsedSinceAttacked += 1 * Gdx.graphics.getDeltaTime();

        if(distance < attackRadius) {
            if (this.elapsedSinceAttacked >= this.attackInterval) {
                ((EnemyEntity) this.getParentEntity()).attackPlayer(this.getParentMap().getEntityPlayer());

                this.attackInterval = this.baseAttackInterval * this.attackIntervalRandom.nextFloat() * 3;

                if (this.attackInterval > this.baseAttackInterval) {
                    this.attackInterval = baseAttackInterval;
                }

                this.elapsedSinceAttacked = 0;
            }
        }
    }

    @Override
    public void onCollision(Direction collisionDirection, Vector2 collisionPosition) {
        //this.changeDirection();
    }

}
