package com.ld45.game.tile;

import box2dLight.PointLight;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class TileData {

    private Body physicsBody;

    private PointLight light;

    public void updatePhysicsBody(World physicsWorld, Vector2 position, Tile tile) {
        if(this.physicsBody == null && tile.isSolid()) {
            this.setupPhysicsBody(physicsWorld, position, tile);
        }
    }

    private void setupPhysicsBody(World physicsWorld, Vector2 position, Tile tile) {
        float width = tile.getBoxSize().x;
        float height = tile.getBoxSize().y;

        BodyDef physicsBodyDefinition = new BodyDef();
        physicsBodyDefinition.position.set(new Vector2(position.x + width / 2, position.y + height / 2));

        this.physicsBody = physicsWorld.createBody(physicsBodyDefinition);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        this.physicsBody.createFixture(shape, 0.0f);

        shape.dispose();
    }

    public Body getPhysicsBody() {
        return this.physicsBody;
    }

    public void setLight(PointLight light) {
        this.light = light;
    }

    public PointLight getLight() {
        return this.light;
    }

}
