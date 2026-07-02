package com.test.SurvivorGame.entity;

import com.badlogic.gdx.math.MathUtils;
import com.test.SurvivorGame.world.maps.GameMap;
import com.test.SurvivorGame.core.PlayerState;

public class Player extends Entity {
    private PlayerState playerState;


    public Player(PlayerState playerState) {
        super(playerState.getX(), playerState.getY(), 2f, 3f, playerState.getHP(), playerState.getSpeed());
        this.playerState = playerState;
    }

    public void reset(float x, float y) {
        alive = true;
        collider.setPosition(x, y);


        moveDirection.setZero();
        isMoving = false;
        facingDirection = Direction.DOWN;

        playerState.resetHealth();
    }

    public void move(float deltaTime) {
        if (moveDirection.isZero()) return;

        float speed = playerState.getSpeed();
        //debug: System.out.println("Player Speed: "+speed);
        float newX = collider.getX() + moveDirection.x * speed * deltaTime;
        float newY = collider.getY() + moveDirection.y * speed * deltaTime;

        playerState.setPosition(newX, newY);
        collider.setPosition(newX, newY);
    }

    // updated mit MapRand
    private void clampToMap(GameMap map) {
        if (map == null) return;
        if (map.isInfinite()) return;

        float minX = 0f;
        float minY = 0f;
        float maxX = map.getWorldWidth() - getWidth();
        float maxY = map.getWorldHeight() - getHeight();

        float clampedX = MathUtils.clamp(collider.x, minX, Math.max(minX, maxX));
        float clampedY = MathUtils.clamp(collider.y, minY, Math.max(minY, maxY));

        collider.setPosition(clampedX, clampedY);
        playerState.setPosition(clampedX, clampedY);
    }

    @Override
    public void takeDamage(float damage) {
        if (playerState.damage(damage)) return;

        die();
        //temp:
        reset(0, 0);

    }
    @Override
    public void update(float deltaTime,GameMap map) {
        move(deltaTime);
        clampToMap(map);
    }

}
