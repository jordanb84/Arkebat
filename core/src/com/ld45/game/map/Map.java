package com.ld45.game.map;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.ld45.game.entity.Entity;
import com.ld45.game.entity.droppeditem.EntityDroppedItem;
import com.ld45.game.entity.enemy.impl.EntityImp;
import com.ld45.game.entity.living.impl.EntityPlayer;
import com.ld45.game.item.ItemType;
import com.ld45.game.state.StateManager;
import com.ld45.game.tile.Tile;
import com.ld45.game.tile.TileRegistry;
import com.ld45.game.ui.impl.HudContainer;

import java.util.ArrayList;
import java.util.List;

public class Map {

    private MapDefinition mapDefinition;

    private List<MapLayer> mapLayers;

    private List<Entity> entities = new ArrayList<Entity>();
    private List<Entity> entitySpawnQueue = new ArrayList<Entity>();
    private List<Entity> entityDespawnQueue = new ArrayList<Entity>();

    private float worldFriction = 2;

    private World world;
    private Box2DDebugRenderer debugRenderer;

    private RayHandler rayHandler;
    private int lightRayCount = 100;

    private boolean enableLights = true;

    private TextureRegion tileMapFbo;

    private EntityPlayer player;

    private StateManager stateManager;

    private HudContainer hudContainer;

    public Map(MapDefinition mapDefinition, List<MapLayer> mapLayers, StateManager stateManager) {
        this.mapDefinition = mapDefinition;
        this.mapLayers = mapLayers;
        this.stateManager = stateManager;

        this.setup();
    }

    public Map(MapDefinition mapDefinition, int groundType, TileRegistry tileRegistry, StateManager stateManager) {
        this.mapDefinition = mapDefinition;
        this.mapLayers = new ArrayList<MapLayer>();
        this.stateManager = stateManager;

        for(int layersGenerated = 0; layersGenerated < this.mapDefinition.getMapLayers(); layersGenerated++) {
            if(layersGenerated == 0) {
                this.mapLayers.add(new MapLayer(this.mapDefinition, groundType, tileRegistry));
            } else {
                this.mapLayers.add(new MapLayer(this.mapDefinition, tileRegistry.getTileIdByName("air"), tileRegistry));
            }
        }

        this.setup();
    }

    private void setup() {
        this.setupPhysicsWorld();

        this.rayHandler = new RayHandler(this.world);
        this.rayHandler.setAmbientLight(Color.GRAY);

        RayHandler.useDiffuseLight(true);

        this.spawnInitialEntities();

        this.regenerateTilemapFrameBuffer();

        this.initiateTiles();

        this.hudContainer = new HudContainer(this.stateManager, this.getEntityPlayer().getInventory());
    }

    public void regenerateTilemapFrameBuffer() {
        int fboPixelsWidth = this.mapDefinition.getMapWidth() * this.mapDefinition.getTileWidth();
        int fboPixelsHeight = this.mapDefinition.getMapHeight() * this.mapDefinition.getTileHeight();

        FrameBuffer frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, fboPixelsWidth, fboPixelsHeight, false);

        SpriteBatch spriteBatch = new SpriteBatch();

        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, fboPixelsWidth, fboPixelsHeight);

        Gdx.gl.glClearColor(0, 0,0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        frameBuffer.begin();

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        for(MapLayer mapLayer : this.mapLayers) {
            mapLayer.render(this, spriteBatch, camera);
        }

        spriteBatch.end();
        frameBuffer.end();

        frameBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        this.tileMapFbo = new TextureRegion(frameBuffer.getColorBufferTexture());
        this.tileMapFbo.flip(false, true);
    }

    private void spawnInitialEntities() {
        Vector2 startingPosition = new Vector2(160, 1088);
        EntityPlayer player = new EntityPlayer(startingPosition, this);

        this.spawnEntity(new EntityDroppedItem(new Vector2(startingPosition.x + 64, startingPosition.y + 32), this, ItemType.Cookie));
        this.spawnEntity(new EntityDroppedItem(new Vector2(startingPosition.x + 64, startingPosition.y + 64), this, ItemType.Bacon));
        this.spawnEntity(new EntityDroppedItem(new Vector2(startingPosition.x + 64, startingPosition.y + 128), this, ItemType.Bacon));

        this.spawnEntity(player);
        this.setPlayer(player);

        this.spawnEntity(new EntityImp(new Vector2(startingPosition.x + 32, startingPosition.y + 32), this));
    }

    private void initiateTiles() {
        for(MapLayer mapLayer : this.mapLayers) {
            mapLayer.initiateTiles(this);
        }
    }

    private void setupPhysicsWorld() {
        this.world = new World(new Vector2(0, -2), true);

        this.debugRenderer = new Box2DDebugRenderer();
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        batch.draw(this.tileMapFbo, 0, 0);

        for(Entity entity : this.getEntities()) {
            entity.render(batch, camera);
        }

        if(this.enableLights) {
            batch.end();
            this.rayHandler.setCombinedMatrix(camera);
            this.rayHandler.updateAndRender();
            batch.begin();
        }

        this.hudContainer.render(batch, camera);
    }

    public void update(OrthographicCamera camera) {
        this.world.step(1/60f, 6, 2);

        for(MapLayer mapLayer : this.mapLayers) {
            mapLayer.update(this, camera);
        }

        this.entities.addAll(this.entitySpawnQueue);
        this.entitySpawnQueue.clear();

        this.entities.removeAll(this.entityDespawnQueue);
        this.entityDespawnQueue.clear();

        for(Entity entity : this.getEntities()) {
            entity.update(camera);
            this.applyEntityFriction(entity);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            camera.setToOrtho(false, Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 4);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        this.hudContainer.update(camera);
    }

    public void spawnEntity(Entity entity) {
        this.entitySpawnQueue.add(entity);
    }

    public void despawnEntity(Entity entity) {
        this.entityDespawnQueue.add(entity);
    }

    public List<Entity> getEntities() {
        return this.entities;
    }

    public void setTile(int layerIndex, int tileIndex, int tileId, Vector2 position) {
        Tile oldTile = this.getMapLayers().get(layerIndex).getLayerTile(tileIndex);

        oldTile.destroyInWorld(this, position);

        this.getMapLayers().get(layerIndex).getLayerTiles().set(tileIndex, tileId);

        Tile newTile = this.getMapLayers().get(layerIndex).getLayerTile(tileIndex);

        newTile.initiateInWorld(this, position);

        this.regenerateTilemapFrameBuffer();
    }

    public boolean collisionAt(Rectangle body) {
        for(MapLayer mapLayer : this.mapLayers) {
            if(mapLayer.collisionAt(body)) {
                return true;
            }
        }

        return false;
    }

    public void applyEntityFriction(Entity entity) {
        if(entity.getVelocity().x > 0) {
            entity.getVelocity().x -= this.worldFriction;
        }

        if(entity.getVelocity().x < 0) {
            entity.getVelocity().x += this.worldFriction;
        }

        if(entity.getVelocity().y > 0) {
            entity.getVelocity().y -= this.worldFriction;
        }

        if(entity.getVelocity().y < 0) {
            entity.getVelocity().y += this.worldFriction;
        }
    }

    public MapDefinition getMapDefinition() {
        return this.mapDefinition;
    }

    public MapLayer getMapLayer(int index) {
        return this.mapLayers.get(index);
    }

    public List<MapLayer> getMapLayers() {
        return this.mapLayers;
    }

    public World getPhysicsWorld() {
        return this.world;
    }

    public RayHandler getRayHandler() {
        return this.rayHandler;
    }

    public int getLightRayCount() {
        return this.lightRayCount;
    }

    public EntityPlayer getEntityPlayer() {
        return this.player;
    }

    public void setPlayer(EntityPlayer player) {
        this.player = player;
    }

}
