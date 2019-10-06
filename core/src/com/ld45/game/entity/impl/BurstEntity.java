package com.ld45.game.entity.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ld45.game.entity.Direction;
import com.ld45.game.entity.Entity;
import com.ld45.game.map.Map;

public class BurstEntity extends Entity {

    private float rotation;

    private float duration;

    private float elapsed;

    private float speedMultiplier;

    private float scaleMultiplier;

    private Color tint;
    private boolean hasTint;

    public BurstEntity(Vector2 position, Map parentMap, int index, int total, float duration, float speedMultiplier, float scaleMultiplier, Sprite sprite) {
        super(position, parentMap, 0);
        this.rotation = (360 / total) * index;
        this.setSprite(sprite);
        this.duration = duration;
        this.speedMultiplier = speedMultiplier;
        this.scaleMultiplier = scaleMultiplier;
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        if(this.hasTint) {
            this.getSprite().setColor(this.tint);
        }

        this.getSprite().setRotation(this.rotation);
        this.getSprite().setScale(0.5f * this.scaleMultiplier);
        super.render(batch, camera);
        this.getSprite().setRotation(0);
        this.getSprite().setScale(1);
        this.getSprite().setColor(Color.WHITE);
    }

    @Override
    public void update(OrthographicCamera camera) {
        super.update(camera);
        this.moveAlongRotation(this.rotation, 10);

        this.elapsed += 1 * Gdx.graphics.getDeltaTime();

        if(this.elapsed >= this.duration) {
            this.getParentMap().despawnEntity(this);
        }

        if(this.getParentMap().collisionAt(this.getBody())) {
            this.getParentMap().despawnEntity(this);
        }
    }

    public void moveAlongRotation(float rotation, float speed) {
        speed = speed * 10 * Gdx.graphics.getDeltaTime();
        speed = speed * this.speedMultiplier;

        float xRotationMovement = -speed * (float) Math.cos(Math.toRadians(rotation - 90));
        float yRotationMovement = -speed * (float) Math.sin(Math.toRadians(rotation - 90));

        this.getPosition().add(xRotationMovement, yRotationMovement);
    }

    public void setTint(Color tint) {
        this.tint = tint;
        this.hasTint = true;
    }

}
