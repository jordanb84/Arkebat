package com.ld45.game.entity.projectile;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ld45.game.animation.Animation;
import com.ld45.game.entity.Direction;
import com.ld45.game.entity.Entity;
import com.ld45.game.map.Map;

public abstract class EntityProjectile extends Entity {

    private Animation animation;

    private Vector2 destination;
    private Rectangle destinationBody;

    public EntityProjectile(Vector2 position, Map parentMap, Vector2 destination, float speed) {
        super(position, parentMap, 0);
        this.animation = this.setupAnimation();
        this.destination = destination;
        this.destinationBody = new Rectangle(destination.x, destination.y, this.animation.getCurrentWidth(), this.animation.getCurrentHeight());
        this.setSpeed(speed, speed);
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        this.animation.render(batch, this.getPosition(), 1, (float) this.getRotationTowardPosition(this.destination));
    }

    @Override
    public void update(OrthographicCamera camera) {
        super.update(camera);
        this.destinationBody.setSize(this.animation.getCurrentWidth(), this.animation.getCurrentHeight());
        this.moveTowardDestination();

        this.setSprite(this.animation.getCurrentFrame().getSprite());

        this.animation.update();

        if(this.getBody().overlaps(this.destinationBody)) {
            this.getParentMap().despawnEntity(this);
        }
    }

    private void moveTowardDestination() {
        if(this.getPosition().x < this.destinationBody.x) {
            this.move(Direction.RIGHT);
        }

        if(this.getPosition().x > this.destinationBody.x) {
            this.move(Direction.LEFT);
        }

        if(this.getPosition().y < this.destinationBody.y) {
            this.move(Direction.UP);
        }

        if(this.getPosition().y > this.destinationBody.y) {
            this.move(Direction.DOWN);
        }
    }

    public abstract Animation setupAnimation();

    @Override
    public void updateBody() {
        this.getBody(false).set(this.getPosition().x, this.getPosition().y, this.animation.getCurrentWidth(), this.animation.getCurrentHeight());
    }

}
