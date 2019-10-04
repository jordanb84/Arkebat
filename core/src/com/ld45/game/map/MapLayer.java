package com.ld45.game.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ld45.game.tile.Tile;
import com.ld45.game.tile.TileData;
import com.ld45.game.tile.TileRegistry;

import java.util.ArrayList;
import java.util.List;

public class MapLayer {

    private MapDefinition mapDefinition;

    private List<Integer> layerTiles = new ArrayList<Integer>();

    private List<TileData> layerTileData = new ArrayList<TileData>();

    private TileRegistry tileRegistry;

    public MapLayer(MapDefinition mapDefinition, List<Integer> layerTiles, TileRegistry tileRegistry) {
        this.mapDefinition = mapDefinition;
        this.layerTiles = layerTiles;
        this.tileRegistry = tileRegistry;
        this.createTileData();
    }

    public MapLayer(MapDefinition mapDefinition, int fillType, TileRegistry tileRegistry) {
        this.mapDefinition = mapDefinition;
        this.tileRegistry = tileRegistry;
        this.fillWithType(fillType);
        this.createTileData();
    }

    public void initiateTiles(Map parentMap) {
        for(int row = 0; row < this.mapDefinition.getMapHeight(); row++) {
            for(int column = 0; column < this.mapDefinition.getMapWidth(); column++) {
                int tileIndex = column + row * this.mapDefinition.getMapHeight();

                Vector2 tilePosition = new Vector2(column * this.mapDefinition.getTileWidth(), row * this.mapDefinition.getTileHeight());
                TileData tileData = this.layerTileData.get(tileIndex);

                this.getLayerTile(tileIndex).initiateInWorld(parentMap, tilePosition);
            }
        }
    }

    public void render(Map parentMap, SpriteBatch batch, OrthographicCamera camera) {
        for(int row = 0; row < this.mapDefinition.getMapHeight(); row++) {
            for(int column = 0; column < this.mapDefinition.getMapWidth(); column++) {
                int tileIndex = column + row * this.mapDefinition.getMapHeight();

                Vector2 tilePosition = new Vector2(column * this.mapDefinition.getTileWidth(), row * this.mapDefinition.getTileHeight());
                TileData tileData = this.layerTileData.get(tileIndex);

                this.getLayerTile(tileIndex).render(parentMap, batch, camera, tilePosition, tileData);
            }
        }
    }

    public void update(Map parentMap, OrthographicCamera camera) {
        for(int row = 0; row < this.mapDefinition.getMapHeight(); row++) {
            for(int column = 0; column < this.mapDefinition.getMapWidth(); column++) {
                int tileIndex = column + row * this.mapDefinition.getMapHeight();

                Vector2 tilePosition = new Vector2(column * this.mapDefinition.getTileWidth(), row * this.mapDefinition.getTileHeight());
                TileData tileData = this.layerTileData.get(tileIndex);

                this.getLayerTile(tileIndex).update(parentMap, camera, tilePosition, tileData);
            }
        }
    }

    public boolean collisionAt(Rectangle body) {
        for(int row = 0; row < this.mapDefinition.getMapHeight(); row++) {
            for(int column = 0; column < this.mapDefinition.getMapWidth(); column++) {
                int tileIndex = column + row * this.mapDefinition.getMapHeight();

                Tile tile = this.getLayerTile(tileIndex);

                Vector2 tilePosition = new Vector2(column * this.mapDefinition.getTileWidth(), row * this.mapDefinition.getTileHeight());

                Rectangle tileBody = tile.getBody(tilePosition);

                if(tileBody.overlaps(body) && tile.isSolid()) {
                    return true;
                }
            }
        }

        return false;
    }

    public void fillWithType(int fillType) {
        this.layerTiles.clear();

        for(int filledTiles = 0; filledTiles < this.mapDefinition.getTilesPerLayer(); filledTiles++) {
            this.layerTiles.add(fillType);
        }
    }

    private void createTileData() {
        this.layerTileData.clear();

        for(int tileDataCreated = 0; tileDataCreated < this.mapDefinition.getTilesPerLayer(); tileDataCreated++) {
            this.layerTileData.add(new TileData());
        }
    }

    public List<Integer> getLayerTiles() {
        return this.layerTiles;
    }

    public List<TileData> getLayerTileData() {
        return this.layerTileData;
    }

    public Tile getLayerTile(int tileIndex) {
        return this.tileRegistry.getTileById(this.layerTiles.get(tileIndex));
    }

}
