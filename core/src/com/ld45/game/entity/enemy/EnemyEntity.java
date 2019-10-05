package com.ld45.game.entity.enemy;

import com.badlogic.gdx.math.Vector2;
import com.ld45.game.entity.living.LivingEntity;
import com.ld45.game.map.Map;

public abstract class EnemyEntity extends LivingEntity {

    public EnemyEntity(Vector2 position, Map parentMap, float weight) {
        super(position, parentMap, weight);
    }

}
