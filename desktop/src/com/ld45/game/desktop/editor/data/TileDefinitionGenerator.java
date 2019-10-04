package com.ld45.game.desktop.editor.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.json.JSONArray;
import org.json.JSONObject;

public class TileDefinitionGenerator {

    private static final TileDefinitionGenerator INSTANCE = new TileDefinitionGenerator();

    /**
     * Generates generic tile definition data for all tiles in a sprite sheet
     */
    public JSONObject generateTileDefinitionFromSheet(String tileSheetPath, int tileWidth, int tileHeight) {
        JSONObject tileDefinitionData = new JSONObject();

        Texture tileSheetTexture = new Texture(Gdx.files.internal(tileSheetPath));
        int tileSheetWidth = tileSheetTexture.getWidth();
        int tileSheetHeight = tileSheetTexture.getHeight();

        int sheetTileColumns = tileSheetWidth / tileWidth;
        int sheetTileRows = tileSheetHeight / tileHeight;

        int totalTilesNeeded = sheetTileColumns * sheetTileRows;
        System.out.println("Tile sheet width: " + tileSheetWidth + " Tile sheet height: " + tileSheetHeight + " Sheet columns: " + sheetTileColumns + " Sheet rows: " + sheetTileRows + " Should make " + totalTilesNeeded + " tiles");

        tileDefinitionData.put("sheetPath", tileSheetPath);
        tileDefinitionData.put("tileWidth", tileWidth);
        tileDefinitionData.put("tileHeight", tileHeight);
        tileDefinitionData.put("sheetTileColumns", sheetTileColumns);
        tileDefinitionData.put("sheetTileRows", sheetTileRows);

        JSONArray tiles = new JSONArray();

        int addedTiles = 0;

        for(int generatedRow = 0; generatedRow < sheetTileRows; generatedRow++) {
            for(int generatedColumn = 0; generatedColumn < sheetTileColumns; generatedColumn++) {
                int tileIndex = addedTiles;

                int tileSheetX = generatedColumn * tileWidth;
                int tileSheetY = generatedRow * tileHeight;

                JSONObject tileData = new JSONObject();

                tileData.put("id", tileIndex);
                tileData.put("name", "tile_" + tileIndex);
                tileData.put("solid", false);
                tileData.put("usesCustomClass", false);
                tileData.put("sheetX", tileSheetX);
                tileData.put("sheetY", tileSheetY);

                tiles.put(tileData);

                System.out.println("Generated tile " + generatedColumn + " for row " + generatedRow + " TILE ID " + tileIndex + " Exact data: " + tileData.toString());
                addedTiles++;
            }
        }

        tileDefinitionData.put("tiles", tiles);

        System.out.println("Generated " + addedTiles + " tiles");

        return tileDefinitionData;
    }

    public static TileDefinitionGenerator getInstance() {
        return INSTANCE;
    }

}
