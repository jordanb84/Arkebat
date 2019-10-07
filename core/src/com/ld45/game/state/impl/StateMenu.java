package com.ld45.game.state.impl;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ld45.game.assets.Assets;
import com.ld45.game.state.State;
import com.ld45.game.state.StateManager;
import com.ld45.game.ui.impl.MenuContainer;

public class StateMenu extends State {

    private MenuContainer menuContainer;

    private Sprite background;

    public StateMenu(StateManager stateManager) {
        super(stateManager);
        this.background = Assets.getInstance().getSprite("ui/menu.png");
    }

    @Override
    public void create() {
        //this.menuContainer = new MenuContainer(this.getManager());
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        this.background.setPosition(0, 0);
        this.background.draw(batch);
        this.menuContainer.render(batch, camera);
    }

    @Override
    public void update(OrthographicCamera camera) {
        this.menuContainer.update(camera);
    }

    @Override
    public void resize(int width, int height) {
        this.menuContainer.resize(width, height);
    }

}
