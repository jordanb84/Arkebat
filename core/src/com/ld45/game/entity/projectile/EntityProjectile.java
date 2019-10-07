package com.ld45.game.entity.projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ld45.game.animation.Animation;
import com.ld45.game.entity.Direction;
import com.ld45.game.entity.Entity;
import com.ld45.game.entity.enemy.EnemyEntity;
import com.ld45.game.entity.impl.BurstEntity;
import com.ld45.game.entity.living.impl.EntityPlayer;
import com.ld45.game.map.Map;

public abstract class EntityProjectile extends Entity {

    private Animation animation;

    private Vector2 destination;
    private Rectangle destinationBody = new Rectangle();

    private boolean attacksPlayer;

    private float damage;

    private float homingThreshold = 26;

    private float maxLifespan = 5;
    private float lifeElapsed;

    private Color tint;
    private boolean hasTint;

    public EntityProjectile(Vector2 position, Map parentMap, Vector2 destination, float speed, boolean attacksPlayer, float damage) {
        super(position, parentMap, 0);
        this.animation = this.setupAnimation();
        this.destination = destination;
        this.attacksPlayer = attacksPlayer;
        this.damage = damage;
        this.setSpeed(speed, speed);
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        if(this.hasTint) {
            this.animation.getCurrentFrame().getSprite().setColor(this.tint);
        }

        this.animation.render(batch, this.getPosition(), 1, (float) this.getRotationTowardPosition(this.destination));

        this.animation.getCurrentFrame().getSprite().setColor(Color.WHITE);
    }

    @Override
    public void update(OrthographicCamera camera) {
        super.update(camera);
        this.destinationBody.set(this.destination.x, this.destination.y, this.animation.getCurrentWidth(), this.animation.getCurrentHeight());
        this.moveTowardDestination();

        this.setSprite(this.animation.getCurrentFrame().getSprite());

        this.animation.update();

        if(this.getBody().overlaps(this.destinationBody)) {
            this.explode();
        }

        boolean homing = false;

        if(this.attacksPlayer) {
            EntityPlayer player = this.getParentMap().getEntityPlayer();

            if(this.getBody().overlaps(player.getBody())) {
                this.explode();

                this.getParentMap().getEntityPlayer().damage(this.damage);

                float distance = player.getPosition().dst(this.getPosition());

                if (distance <= this.homingThreshold) {
                    this.destination.set(player.getPosition());
                    homing = true;
                }
            }
        } else {
            for(EnemyEntity enemy : this.getParentMap().getEnemyCache()) {
                if(this.getBody().overlaps(enemy.getBody())) {
                    this.explode();

                    enemy.damage(this.damage);
                }

                if(!homing) {
                    float distance = enemy.getPosition().dst(this.getPosition());

                    if (distance <= this.homingThreshold) {
                        this.destination.set(enemy.getPosition());
                        homing = true;
                    }
                }
            }
        }

        this.lifeElapsed += 1 * Gdx.graphics.getDeltaTime();

        if(this.lifeElapsed >= this.maxLifespan) {
            this.explode();
        }
    }

    @Override
    public void explode() {
        this.getParentMap().despawnEntity(this);

        int totalParticles = 10;

        for(int particle = 0; particle < totalParticles; particle++) {
            BurstEntity burstEntity = new BurstEntity(new Vector2(this.getPosition()), this.getParentMap(), particle, totalParticles, 2f, 1.2f, 1.2f, this.getSprite());

            if(this.hasTint) {
                burstEntity.setTint(this.tint);
            }

            this.getParentMap().spawnEntity(burstEntity);
        }
    }

    private void moveTowardDestination() {
        if(this.getPosition().x < this.destinationBody.x) {
            this.move(Direction.RIGHT);
        }

        if(this.getPosition().x > this.destinationBody.x) {
            this.move(Direction.LEFT);
        }

        if(this.getPosition().y < this.destinationBody.y) {
            this.move(Direction.UP);
        }

        if(this.getPosition().y > this.destinationBody.y) {
            this.move(Direction.DOWN);
        }
    }

    public abstract Animation setupAnimation();

    @Override
    public void updateBody() {
        this.getBody(false).set(this.getPosition().x, this.getPosition().y, this.animation.getCurrentWidth(), this.animation.getCurrentHeight());
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    @Override
    public void onCollision(Direction direction) {
        super.onCollision(direction);
        this.explode();
    }

    public void setTint(Color tint) {
        this.tint = tint;
        this.hasTint = true;
    }

    public void setMaxLifespan(float maxLifespan) {
        this.maxLifespan = maxLifespan;
    }

}
