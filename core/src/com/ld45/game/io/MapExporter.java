package com.ld45.game.io;

import com.badlogic.gdx.files.FileHandle;
import com.ld45.game.map.Map;
import com.ld45.game.map.MapLayer;

import java.util.List;

public class MapExporter {

    private static final MapExporter INSTANCE = new MapExporter();

    public void exportToFile(Map map, FileHandle file) {
        String tiles = this.getTilesAsString(map.getMapLayers());

        file.writeString(tiles, false);
    }

    private String getTilesAsString(List<MapLayer> mapLayers) {
        String tiles = ("");

        for(MapLayer mapLayer : mapLayers) {
            for(Integer tileId : mapLayer.getLayerTiles()) {
                tiles += (tileId + ",");
            }

            tiles += ("\n");
        }

        return tiles;
    }

    public static MapExporter getInstance() {
        return INSTANCE;
    }

}
