package com.ld45.game.tile.impl;

import box2dLight.Light;
import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.ld45.game.map.Map;
import com.ld45.game.tile.TileData;
import com.ld45.game.tile.TileRegistry;

import java.util.Random;

public class LightTile extends BasicTile {

    private int lightSize = 40;

    private Color color;

    private Random lightFlickerRandom = new Random();

    public LightTile(Color color, int tileId, Texture tileSheet, TileRegistry tileRegistry) {
        super(tileId, tileSheet, tileRegistry);
        this.color = color;
    }

    @Override
    public void destroyInWorld(Map parentMap, Vector2 position) {
        super.destroyInWorld(parentMap, position);
    }

    @Override
    public void initiateInWorld(Map parentMap, Vector2 position) {
        super.initiateInWorld(parentMap, position);
    }

    @Override
    public void update(Map parentMap, OrthographicCamera camera, Vector2 position, TileData tileData) {
        super.update(parentMap, camera, position, tileData);
        if(tileData.getLight() == null) {
            tileData.setLight(new PointLight(parentMap.getRayHandler(), parentMap.getLightRayCount(), this.color, this.lightSize, position.x + this.getWidth() / 2, position.y + this.getHeight() / 2));
            tileData.getLight().setXray(true);
        }

        this.flickerLight(20, 50, 30, tileData);
    }

    public void flickerLight(int amount, int maxDistance, int minDistance, TileData tileData) {
        PointLight light = tileData.getLight();

        int flickerAmount = this.lightFlickerRandom.nextInt((int) light.getDistance() / amount);

        float originalDistance = light.getDistance();

        float distanceModifier = this.lightFlickerRandom.nextBoolean() ? originalDistance : -originalDistance;

        light.setDistance(originalDistance + distanceModifier);

        if (light.getDistance() > maxDistance) {
            light.setDistance(originalDistance - flickerAmount);
        }

        if (light.getDistance() < minDistance) {
            light.setDistance(originalDistance + flickerAmount);
        }
    }

}
