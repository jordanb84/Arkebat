package com.ld45.game.io;

import com.badlogic.gdx.files.FileHandle;
import com.ld45.game.map.MapDefinition;
import com.ld45.game.map.Map;
import com.ld45.game.map.MapLayer;
import com.ld45.game.state.StateManager;
import com.ld45.game.tile.TileRegistry;

import java.util.ArrayList;
import java.util.List;

public class MapImporter {

    private static final MapImporter INSTANCE = new MapImporter();

    public Map getMapFromFile(FileHandle file, TileRegistry tileRegistry, StateManager stateManager) {
        MapDefinition mapDefinition = new MapDefinition(3, 40, 40, tileRegistry.getTileWidth(), tileRegistry.getTileHeight());

        Map map = new Map(mapDefinition, this.getTilesFromFile(file, mapDefinition, tileRegistry), stateManager);

        return map;
    }

    public List<MapLayer> getTilesFromFile(FileHandle file, MapDefinition mapDefinition, TileRegistry tileRegistry) {
        List<MapLayer> mapLayers = new ArrayList<MapLayer>();

        String fileData = file.readString();

        String[] fileTileLayers = fileData.split("\n");

        for(String fileTileLayer : fileTileLayers) {
            String[] fileLayerTiles = fileTileLayer.split(",");

            List<Integer> layerTiles = new ArrayList<Integer>();

            for(String fileLayerTile : fileLayerTiles) {
                layerTiles.add(Integer.parseInt(fileLayerTile));
            }

            mapLayers.add(new MapLayer(mapDefinition, layerTiles, tileRegistry));
        }

        return mapLayers;
    }

    public static MapImporter getInstance() {
        return INSTANCE;
    }
}
