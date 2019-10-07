package com.ld45.game.tile;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.ld45.game.map.Map;

public class Tile {

    private int tileId;

    private Sprite sprite;

    private String name;

    private boolean solid;

    private TileRegistry tileRegistry;

    public Tile(int tileId, Texture tileSheet, TileRegistry tileRegistry) {
        JsonValue tileData = tileRegistry.getTileDataForId(tileId);

        int tileSheetX = tileData.getInt("sheetX");
        int tileSheetY = tileData.getInt("sheetY");

        this.tileId = tileId;
        this.sprite = new Sprite(new TextureRegion(tileSheet, tileSheetX, tileSheetY, tileRegistry.getTileWidth(), tileRegistry.getTileHeight()));
        this.name = tileData.getString("name");
        this.solid = tileData.getBoolean("solid");

        this.tileRegistry = tileRegistry;
    }

    public void initiateInWorld(Map parentMap, Vector2 position) {

    }

    public void destroyInWorld(Map parentMap, Vector2 position) {

    }

    public void render(Map parentMap, SpriteBatch batch, OrthographicCamera camera, Vector2 position, TileData tileData) {
        this.getSprite().setPosition(position.x, position.y);
        this.getSprite().draw(batch);
    }

    public void update(Map parentMap, OrthographicCamera camera, Vector2 position, TileData tileData) {
        tileData.updatePhysicsBody(parentMap.getPhysicsWorld(), position, this);
    }

    public int getTileId() {
        return this.tileId;
    }

    public Rectangle getBody(Vector2 position) {
        return new Rectangle(position.x, position.y, this.getBoxSize().x, this.getBoxSize().y);
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public boolean isSolid() {
        return this.solid;
    }

    public TileRegistry getTileRegistry() {
        return this.tileRegistry;
    }

    public String getName() {
        return this.name;
    }

    public float getWidth() {
        return this.getSprite().getWidth();
    }

    public float getHeight() {
        return this.getSprite().getHeight();
    }

    public Vector2 getBoxSize() {
        return new Vector2(this.getWidth(), this.getHeight());
    }

}