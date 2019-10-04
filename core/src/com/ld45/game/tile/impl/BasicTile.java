package com.ld45.game.tile.impl;

import com.badlogic.gdx.graphics.Texture;
import com.ld45.game.tile.Tile;
import com.ld45.game.tile.TileRegistry;

public class BasicTile extends Tile {

    public BasicTile(int tileId, Texture tileSheet, TileRegistry tileRegistry) {
        super(tileId, tileSheet, tileRegistry);
    }

}