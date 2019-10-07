package com.ld45.game.desktop.editor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.ld45.game.assets.Assets;
import com.ld45.game.desktop.editor.data.TileDefinitionGenerator;
import com.ld45.game.io.MapExporter;
import com.ld45.game.io.MapImporter;
import com.ld45.game.map.Map;
import com.ld45.game.map.MapDefinition;
import com.ld45.game.map.MapLayer;
import com.ld45.game.state.StateManager;
import com.ld45.game.tile.Tile;
import com.ld45.game.tile.TileData;
import com.ld45.game.tile.TileRegistry;
import org.json.JSONObject;

import javax.swing.*;
import java.io.File;

public class MapEditor extends ApplicationAdapter {

    private Map map;

    private TileRegistry tileRegistry;

    private int placingTileId;

    private int editingLayerIndex = 0;

    private Rectangle mouseBody = new Rectangle();

    private boolean overlapping;
    private Rectangle overlappingBody = new Rectangle();

    private Sprite overlaySprite;

    private boolean selectingTile;

    private boolean playtesting;

    private OrthographicCamera primaryCamera;
    private OrthographicCamera menuCamera;

    private SpriteBatch batch;

    private boolean mouseReleasedSinceTileSelection = true;

    @Override
    public void create() {
        this.writeTilesetDataForSheet("tile/tileset.png", 32, 32);

        this.tileRegistry = new TileRegistry("tile/tiledata.data");

        this.placingTileId = this.tileRegistry.getTileIdByName("base");

        this.primaryCamera = new OrthographicCamera();
        this.primaryCamera.setToOrtho(false, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        this.menuCamera = new OrthographicCamera();
        this.menuCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.batch = new SpriteBatch();

        MapDefinition mapDefinition = new MapDefinition(3, 40, 40, tileRegistry.getTileWidth(), tileRegistry.getTileHeight());
        this.map = new Map(mapDefinition, this.tileRegistry.getTileIdByName("base"), this.tileRegistry, new StateManager());

        this.overlaySprite = Assets.getInstance().getSprite("tile/overlay.png");
    }

    private void writeTilesetDataForSheet(String tileSheetPath, int tileWidth, int tileHeight) {
        TileDefinitionGenerator generator = TileDefinitionGenerator.getInstance();

        JSONObject generatedData = generator.generateTileDefinitionFromSheet(tileSheetPath, tileWidth, tileHeight);

        Gdx.files.external("./map/tiledata.data").writeString(generatedData.toString(4), false);
    }

    @Override
    public void render() {
        this.update(this.primaryCamera);

        this.batch.begin();
        this.batch.setProjectionMatrix(this.primaryCamera.combined);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.map.render(this.batch, this.primaryCamera, this.playtesting);

        if(this.overlapping) {
            this.overlaySprite.setSize(this.tileRegistry.getTileWidth(), this.tileRegistry.getTileHeight());
            this.overlaySprite.setPosition(this.overlappingBody.getX(), this.overlappingBody.getY());
            this.overlaySprite.setAlpha(0.5f);
            this.overlaySprite.draw(batch);
        }

        if(this.selectingTile) {
            this.renderTileSelectionBar(this.batch, this.primaryCamera);
        }

        this.batch.end();

        //System.out.println("FPS " + Gdx.graphics.getFramesPerSecond());
    }

    public void update(OrthographicCamera camera) {
        this.updateTitle();

        this.updateMouseBody(camera);

        if(!this.selectingTile && !this.playtesting) {
            this.pollEditingInput(camera);

            camera.update();
        }

        if(this.playtesting) {
            this.map.update(camera);
        }

        this.pollEditorStateInput();
    }

    private void updateAndPlaceTiles(OrthographicCamera camera) {
        MapDefinition mapDefinition = this.map.getMapDefinition();

        MapLayer editingLayer = this.map.getMapLayer(this.editingLayerIndex);

        boolean liveUpdate = true;

        this.overlapping = false;

        for(int row = 0; row < mapDefinition.getMapHeight(); row++) {
            for(int column = 0; column < mapDefinition.getMapWidth(); column++) {
                int tileIndex = column + row * mapDefinition.getMapHeight();

                int tileId = editingLayer.getLayerTiles().get(tileIndex);
                Tile tile = this.tileRegistry.getTileById(tileId);

                Vector2 tilePosition = new Vector2(column * mapDefinition.getTileWidth(), row * mapDefinition.getTileHeight());

                Rectangle tileBody = tile.getBody(tilePosition);

                if(this.mouseBody.overlaps(tileBody)) {
                    this.overlapping = true;
                    this.overlappingBody = new Rectangle(tileBody);

                    if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && this.mouseReleasedSinceTileSelection) {
                        this.placeTileOnCurrentLayer(tileIndex, this.placingTileId, tilePosition);

                        System.out.println("Set tile " + this.placingTileId);
                    }

                    if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
                        this.placingTileId = tileId;
                        System.out.println("Selected tile " + this.placingTileId + " at pos " + tilePosition.x + "/" + tilePosition.y);
                    }
                }

                if(liveUpdate) {
                    TileData tileData = editingLayer.getLayerTileData().get(tileIndex);

                    tile.update(this.map, camera, tilePosition, tileData);
                }
            }
        }
    }

    private void placeTileOnCurrentLayer(int tileIndex, int tileId, Vector2 position) {
        this.map.setTile(this.editingLayerIndex, tileIndex, tileId, position);
    }

    @Override
    public void resize(int width, int height) {
        this.map.resize(width, height);
    }

    public void updateTitle() {
        Gdx.graphics.setTitle("Layer " + this.editingLayerIndex + " Testing " + this.playtesting);
    }

    private void pollEditingInput(OrthographicCamera  camera) {
        if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            this.mouseReleasedSinceTileSelection = true;
        }

        this.updateAndPlaceTiles(camera);

        this.pollLayerSwitchInput();

        this.pollFileManagementInput();

        this.pollNavigationInput(camera);

        if(Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            this.map.getMapLayer(this.editingLayerIndex).fillWithType(this.placingTileId);
            this.map.regenerateTilemapFrameBuffer();

            System.out.println("Filled");
        }

    }

    private void pollNavigationInput(OrthographicCamera camera) {
        float speed = 120;
        float delta = Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.position.add(0, speed * delta, 0);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.position.add(0, -speed * delta, 0);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.position.add(speed * delta, 0, 0);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.position.add(-speed * delta, 0, 0);
        }
    }

    private void pollLayerSwitchInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            if (this.editingLayerIndex < this.map.getMapDefinition().getMapLayers() - 1) {
                this.editingLayerIndex++;
                Gdx.graphics.setTitle("Layer " + this.editingLayerIndex);
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            if (this.editingLayerIndex > 0) {
                this.editingLayerIndex--;
                Gdx.graphics.setTitle("Layer " + this.editingLayerIndex);
            }
        }
    }

    private void pollFileManagementInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            JFileChooser fileChooser = new JFileChooser();

            int selectedOption = fileChooser.showOpenDialog(null);

            if (selectedOption == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                this.map = MapImporter.getInstance().getMapFromFile(Gdx.files.internal("map/" + selectedFile.getName()), this.tileRegistry, new StateManager());

                System.out.println("Replaced map");
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            JFileChooser fileChooser = new JFileChooser();

            int selectedOption = fileChooser.showOpenDialog(null);

            if (selectedOption == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                String fileName = selectedFile.getName();

                MapExporter.getInstance().exportToFile(this.map, Gdx.files.local("core/assets/map/" + fileName));
            }
        }
    }

    private void pollEditorStateInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            this.selectingTile = !this.selectingTile;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            this.playtesting = !this.playtesting;
        }
    }

    private void updateMouseBody(OrthographicCamera camera) {
        Vector3 mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePosition);

        this.mouseBody.set(mousePosition.x, mousePosition.y, 0, 0);
    }

    private void renderTileSelectionBar(SpriteBatch batch, OrthographicCamera camera) {
        batch.setProjectionMatrix(this.menuCamera.combined);
        this.overlaySprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.overlaySprite.setPosition(0, 0);
        this.overlaySprite.setAlpha(0.2f);
        this.overlaySprite.draw(batch);

        int row = 0;
        int column = 0;

        int totalRows = this.tileRegistry.getSheetTileRows();

        int baseY = 100;
        int highestRowY = baseY + totalRows * this.tileRegistry.getTileHeight();

        for(int tileId = 0; tileId < this.tileRegistry.getTileIds().size(); tileId++) {
            Tile tile = this.tileRegistry.getTileById(tileId);

            Vector2 tileTypePosition = new Vector2( column * this.map.getMapDefinition().getTileWidth(), highestRowY - (row * this.map.getMapDefinition().getTileHeight()));

            tile.getSprite().setPosition(tileTypePosition.x, tileTypePosition.y);
            tile.getSprite().draw(batch);

            Rectangle tileBody = new Rectangle(tileTypePosition.x, tileTypePosition.y, this.map.getMapDefinition().getTileWidth(), this.map.getMapDefinition().getTileHeight());

            Vector3 testingMousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            this.menuCamera.unproject(testingMousePosition);

            Rectangle testingMouseBody = new Rectangle(testingMousePosition.x, testingMousePosition.y, 0, 0);

            if(testingMouseBody.overlaps(tileBody)) {
                if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    this.mouseReleasedSinceTileSelection = false;
                    this.selectingTile = false;
                    this.placingTileId = tileId;
                }

                this.overlaySprite.setPosition(tileTypePosition.x, tileTypePosition.y);
                this.overlaySprite.setSize(tileRegistry.getTileWidth(), tileRegistry.getTileHeight());
                this.overlaySprite.draw(batch);
            }

            column++;

            if(column >= this.tileRegistry.getSheetTileColumns()) {
                column = 0;
                row++;
            }
        }

        batch.setProjectionMatrix(camera.combined);
    }

}
