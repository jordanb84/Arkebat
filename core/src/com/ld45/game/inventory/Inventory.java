package com.ld45.game.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.ld45.game.entity.living.impl.EntityPlayer;
import com.ld45.game.item.ItemType;
import com.ld45.game.ui.SkinType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Inventory {

    private int columns = 9;
    private int rows = 1;

    private List<InventoryCell> inventoryCells = new ArrayList<InventoryCell>();

    private int selectedIndex;

    private boolean mouseOverCell;

    private HashMap<Integer, Integer> selectionInputs = new HashMap<Integer, Integer>();

    public Inventory(EntityPlayer player) {
        this.generate();

        this.addItem(ItemType.Cookie, 5);

        this.selectionInputs.put(Input.Keys.NUM_1, 0);
        this.selectionInputs.put(Input.Keys.NUM_2, 1);
        this.selectionInputs.put(Input.Keys.NUM_3, 2);
        this.selectionInputs.put(Input.Keys.NUM_4, 3);
        this.selectionInputs.put(Input.Keys.NUM_5, 4);
        this.selectionInputs.put(Input.Keys.NUM_6, 5);
        this.selectionInputs.put(Input.Keys.NUM_7, 6);
        this.selectionInputs.put(Input.Keys.NUM_8, 7);
        this.selectionInputs.put(Input.Keys.NUM_9, 8);
    }

    public void update(OrthographicCamera camera) {
        this.setMouseOverCell(false);
        this.getSelectedCell().setSelected(true);

        for(HashMap.Entry<Integer, Integer> selectionInput : this.selectionInputs.entrySet()) {
            if(Gdx.input.isKeyPressed(selectionInput.getKey())) {
                this.setSelectedCell(selectionInput.getValue());
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.U)) {
            this.moveDown();
        }
    }

    private void generate() {
        int cellIndex = 0;

        for(int row = 0; row < this.rows; row++) {
            for(int column = 0; column < this.columns; column++) {
                InventoryCell emptyCell = new InventoryCell(this, cellIndex, SkinType.Sgx.SKIN);

                this.inventoryCells.add(emptyCell);

                cellIndex++;
            }
        }
    }

    public void addItem(ItemType item, int amount) {
        if(!this.hasItem(item)) {
            this.moveDown();
        }

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

    public void removeItem(ItemType item, int amount) {
        InventoryCell itemCell = this.getFirstCellWithItem(item);

        if(itemCell != null) {
            itemCell.setAmount(itemCell.getAmount() - amount);

            if(itemCell.getAmount() <= 0) {
                itemCell.setItem(null, 0);
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

    public void moveDown() {
        /**
         * Start at the last cell with an item in it
         *
         * Set its item to nothing
         *
         * Get slot at next index and set it to the original item
         */

        if(this.getInventoryCells().get(0).hasItem()) {
            InventoryCell lastCell = this.getLastCellWithItem();

            int lastCellIndex = lastCell.getCellIndex();

            int currentCellIndex = lastCellIndex;

            if (lastCell != null) {
                boolean finishedMoving = false;

                while (!finishedMoving) {
                    if (currentCellIndex == 0) {
                        finishedMoving = true;
                    }

                    InventoryCell currentCell = this.inventoryCells.get(currentCellIndex);

                    ItemType originalItem = currentCell.getItem();
                    int originalAmount = currentCell.getAmount();

                    currentCell.setItem(null, 0);

                    this.getInventoryCells().get(currentCellIndex + 1).setItem(originalItem, originalAmount);

                    currentCellIndex--;
                }
            }
        }
    }

    //lastCellIndex should start at the last item-having cell before a blank one, not the last in total
    //(in case there's a space)

    public InventoryCell getLastCellWithItem() {
        int lastCellIndex = 0;

        for(InventoryCell inventoryCell : this.inventoryCells) {
            if(inventoryCell.getItem() != null) {
                lastCellIndex = inventoryCell.getCellIndex();
            }
        }

        return this.inventoryCells.get(lastCellIndex);
    }

    public InventoryCell getLastCellWithItemBeforeBlank() {
        int lastCellIndex = 0;

        for(InventoryCell inventoryCell : this.inventoryCells) {
            if(inventoryCell.getItem() != null) {
                lastCellIndex = inventoryCell.getCellIndex();

                //check if the next one is empty, if so, just return this one
                boolean nextEmpty = false;

                if(inventoryCell.getCellIndex() == this.getInventoryCells().size()) { //might be an off by 1 (index out of bounds by 1)
                    nextEmpty = true;
                } else {
                    if(!this.getInventoryCells().get(inventoryCell.getCellIndex() + 1).hasItem()) {
                        nextEmpty = true;
                    }
                }

                if(nextEmpty) {
                    return inventoryCell;
                }
            }
        }

        return this.inventoryCells.get(lastCellIndex);
    }

    public List<InventoryCell> getInventoryCells() {
        return this.inventoryCells;
    }

    public InventoryCell getSelectedCell() {
        return this.inventoryCells.get(this.selectedIndex);
    }

    public void setSelectedCell(int cellIndex) {
        this.getSelectedCell().setSelected(false);

        this.selectedIndex = cellIndex;
    }

    public ItemType getSelectedItem() {
        if(this.getSelectedCell() != null) {
            return this.getSelectedCell().getItem();
        }

        return null;
    }

    public boolean isMouseOverCell() {
        return this.mouseOverCell;
    }

    public void setMouseOverCell(boolean mouseOverCell) {
        this.mouseOverCell = mouseOverCell;
    }

}
