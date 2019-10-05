package com.ld45.game.entity.mind.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.ld45.game.entity.Direction;
import com.ld45.game.entity.living.LivingEntity;
import com.ld45.game.entity.living.impl.EntityPlayer;
import com.ld45.game.entity.mind.EntityMind;
import com.ld45.game.entity.mind.EntityMindState;
import com.ld45.game.entity.projectile.impl.EntityFoodProjectile;
import com.ld45.game.inventory.Inventory;
import com.ld45.game.inventory.InventoryCell;
import com.ld45.game.item.ItemType;

public class PlayerInputMindState extends EntityMindState {

    public PlayerInputMindState(EntityMind parentMind) {
        super(parentMind, "input");
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera, LivingEntity parentEntity) {

    }

    @Override
    public void update(OrthographicCamera camera, LivingEntity parentEntity) {
        camera.position.set(parentEntity.getPosition().x + parentEntity.getWidth() / 2, parentEntity.getPosition().y + parentEntity.getHeight() / 2, 0);
        camera.update();

        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            parentEntity.move(Direction.UP);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            parentEntity.move(Direction.DOWN);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            parentEntity.move(Direction.RIGHT);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            parentEntity.move(Direction.LEFT);
        }

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mouse);

            EntityPlayer player = ((EntityPlayer) parentEntity);

            Inventory inventory = player.getInventory();

            ItemType selectedItem = inventory.getSelectedItem();

            if(selectedItem != null && !inventory.isMouseOverCell()) {
                Vector2 projectilePosition = new Vector2(player.getPosition().x, player.getPosition().y);
                Vector2 foodDestination = new Vector2(mouse.x, mouse.y);

                EntityFoodProjectile foodProjectile = new EntityFoodProjectile(selectedItem, projectilePosition, player.getParentMap(), foodDestination);

                player.getParentMap().spawnEntity(foodProjectile);
            }

            //parentEntity.getParentMap().spawnEntity(new EntityFlameProjectile(new Vector2(parentEntity.getPosition().x, parentEntity.getPosition().y), parentEntity.getParentMap(), flameDestination));
        }
    }

    @Override
    public void onCollision(Direction collisionDirection, Vector2 collisionPosition) {

    }

}
