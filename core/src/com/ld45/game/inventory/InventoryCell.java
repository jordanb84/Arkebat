package com.ld45.game.inventory;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.ld45.game.item.ItemType;
import com.ld45.game.ui.SkinType;

public class InventoryCell extends ImageButton {

    private ItemType item;

    private int amount;

    private Label amountDisplay;

    private float alpha = 0.8f;

    public InventoryCell(Skin skin) {
        super(skin);
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
    }

    private void updateAmountDisplay() {
        this.amountDisplay.setText("" + this.amount);
    }

    private boolean hasItem() {
        return this.item != null;
    }

    public ItemType getItem() {
        return this.item;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if(this.hasItem()) {
            Sprite sprite = this.getItem().UI_SPRITE;

            sprite.setAlpha(this.alpha);
            sprite.setPosition(this.getX() + sprite.getWidth() / 5f, this.getY() + sprite.getHeight() / 5f);

            sprite.draw(batch);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.amountDisplay.setVisible(this.hasItem());
        this.updateAmountDisplay();
    }

    public void setAmountDisplay(Label amountDisplay) {
        this.amountDisplay = amountDisplay;
    }

}
