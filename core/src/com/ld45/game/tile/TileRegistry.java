package com.ld45.game.tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.ld45.game.assets.Assets;
import com.ld45.game.tile.impl.BasicTile;

import java.util.*;

public class TileRegistry {

    private LinkedHashMap<Integer, Tile> registeredTiles = new LinkedHashMap<Integer, Tile>();
    private HashMap<String, Integer> tileNameIdCache = new HashMap<String, Integer>();

    private final int TILE_WIDTH;
    private final int TILE_HEIGHT;

    private final int SHEET_TILE_ROWS;
    private final int SHEET_TILE_COLUMNS;

    private List<JsonValue> tiles = new ArrayList<JsonValue>();

    public TileRegistry(String tileDataFilePath) {
        JsonValue tileData = new JsonReader().parse(Gdx.files.internal(tileDataFilePath).readString());

        String tileSheetPath = tileData.getString("sheetPath");
        Texture tileSheet = Assets.getInstance().getTexture(tileSheetPath);

        this.TILE_WIDTH = tileData.getInt("tileWidth");
        this.TILE_HEIGHT = tileData.getInt("tileHeight");

        this.SHEET_TILE_ROWS = tileData.getInt("sheetTileRows");
        this.SHEET_TILE_COLUMNS = tileData.getInt("sheetTileColumns");

        this.setupTiles(tileData);

        this.registerDefaultTiles(tileData, tileSheet);
        this.registerCustomTiles(tileSheet);
    }

    private void setupTiles(JsonValue tileData) {
        JsonValue.JsonIterator tiles = tileData.get("tiles").iterator();

        while(tiles.hasNext()) {
            this.tiles.add(tiles.next());
        }
    }

    private void registerDefaultTiles(JsonValue tileData, Texture tileSheet) {
        for(JsonValue tileValue : this.tiles) {
            int tileId = tileValue.getInt("id");

            boolean usesCustomClass = tileValue.getBoolean("usesCustomClass");

            if(!usesCustomClass) {
                this.registerTile(new BasicTile(tileId, tileSheet, this));
            }
        }
    }

    public JsonValue getTileDataForId(int tileId) {
        return this.tiles.get(tileId);
    }

    private void registerCustomTiles(Texture tileSheet) {

    }

    private void registerTile(Tile tileInstance) {
        this.registeredTiles.put(tileInstance.getTileId(), tileInstance);

        this.tileNameIdCache.put(tileInstance.getName(), tileInstance.getTileId());
    }

    public int getTileIdByName(String name) {
        try {
            return this.tileNameIdCache.get(name);
        } catch (NullPointerException noSuchTile) {
            Gdx.app.error("Tile fetch failed", "Tile by name " + name + " not registered. Returning ID 0");
            return 0;
        }
    }

    public Tile getTileById(int id) {
        return this.registeredTiles.get(id);
    }

    public Collection<Integer> getTileIds() {
        return this.registeredTiles.keySet();
    }

    public int getTileWidth() {
        return this.TILE_WIDTH;
    }

    public int getTileHeight() {
        return this.TILE_HEIGHT;
    }

    public int getSheetTileColumns() {
        return this.SHEET_TILE_COLUMNS;
    }

    public int getSheetTileRows() {
        return this.SHEET_TILE_ROWS;
    }

}
