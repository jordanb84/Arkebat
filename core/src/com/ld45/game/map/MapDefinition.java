package com.ld45.game.map;

public class MapDefinition {

    private final int MAP_LAYERS;

    private final int MAP_WIDTH;
    private final int MAP_HEIGHT;

    private final int TILE_WIDTH;
    private final int TILE_HEIGHT;

    private final int TILES_PER_LAYER;

    public MapDefinition(int mapLayers, int mapWidth, int mapHeight, int tileWidth, int tileHeight) {
        this.MAP_LAYERS = mapLayers;
        this.MAP_WIDTH = mapWidth;
        this.MAP_HEIGHT = mapHeight;
        this.TILE_WIDTH = tileWidth;
        this.TILE_HEIGHT = tileHeight;
        this.TILES_PER_LAYER = this.MAP_WIDTH * this.MAP_HEIGHT;
    }

    public int getMapLayers() {
        return this.MAP_LAYERS;
    }

    public int getMapWidth() {
        return this.MAP_WIDTH;
    }

    public int getMapHeight() {
        return this.MAP_HEIGHT;
    }

    public int getTileWidth() {
        return this.TILE_WIDTH;
    }

    public int getTileHeight() {
        return this.TILE_HEIGHT;
    }

    public int getTilesPerLayer() {
        return this.TILES_PER_LAYER;
    }

}
