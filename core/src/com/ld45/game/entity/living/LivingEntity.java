package com.ld45.game.entity.living;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.ld45.game.entity.AnimatedEntity;
import com.ld45.game.entity.mind.EntityMind;
import com.ld45.game.map.Map;

public abstract class LivingEntity extends AnimatedEntity {

    private EntityMind mind;

    private Body physicsBody;

    public LivingEntity(Vector2 position, Map parentMap, float weight) {
        super(position, parentMap, weight);
        this.mind = this.setupMind();
    }

    public void addPhysicsBody() {
        BodyDef bodyDefinition = new BodyDef();
        bodyDefinition.type = BodyDef.BodyType.DynamicBody;
        bodyDefinition.position.set(this.getPosition().x, this.getPosition().y);

        this.physicsBody = this.getParentMap().getPhysicsWorld().createBody(bodyDefinition);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(this.getSprite().getWidth() / 2, this.getSprite().getHeight() / 2);

        FixtureDef fixtureDefinition = new FixtureDef();
        fixtureDefinition.shape = shape;
        fixtureDefinition.density = 0.6f;
        fixtureDefinition.friction = 0.3f;
        fixtureDefinition.restitution = 0.2f;

        Fixture fixture = this.physicsBody.createFixture(fixtureDefinition);

        shape.dispose();
    }

    public void updatePhysicsBody() {
        this.physicsBody.setTransform(this.getPosition().x + this.getWidth() / 2, this.getPosition().y + this.getHeight() / 2, 0);
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        super.render(batch, camera);
        this.mind.render(batch, camera);
    }

    @Override
    public void update(OrthographicCamera camera) {
        super.update(camera);
        this.mind.update(camera);

        if(this.hasPhysicsBody()) {
            this.updatePhysicsBody();
        }
    }

    public abstract EntityMind setupMind();

    public EntityMind getMind() {
        return this.mind;
    }

    public boolean hasPhysicsBody() {
        return this.physicsBody != null;
    }

}
