package com.ld45.game.entity.droppeditem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ld45.game.entity.Entity;
import com.ld45.game.entity.impl.BurstEntity;
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

    private float scaleMultiplier = 1;

    private boolean scalingUp = true;

    private int amount;

    public EntityDroppedItem(Vector2 position, Map parentMap, ItemType itemType, int amount) {
        super(position, parentMap, 0);
        this.itemType = itemType;
        this.setSprite(itemType.SPRITE);
        this.amount = amount;
    }

    public EntityDroppedItem(Vector2 position, Map parentMap, Sprite sprite) {
        super(position, parentMap, 0);
        this.setSprite(sprite);
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        this.getSprite().setRotation(this.rotation);
        this.getSprite().setScale(this.scale * this.scaleMultiplier);
        super.render(batch, camera);
        this.getSprite().setRotation(0);
        this.getSprite().setScale(1);
    }

    @Override
    public void update(OrthographicCamera camera) {
        super.update(camera);
        this.spin();

        EntityPlayer player = this.getParentMap().getEntityPlayer();

        if(this.getBody().overlaps(player.getBody())) {
            this.pickup(player);
        }
    }

    public void spin() {
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
    }

    public void pickup(EntityPlayer player) {
        player.getInventory().addItem(this.itemType, this.amount);
        this.getParentMap().despawnEntity(this);

        int totalParticles = 10;

        for(int particle = 0; particle < totalParticles; particle++) {
            BurstEntity burstEntity = new BurstEntity(new Vector2(this.getPosition()), this.getParentMap(), particle, totalParticles, 1f, 0.8f, 0.8f, this.itemType.SPRITE);

            this.getParentMap().spawnEntity(burstEntity);
        }

        player.getInventory().restoreHunger(this.itemType.HUNGER_RESTORATION);
    }

    public void setScaleMultiplier(float scaleMultiplier) {
        this.scaleMultiplier = scaleMultiplier;
    }

}
