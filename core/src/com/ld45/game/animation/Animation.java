package com.ld45.game.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ld45.game.assets.Assets;

import java.util.ArrayList;
import java.util.List;

public class Animation {

    private List<Frame> frames = new ArrayList<Frame>();

    private int currentFrame;

    private float elapsedSinceFrameChange;

    private float defaultFrameDuration;

    public Animation(float defaultFrameDuration) {
        this.defaultFrameDuration = defaultFrameDuration;
    }

    public void render(SpriteBatch batch, Vector2 position) {
        this.getCurrentFrame().render(batch, position);
    }

    public void render(SpriteBatch batch, Vector2 position, float alpha, float rotation) {
        Sprite currentFrameSprite = this.getCurrentFrame().getSprite();

        currentFrameSprite.setAlpha(alpha);
        currentFrameSprite.setRotation(rotation);
        this.getCurrentFrame().render(batch, position);
        currentFrameSprite.setAlpha(1);
        currentFrameSprite.setRotation(0);
    }

    public void update() {
        this.elapsedSinceFrameChange += 1 * Gdx.graphics.getDeltaTime();

        if(this.elapsedSinceFrameChange >= this.getCurrentFrame().getDuration()) {
            this.nextFrame();
        }
    }

    public void addFrame(Frame frame) {
        this.frames.add(frame);
    }

    public void addFrame(String frame) {
        this.frames.add(new Frame(Assets.getInstance().getSprite(frame), this.defaultFrameDuration));
    }

    public void addFrames(float duration, String ... frames) {
        for(String frame : frames) {
            Sprite frameSprite = Assets.getInstance().getSprite(frame);
            this.addFrame(new Frame(frameSprite, duration));
        }
    }

    public void addFrames(String ... frames) {
        this.addFrames(this.defaultFrameDuration, frames);
    }

    public void nextFrame() {
        this.currentFrame++;

        if(this.currentFrame >= this.frames.size()) {
            this.currentFrame = 0;
        }

        this.elapsedSinceFrameChange = 0;
    }

    public Frame getCurrentFrame() {
        return this.frames.get(this.currentFrame);
    }

    public float getCurrentWidth() {
        return this.getCurrentFrame().getSprite().getWidth();
    }

    public float getCurrentHeight() {
        return this.getCurrentFrame().getSprite().getHeight();
    }

}