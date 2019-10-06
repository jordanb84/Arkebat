package com.ld45.game.inventory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ld45.game.item.ItemType;
import com.ld45.game.ui.SkinType;

public class InventoryCell extends ImageButton {

    private ItemType item;

    private int amount;

    private Label amountDisplay;

    private float alpha = 1f;

    private boolean selected;

    private TextTooltip tooltip;

    private Inventory parentInventory;

    private int cellIndex;

    public InventoryCell(final Inventory parentInventory, final int cellIndex, Skin skin) {
        super(skin);
        this.parentInventory = parentInventory;
        this.cellIndex = cellIndex;

        this.tooltip = new TextTooltip("", SkinType.Arcade.SKIN);
        this.tooltip.setInstant(true);
        this.tooltip.getActor().setColor(Color.WHITE);
        this.tooltip.getActor().setWrap(false);
        this.addListener(this.tooltip);

        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                parentInventory.setSelectedCell(cellIndex);
            }
        });
    }

    public void increaseAmount(int amount) {
        this.amount += amount;
    }

    public void addItem(ItemType item, int amount) {
        if(this.hasItem()) {
            if(item == this.item) {
                this.amount += amount;
            }
        } else {
            this.item = item;
            this.amount = amount;
        }

        this.updateTooltip();
    }

    public void setItem(ItemType item, int amount) {
        this.item = item;
        this.amount = amount;

        if(item != null) {
            this.updateTooltip();;
        }
    }

    public void updateTooltip() {
        String updatedTooltipText = (item.DISPLAY_NAME + "\n- " + item.DESCRIPTION + "\n(" + item.DAMAGE + " damage)");
        this.tooltip.getActor().setText(updatedTooltipText);
    }

    private void updateAmountDisplay() {
        this.amountDisplay.setText("" + this.amount);
    }

    public boolean hasItem() {
        return this.item != null;
    }

    public ItemType getItem() {
        return this.item;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(this.isSelected()) {
            this.getImage().setColor(Color.GREEN);
        }

        float alpha = this.isSelected() ? 1 : 0.6f;

        super.draw(batch, alpha);

        this.getImage().setColor(Color.WHITE);

        if(this.hasItem()) {
            Sprite sprite = this.getItem().UI_SPRITE;

            sprite.setAlpha(this.alpha);
            sprite.setPosition(this.getX() + sprite.getWidth() / 5f, this.getY() + sprite.getHeight() / 5f);

            if(this.isSelected()) {
                sprite.setAlpha(1);
                //sprite.setColor(Color.GREEN);
            }

            sprite.draw(batch);

            sprite.setColor(Color.WHITE);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.amountDisplay.setVisible(this.hasItem());

        this.updateAmountDisplay();

        //this.setDisabled(!selected);

        if(this.isOver()) {
            this.parentInventory.setMouseOverCell(true);
        }
    }

    public void setAmountDisplay(Label amountDisplay) {
        this.amountDisplay = amountDisplay;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public int getCellIndex() {
        return this.cellIndex;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
