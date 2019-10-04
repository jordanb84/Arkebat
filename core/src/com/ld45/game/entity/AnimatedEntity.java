package com.ld45.game.entity;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.ld45.game.animation.DirectionalAnimation;
import com.ld45.game.map.Map;

public abstract class AnimatedEntity extends Entity {

    private DirectionalAnimation directionalAnimation;

    public AnimatedEntity(Vector2 position, Map parentMap, float weight) {
        super(position, parentMap, weight);
        this.directionalAnimation = this.setupAnimation();
    }

    public abstract DirectionalAnimation setupAnimation();

    @Override
    public Sprite getSprite() {
        return this.directionalAnimation.getActiveSprite(this.getDirection());
    }

    @Override
    public void update(OrthographicCamera camera) {
        super.update(camera);
        if(this.isMoving()) {
            this.directionalAnimation.update(this.getDirection());
        }
    }
}
