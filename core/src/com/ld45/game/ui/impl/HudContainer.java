package com.ld45.game.ui.impl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ld45.game.entity.living.impl.EntityPlayer;
import com.ld45.game.inventory.Inventory;
import com.ld45.game.inventory.InventoryCell;
import com.ld45.game.item.ItemType;
import com.ld45.game.map.Map;
import com.ld45.game.state.StateManager;
import com.ld45.game.ui.SkinType;
import com.ld45.game.ui.UiContainer;
import com.ld45.game.util.Screen;

public class HudContainer extends UiContainer {

    private Inventory inventory;

    private Map map;

    private Window gameOverWindow;

    private boolean showedRestart;

    public HudContainer(StateManager stateManager, Inventory inventory) {
        super(stateManager, SkinType.Sgx.SKIN);
        this.inventory = inventory;
        this.map = this.inventory.getPlayer().getParentMap();

        InfoWindow infoWindow = new InfoWindow(this.inventory);
        infoWindow.setPosition(0, Screen.SCREEN_HEIGHT / 2 - infoWindow.getHeight() / 2);

        this.getPrimaryTable().addActor(infoWindow);

        int columnsAdded = 0;

        for(InventoryCell inventoryCell : this.inventory.getInventoryCells()) {
            Label amountDisplay = new Label("0", this.getDefaultSkin());

            inventoryCell.setAmountDisplay(amountDisplay);

            this.getPrimaryTable().bottom().add(inventoryCell).width(64).height(64);
            this.getPrimaryTable().add(amountDisplay);

            columnsAdded++;

            if(columnsAdded >= this.inventory.getColumns()) {
                this.getPrimaryTable().row();
                columnsAdded = 0;
            }
        }

        this.getPrimaryTable().padBottom(32);

        Color transparentColor = new Color(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, 0.8f);

        this.gameOverWindow = new Window("You died!", this.getDefaultSkin());
        this.gameOverWindow.setSize(300, 240);

        this.gameOverWindow.setColor(transparentColor);

        TextButton restartButton = new TextButton("Restart", this.getDefaultSkin());
        restartButton.setColor(transparentColor);

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                map.reset();
            }
        });

        TextButton exitButton = new TextButton("Exit", this.getDefaultSkin());
        restartButton.setColor(transparentColor);

        exitButton.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.exit(0);
            }
        });

        restartButton.setSize(120, 60);
        exitButton.setSize(restartButton.getWidth(), restartButton.getHeight());

        float centerX = this.gameOverWindow.getWidth() / 2 - restartButton.getWidth() / 2;
        float centerY = this.gameOverWindow.getHeight() / 2 - restartButton.getHeight();

        restartButton.setPosition(centerX, centerY + restartButton.getHeight());
        exitButton.setPosition(restartButton.getX(), centerY - exitButton.getHeight() / 2);

        this.gameOverWindow.addActor(restartButton);
        this.gameOverWindow.addActor(exitButton);

        this.gameOverWindow.setPosition(Screen.SCREEN_WIDTH / 2 - this.gameOverWindow.getWidth() / 2, Screen.SCREEN_HEIGHT / 2 - this.gameOverWindow.getHeight() / 2);
    }

    @Override
    public void setup() {

    }

    @Override
    public void update(OrthographicCamera camera) {
        super.update(camera);
        if(this.map.isGameOver() && !this.showedRestart) {
            this.getPrimaryTable().addActor(this.gameOverWindow);
            this.showedRestart = true;
        }
    }

}

class InfoWindow extends Window {

    private Inventory inventory;

    private Label healthDisplay;

    private Label hungerDisplay;

    private Label collectedFoodsDisplay;

    private EntityPlayer player;

    //redo positioning each frame

    private Label healthCount;

    private Label hungerCount;

    private Label collectedFoodsCount;

    public InfoWindow(Inventory inventory) {
        super("Status", SkinType.Sgx.SKIN);
        this.inventory = inventory;
        this.setMovable(false);

        this.setSize(300, 400);

        Skin textSkin = SkinType.Arcade.SKIN;

        this.healthDisplay = new Label("Health", textSkin);
        this.hungerDisplay = new Label("Hunger", textSkin);
        this.collectedFoodsDisplay = new Label("Food Types", textSkin);
        this.healthCount = new Label("", textSkin);
        this.hungerCount = new Label("", textSkin);
        this.collectedFoodsCount = new Label("", textSkin);

        this.player = this.inventory.getPlayer();

        this.center();

        this.add(this.collectedFoodsDisplay);
        this.row();
        this.add(this.collectedFoodsCount);
        this.row();
        this.add(this.healthDisplay);
        this.row();
        this.add(this.healthCount);
        this.row();
        this.add(this.hungerDisplay);
        this.row();
        this.add(this.hungerCount);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, 0.8f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        int playerHealth = (int) this.player.getHealth();
        int playerMaxHealth = (int) this.player.getMaxHealth();

        if(playerHealth < 0) {
            playerHealth = 0;
        }

        int hunger = this.inventory.getHungerRemaining();
        int hungerMax = this.inventory.getHungerMax();

        this.healthCount.setText(playerHealth + "/" + playerMaxHealth);

        this.hungerCount.setText(hunger + "/" + hungerMax);

        int collectedFoods = this.inventory.getCollectedFoods().size();
        int totalFoods = ItemType.values().length;

        this.collectedFoodsCount.setText(collectedFoods + "/" + totalFoods);
    }

}
