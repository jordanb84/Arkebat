package com.ld45.game.ui.impl;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.ld45.game.inventory.Inventory;
import com.ld45.game.inventory.InventoryCell;
import com.ld45.game.state.StateManager;
import com.ld45.game.ui.SkinType;
import com.ld45.game.ui.UiContainer;

public class HudContainer extends UiContainer {

    private Inventory inventory;

    public HudContainer(StateManager stateManager, Inventory inventory) {
        super(stateManager, SkinType.Sgx.SKIN);
        this.inventory = inventory;

        for(InventoryCell inventoryCell : this.inventory.getInventoryCells()) {
            Label amountDisplay = new Label("0", this.getDefaultSkin());

            inventoryCell.setAmountDisplay(amountDisplay);

            this.getPrimaryTable().bottom().add(inventoryCell);
            this.getPrimaryTable().add(amountDisplay);
        }
    }

    @Override
    public void setup() {
    }

}
