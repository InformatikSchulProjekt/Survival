package com.test.SurvivorGame.entity;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity extends GameObject {

    // rendering-relevante eigenschaften
    protected Direction facingDirection = Direction.DOWN;
    private static final float DAMAGE_FLASH_DURATION = 0.18f;
    protected float damageFlashTimer = 0f;

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

    public boolean isDamageFlashing() {
        return damageFlashTimer > 0f;
    }

    public float getDamageFlashProgress() {
        return Math.min(damageFlashTimer / DAMAGE_FLASH_DURATION, 1f);
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
        startDamageFlash();
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

    protected void startDamageFlash() {
        damageFlashTimer = DAMAGE_FLASH_DURATION;
    }

    protected void updateDamageFlash(float deltaTime) {
        if (damageFlashTimer > 0f) {
            damageFlashTimer = Math.max(0f, damageFlashTimer - deltaTime);
        }
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
