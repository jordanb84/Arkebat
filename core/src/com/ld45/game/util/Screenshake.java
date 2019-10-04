package com.ld45.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Screenshake {

    private float currentShakeDuration;
    private float currentShakeRoughness;

    private boolean performingShake;

    private float currentShakeElapsed;

    private Random shakeRandom = new Random();

    private Vector2 differenceFromShake = new Vector2();

    public Screenshake() {

    }

    public void update(OrthographicCamera camera) {
        if(this.performingShake) {
            this.currentShakeElapsed += 1 * Gdx.graphics.getDeltaTime();

            float shakeForFrameX = this.currentShakeRoughness * this.shakeRandom.nextInt(3);
            float shakeForFrameY = this.currentShakeRoughness * this.shakeRandom.nextInt(3);

            shakeForFrameX = this.shakeRandom.nextBoolean() ? shakeForFrameX : -shakeForFrameX;
            shakeForFrameY = this.shakeRandom.nextBoolean() ? shakeForFrameY : -shakeForFrameY;

            this.differenceFromShake.add(shakeForFrameX, shakeForFrameY);

            camera.position.add(shakeForFrameX, shakeForFrameY, 0);
            camera.update();

            if(this.currentShakeElapsed >= this.currentShakeDuration) {
                this.performingShake = false;
                this.restoreCameraPosition(camera);
            }
        }
    }

    public void startShake(float duration, float roughness) {
        this.currentShakeDuration = duration;
        this.currentShakeRoughness = roughness;
        this.currentShakeElapsed = 0;
        this.performingShake = true;
        this.differenceFromShake.set(0, 0);
    }

    private void restoreCameraPosition(OrthographicCamera camera) {
        camera.position.add(-this.differenceFromShake.x, -this.differenceFromShake.y, 0);
        camera.update();
    }

}
