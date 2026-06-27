package com.test.SurvivorGame.entity;

import com.badlogic.gdx.math.Vector2;
import com.test.SurvivorGame.core.PlayerState;

public class Player extends GameObject {
    private PlayerState playerState;

    private Direction facingDirection = Direction.DOWN;

    private final Vector2 moveDirection =  new Vector2();

    public Player(PlayerState playerState) {
        super(playerState.getPlayerData().x, playerState.getPlayerData().y, 2f, 3f);
        this.playerState = playerState;
    }

    @Override
    public void update(float deltaTime)
    {
        move((deltaTime));
    }

    public void move(float deltaTime)
    {
        if(moveDirection.isZero()) return;

        float speed = playerState.getSpeed();
        float newX = collider.getX() + moveDirection.x * speed * deltaTime;
        float newY = collider.getY() + moveDirection.y * speed * deltaTime;

        collider.setPosition(newX, newY);
    }

//Diese Methode prüft einerseits welche Richtung der Spieler läuft andernseits berechnet sie in welche Richtung er stärker läuft...
    public void updateMoveDirection(Vector2 moveDirectionUpdate)
    {
        moveDirection.set(moveDirectionUpdate);
        if (moveDirection.isZero()) return;

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

    public enum Direction {
        DOWN,
        UP,
        LEFT,
        RIGHT
    }

    public boolean isMoving() {
        return !moveDirection.isZero();
    }

    public Direction getFacingDirection() {
        return facingDirection;
    }
}
