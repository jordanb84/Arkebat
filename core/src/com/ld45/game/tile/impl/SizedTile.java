package com.ld45.game.tile.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.ld45.game.tile.Tile;
import com.ld45.game.tile.TileRegistry;

public class SizedTile extends Tile {

    private Vector2 boxSize;

    public SizedTile(Vector2 boxSize, int tileId, Texture tileSheet, TileRegistry tileRegistry) {
        super(tileId, tileSheet, tileRegistry);
        this.boxSize = boxSize;
    }

    @Override
    public Vector2 getBoxSize() {
        return this.boxSize;
    }

}