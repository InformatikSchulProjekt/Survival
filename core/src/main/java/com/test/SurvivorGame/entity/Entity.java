package com.test.SurvivorGame.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity extends GameObject {

    // rendering-relevante eigenschaften
    protected Animation<TextureRegion> currentAnimation;
    protected float animationTime = 0f;
    protected Direction facingDirection = Direction.DOWN;

    // bewegung
    protected final Vector2 moveDirection = new Vector2();
    protected float movementSpeed = 5f;
    protected boolean isMoving = false;

    // status
    protected float maxHP;
    protected float currentHP;
    protected boolean alive = true;

    public Entity(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    // abstrakte Methoden für Subklassen
    public abstract void update(float deltaTime);
    //public abstract Animation<TextureRegion> getIdleAnimation();
    //public abstract Animation<TextureRegion> getMovingAnimation(Direction direction);

    // rendering methoden
//    public void render(Batch batch, float deltaTime) {
//        updateAnimationTime(deltaTime);
//        Animation<TextureRegion> animation = selectAnimation();
//        TextureRegion frame = animation.getKeyFrame(animationTime);
//
//        batch.draw(
//            frame,
//            getX(),
//            getY(),
//            getWidth(),
//            getHeight()
//        );
//    }

//    protected Animation<TextureRegion> selectAnimation() {
//        if (!isMoving()) {
//            return getIdleAnimation();
//        } else {
//            return getMovingAnimation(facingDirection);
//        }
//    }

    protected void updateAnimationTime(float deltaTime) {
        animationTime += deltaTime;
    }

    // getter
    public Direction getFacingDirection() {
        return facingDirection;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public float getCurrentHP() {
        return currentHP;
    }

    public float getMaxHP() {
        return maxHP;
    }

    public boolean isAlive() {
        return alive;
    }

    public Vector2 getMoveDirection() {
        return moveDirection;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

    // setter
    public void setFacingDirection(Direction direction) {
        this.facingDirection = direction;
    }

    public void setMoving(boolean moving) {
        this.isMoving = moving;
    }

    public void setMovementSpeed(float speed) {
        this.movementSpeed = speed;
    }

    // schaden
    public void takeDamage(float damage) {
        currentHP -= damage;
        if (currentHP <= 0) {
            currentHP = 0;
            die();
        }
    }

    public void heal(float amount) {
        currentHP = Math.min(currentHP + amount, maxHP);
    }

    protected void die() {
        alive = false;
    }

    // richtungs enum
    public enum Direction {
        DOWN,
        UP,
        LEFT,
        RIGHT
    }
    public void updateMoveDirection(Vector2 moveDirectionUpdate) {
        moveDirection.set(moveDirectionUpdate);
        isMoving = !moveDirection.isZero();
        if (!isMoving) return;

        if (Math.abs(moveDirection.x) > Math.abs(moveDirection.y)) {
            if (moveDirection.x > 0) {
                facingDirection = Direction.RIGHT;
            } else {
                facingDirection = Direction.LEFT;
            }
        } else {
            if (moveDirection.y > 0) {
                facingDirection = Direction.UP;
            } else {
                facingDirection = Direction.DOWN;
            }
        }
    }

}
