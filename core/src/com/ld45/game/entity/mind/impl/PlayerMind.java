package com.ld45.game.entity.mind.impl;

import com.ld45.game.entity.living.LivingEntity;
import com.ld45.game.entity.mind.EntityMind;

public class PlayerMind extends EntityMind {

    private PlayerInputMindState playerInputMindState;

    public PlayerMind(LivingEntity parentEntity) {
        super(parentEntity);
        this.playerInputMindState = new PlayerInputMindState(this, "input");
        this.registerState(this.playerInputMindState);
        this.setState("input");
    }

    @Override
    public void recalculateState(LivingEntity parentEntity) {
        this.setState("input");
    }

}
