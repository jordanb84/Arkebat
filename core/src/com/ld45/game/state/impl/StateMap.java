package com.ld45.game.state.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ld45.game.io.MapImporter;
import com.ld45.game.map.Map;
import com.ld45.game.map.MapDefinition;
import com.ld45.game.state.State;
import com.ld45.game.state.StateManager;
import com.ld45.game.tile.TileRegistry;
import com.ld45.game.ui.impl.HudContainer;
import com.ld45.game.ui.impl.MenuContainer;

public class StateMap extends State {

    private Map map;

    private MenuContainer menuContainer;

    private boolean menu = true;

    private HudContainer hudContainer;

    public StateMap(StateManager manager) {
        super(manager);
        this.menuContainer = new MenuContainer(manager, this);
    }

    @Override
    public void create() {
        this.hudContainer = new HudContainer(this.getManager());

        this.reset();

        this.hudContainer.create(this.map.getEntityPlayer().getInventory());

        this.map.setHudContainer(this.hudContainer);
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        if(this.menu) {
            this.menuContainer.render(batch, camera);
        } else {
            this.map.render(batch, camera, true);
        }
    }

    @Override
    public void update(OrthographicCamera camera) {
        if(this.menu) {
            this.menuContainer.update(camera);
        } else {
            this.map.update(camera);
        }
    }

    @Override
    public void resize(int width, int height) {
        this.map.resize(width, height);
        this.menuContainer.resize(width, height);
    }

    @Override
    public void reset() {
        super.reset();
        TileRegistry tileRegistry = new TileRegistry("tile/tiledata.data");

        this.map = MapImporter.getInstance().getMapFromFile(Gdx.files.internal("map/box34.map"), tileRegistry, this.getManager());

        this.map.setHudContainer(this.hudContainer);

        this.hudContainer.restart(this.map);

        this.hudContainer.setInventory(this.map.getEntityPlayer().getInventory());
    }

    public void start() {
        this.menu = false;
        Gdx.input.setInputProcessor(this.map.getHudContainer().getStage());
    }

}
