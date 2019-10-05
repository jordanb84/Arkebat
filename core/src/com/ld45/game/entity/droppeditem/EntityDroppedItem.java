package com.ld45.game.entity.droppeditem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ld45.game.entity.Entity;
import com.ld45.game.entity.living.impl.EntityPlayer;
import com.ld45.game.item.ItemType;
import com.ld45.game.map.Map;

public class EntityDroppedItem extends Entity {

    private ItemType itemType;

    private float rotation;
    private float rotationRate = 120;

    private float scale = 1;
    private float scaleRate = 0.6f;

    private float minScale = 0.9f;
    private float maxScale = 1.1f;

    private boolean scalingUp = true;

    public EntityDroppedItem(Vector2 position, Map parentMap, ItemType itemType) {
        super(position, parentMap, 0);
        this.itemType = itemType;
        this.setSprite(itemType.SPRITE);
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        this.getSprite().setRotation(this.rotation);
        this.getSprite().setScale(this.scale);
        super.render(batch, camera);
        this.getSprite().setRotation(0);
        this.getSprite().setScale(1);
    }

    @Override
    public void update(OrthographicCamera camera) {
        super.update(camera);
        this.rotation += this.rotationRate * Gdx.graphics.getDeltaTime();

        if(this.scalingUp) {
            this.scale += this.scaleRate * Gdx.graphics.getDeltaTime();

            if(this.scale >= this.maxScale) {
                this.scalingUp = false;
            }
        } else {
            this.scale -= this.scaleRate * Gdx.graphics.getDeltaTime();

            if(this.scale <= this.minScale) {
                this.scalingUp = true;
            }
        }

        EntityPlayer player = this.getParentMap().getEntityPlayer();

        if(this.getBody().overlaps(player.getBody())) {
            player.getInventory().addItem(this.itemType, 1);
            this.getParentMap().despawnEntity(this);
        }
    }

}
