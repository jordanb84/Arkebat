package com.ld45.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.ld45.game.state.StateManager;

public abstract class UiContainer {

    private Stage stage;

    private Table primaryTable;

    private Skin defaultSkin;

    private StateManager stateManager;

    public UiContainer(StateManager stateManager, Skin defaultSkin) {
        this.stateManager = stateManager;
        this.defaultSkin = defaultSkin;

        this.stage = new Stage();

        this.primaryTable = new Table();
        this.primaryTable.setFillParent(true);

        this.setup();

        this.stage.addActor(this.primaryTable);

        Gdx.input.setInputProcessor(this.stage);
    }

    public abstract void setup();

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        batch.end();
        this.stage.draw();
        batch.begin();
    }

    public void update(OrthographicCamera camera) {
        this.stage.act();
    }

    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height);
    }

    public Table getPrimaryTable() {
        return this.primaryTable;
    }

    public Skin getDefaultSkin() {
        return this.defaultSkin;
    }

    public StateManager getStateManager() {
        return this.stateManager;
    }

}
