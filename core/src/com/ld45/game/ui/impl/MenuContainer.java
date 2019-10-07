package com.ld45.game.ui.impl;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ld45.game.state.StateManager;
import com.ld45.game.state.impl.StateMap;
import com.ld45.game.state.impl.StateMenu;
import com.ld45.game.ui.SkinType;
import com.ld45.game.ui.UiContainer;
import com.ld45.game.util.Screen;

public class MenuContainer extends UiContainer {

    public MenuContainer(final StateManager stateManager, final StateMap stateMap) {
        super(stateManager, SkinType.Sgx.SKIN);
        TextButton playButton = new TextButton("Play", this.getDefaultSkin());

        final StateManager manager = this.getStateManager();

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //manager.registerState("map", new StateMap(manager));
                //manager.setActiveState("map");
                stateMap.start();
            }
        });

        playButton.setSize(100, 40);
        playButton.setPosition(Screen.centerX(playButton.getWidth()), Screen.centerY(playButton.getHeight()));

        this.getPrimaryTable().addActor(playButton);

        Label titleLabel = new Label("Ludum Dare", SkinType.Arcade.SKIN);

        titleLabel.setPosition(Screen.centerX(titleLabel.getWidth()), playButton.getY() + titleLabel.getHeight() * 1.5f);

        this.getPrimaryTable().addActor(titleLabel);
    }

    @Override
    public void setup() {

    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        super.render(batch, camera);
    }

    @Override
    public void update(OrthographicCamera camera) {
        super.update(camera);
    }

}
