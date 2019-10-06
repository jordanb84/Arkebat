package com.ld45.game.entity.impl;

import com.badlogic.gdx.math.Vector2;
import com.ld45.game.assets.Assets;
import com.ld45.game.entity.droppeditem.EntityDroppedItem;
import com.ld45.game.entity.living.impl.EntityPlayer;
import com.ld45.game.map.Map;

import java.util.Random;

public class HealEntity extends EntityDroppedItem {

    private int healRate = 3;

    private Random healRandom = new Random();

    public HealEntity(Vector2 position, Map parentMap) {
        super(position, parentMap, Assets.getInstance().getSprite("entity/heal.png"));
        this.setScaleMultiplier(0.6f);
    }

    @Override
    public void pickup(EntityPlayer player) {
        this.getParentMap().despawnEntity(this);
        player.setHealth(player.getHealth() + this.healRate + this.healRandom.nextInt(1));
    }

}
