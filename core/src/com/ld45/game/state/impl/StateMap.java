package com.ld45.game.state.impl;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ld45.game.map.Map;
import com.ld45.game.state.State;
import com.ld45.game.state.StateManager;
import com.ld45.game.tile.TileRegistry;

public class StateMap extends State {

    private Map map;

    public StateMap(StateManager manager) {
        super(manager);
    }

    @Override
    public void create() {
        TileRegistry tileRegistry = new TileRegistry("tile/tiledata.data");


    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {

    }

    @Override
    public void update(OrthographicCamera camera) {

    }

    @Override
    public void resize(int width, int height) {

    }

}
