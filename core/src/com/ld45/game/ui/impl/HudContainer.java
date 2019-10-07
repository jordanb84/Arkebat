package com.ld45.game.ui.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ld45.game.entity.living.impl.EntityPlayer;
import com.ld45.game.inventory.Inventory;
import com.ld45.game.inventory.InventoryCell;
import com.ld45.game.item.ItemType;
import com.ld45.game.map.Map;
import com.ld45.game.state.StateManager;
import com.ld45.game.state.impl.StateMap;
import com.ld45.game.ui.SkinType;
import com.ld45.game.ui.UiContainer;
import com.ld45.game.util.Screen;

import java.util.ArrayList;
import java.util.List;

public class HudContainer extends UiContainer {

    private Inventory inventory;

    private Map map;

    private Window gameOverWindow;

    private boolean showedRestart;

    private OrthographicCamera camera;

    private List<InventoryCell> inventoryCells = new ArrayList<>();

    public HudContainer(StateManager stateManager) {
        super(stateManager, SkinType.Sgx.SKIN);
    }

    public void create(Inventory inventory) {
        this.inventory = inventory;
        this.map = this.inventory.getPlayer().getParentMap();

        InfoWindow infoWindow = new InfoWindow(this.inventory);
        infoWindow.setPosition(0, Screen.SCREEN_HEIGHT / 2 - infoWindow.getHeight() / 2);

        this.getPrimaryTable().addActor(infoWindow);

        int columnsAdded = 0;

        /**
         * On reset, we need to pass these old inventory cells to the new player's
         * inventory, and clear them
         */

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

            this.inventoryCells.add(inventoryCell);
        }

        System.out.println("Cells available on init: " + this.inventoryCells.size());

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
                StateMap mapState = ((StateMap) getStateManager().getActiveState());

                mapState.die();
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

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Screen.SCREEN_WIDTH, Screen.SCREEN_HEIGHT);

        this.getPrimaryTable().addActor(this.gameOverWindow);
        this.gameOverWindow.setVisible(false);

        this.gameOverWindow.setMovable(false);
    }

    @Override
    public void setup() {

    }

    @Override
    public void update(OrthographicCamera camera) {
        super.update(camera);
        if(this.map.isGameOver() && !this.showedRestart) {
            this.showedRestart = true;
            this.gameOverWindow.setVisible(true);
            this.gameOverWindow.getTitleLabel().setText("You lost!");
        }

        if(this.map.isGameWon() && !this.showedRestart) {
            this.gameOverWindow.getTitleLabel().setText("You won!");
            this.showedRestart = true;
            this.gameOverWindow.setVisible(true);
        }

    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        batch.setProjectionMatrix(this.camera.combined);
        super.render(batch, camera);
        batch.setProjectionMatrix(camera.combined);
    }

    public void setInventory(Inventory inventory) {
        /**System.out.println("Cell count " + this.inventoryCells.size() + " new cell count " + inventory.getInventoryCells().size());
        this.inventory = inventory;
        this.inventoryCells = this.inventory.getInventoryCells();

        System.out.println("C cells: " + this.inventoryCells.size());

        if(this.inventory.getInventoryCells().size() > 0) {
            //this.inventory.getInventoryCells().clear();
        }

        int addedCells = 0;
        for(InventoryCell inventoryCell : this.inventoryCells) {
            inventoryCell.setItem(null, 0);
            //this.inventory.getInventoryCells().add(inventoryCell);

            addedCells++;
        }

        System.out.println("Added " + addedCells + " cells");**/

        this.inventory = inventory;

        /**
         * On death, and ONLY on death, not on normal init,
         * we need to take every inventoryCell in the new inventory instance
         * and replace it with the original inventory cells held here in HudContainer
         */
    }

    public void restart(Map map) {
        this.showedRestart = false;

        if(this.gameOverWindow != null) {
            this.gameOverWindow.setVisible(false);
            System.out.println("Set visible to false");
        }

        this.map = map;
    }

    public List<InventoryCell> getInventoryCells() {
        return this.inventoryCells;
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
