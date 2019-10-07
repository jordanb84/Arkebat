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

public class StateMap extends State {

    private Map map;

    //todo win/lose conditions
    //show transparent window with you win/you lose and "restarting" if you lose or a "replay" button

    public StateMap(StateManager manager) {
        super(manager);
    }

    @Override
    public void create() {
        this.reset();
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        this.map.render(batch, camera, true);
    }

    @Override
    public void update(OrthographicCamera camera) {
        this.map.update(camera);
    }

    @Override
    public void resize(int width, int height) {
        this.map.resize(width, height);
    }

    @Override
    public void reset() {
        super.reset();
        TileRegistry tileRegistry = new TileRegistry("tile/tiledata.data");

        this.map = MapImporter.getInstance().getMapFromFile(Gdx.files.internal("map/box27.map"), tileRegistry, this.getManager());
    }

}
