package com.test.SurvivorGame.entity;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity extends GameObject {

    // rendering-relevante eigenschaften
    protected Direction facingDirection = Direction.DOWN;
    private static final float DAMAGE_FLASH_DURATION = 0.18f;
    protected float damageFlashTimer = 0f;

    // bewegung
    protected final Vector2 moveDirection = new Vector2();
    protected float movementSpeed;
    protected boolean isMoving = false;

    // status
    protected float maxHP;
    protected float currentHP;
    protected boolean alive = true;

    public Entity(float x, float y, float width, float height, float maxHP, float movementSpeed)
    {
        super(x,y,width,height);

        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.movementSpeed = movementSpeed;
    }

    // abstrakte Methoden für Subklassen

    public Direction getFacingDirection() {
        return facingDirection;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public boolean isDamageFlashing() {
        return damageFlashTimer > 0f;
    }

    public float getDamageFlashProgress() {
        return Math.min(damageFlashTimer / DAMAGE_FLASH_DURATION, 1f);
    }

    // schaden
    public void takeDamage(float damage) {
        currentHP -= damage;
        startDamageFlash();
    }

    public void heal(float amount) {
        currentHP = Math.min(currentHP + amount, maxHP);
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
