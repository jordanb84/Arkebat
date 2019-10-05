package com.ld45.game.inventory;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.ld45.game.entity.living.impl.EntityPlayer;
import com.ld45.game.item.ItemType;
import com.ld45.game.ui.SkinType;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private int columns = 10;
    private int rows = 1;

    private List<InventoryCell> inventoryCells = new ArrayList<InventoryCell>();

    public Inventory(EntityPlayer player) {
        this.generate();

        this.addItem(ItemType.Cookie, 5);
    }

    private void generate() {
        for(int row = 0; row < this.rows; row++) {
            for(int column = 0; column < this.columns; column++) {
                InventoryCell emptyCell = new InventoryCell(SkinType.Sgx.SKIN);

                this.inventoryCells.add(emptyCell);
            }
        }
    }

    public void addItem(ItemType item, int amount) {
        if(this.hasItem(item)) {
            this.getFirstCellWithItem(item).increaseAmount(amount);
        } else {
            InventoryCell firstEmptyCell = this.getFirstEmptyCell();

            boolean hasEmptyCell = firstEmptyCell != null;

            if(hasEmptyCell) {
                firstEmptyCell.addItem(item, amount);
            }
        }
    }

    public InventoryCell getFirstCellWithItem(ItemType item) {
        for(InventoryCell inventoryCell : this.inventoryCells) {
            if(inventoryCell.getItem() == item) {
                return inventoryCell;
            }
        }

        return null;
    }

    public InventoryCell getFirstEmptyCell() {
        for(InventoryCell inventoryCell : this.inventoryCells) {
            if(inventoryCell.getItem() == null) {
                return inventoryCell;
            }
        }

        return null;
    }

    public boolean hasItem(ItemType item) {
        for(InventoryCell inventoryCell : this.inventoryCells) {
            if(inventoryCell.getItem() == item) {
                return true;
            }
        }

        return false;
    }

    public List<InventoryCell> getInventoryCells() {
        return this.inventoryCells;
    }

}
