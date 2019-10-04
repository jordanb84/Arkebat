package com.ld45.game.animation;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ld45.game.entity.Direction;

import java.util.HashMap;

public class DirectionalAnimation {

    private HashMap<Direction, Animation> animations = new HashMap<Direction, Animation>();

    public DirectionalAnimation() {

    }

    public DirectionalAnimation(Animation rightAnimation, Animation leftAnimation) {
        this.animations.put(Direction.RIGHT, rightAnimation);
        this.animations.put(Direction.LEFT, leftAnimation);
    }

    public DirectionalAnimation(Animation upAnimation, Animation downAnimation, Animation rightAnimation, Animation leftAnimation) {
        this.animations.put(Direction.UP, upAnimation);
        this.animations.put(Direction.DOWN, downAnimation);
        this.animations.put(Direction.RIGHT, rightAnimation);
        this.animations.put(Direction.LEFT, leftAnimation);
    }

    public void render(SpriteBatch batch, Vector2 position, Direction direction) {
        this.animations.get(direction).render(batch, position);
    }

    public void update(Direction direction) {
        this.animations.get(direction).update();
    }

    public void addAnimationForDirection(Direction direction, Animation animation) {
        this.animations.put(direction, animation);
    }

    public void addAnimationForDirections(Animation animation, Direction ... directions) {
        for(Direction direction : directions) {
            this.animations.put(direction, animation);
        }
    }

    public void addAnimationForDirection(Direction direction, float frameDuration, String ... frameSpritePaths) {
        Animation animation = new Animation(frameDuration);
        animation.addFrames(frameSpritePaths);

        this.animations.put(direction, animation);
    }

    public float getWidth(Direction direction) {
        return this.animations.get(direction).getCurrentWidth();
    }

    public float getHeight(Direction direction) {
        return this.animations.get(direction).getCurrentHeight();
    }

    public Sprite getActiveSprite(Direction direction) {
        return this.animations.get(direction).getCurrentFrame().getSprite();
    }

}