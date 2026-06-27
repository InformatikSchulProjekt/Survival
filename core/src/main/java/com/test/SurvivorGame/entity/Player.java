package com.test.SurvivorGame.entity;

import com.badlogic.gdx.math.MathUtils;
import com.test.SurvivorGame.core.data.PlayerData;
import com.test.SurvivorGame.world.maps.GameMap;
import com.badlogic.gdx.math.Vector2;
import com.test.SurvivorGame.core.PlayerState;

public class Player extends Entity {
    private PlayerState playerState;

    private PlayerData playerData;

    public Player(PlayerState playerState) {
        super(playerState.getPlayerData().x, playerState.getPlayerData().y, 2f, 3f);
        this.playerState = playerState;
    }

    public void reset(float x, float y) {
        alive = true;
        collider.setPosition(x, y);


        moveDirection.setZero();
        isMoving = false;
        facingDirection = Direction.DOWN;
    }

    public void move(float deltaTime) {
        if (moveDirection.isZero()) return;

        float speed = playerState.getSpeed();
        float newX = collider.getX() + moveDirection.x * speed * deltaTime;
        float newY = collider.getY() + moveDirection.y * speed * deltaTime;

        collider.setPosition(newX, newY);
    }

    // updated mit MapRand
    private void clampToMap(GameMap map) {
        if (map == null) return;

        float minX = 0f;
        float minY = 0f;
        float maxX = map.getWorldWidth() - getWidth();
        float maxY = map.getWorldHeight() - getHeight();

        float clampedX = MathUtils.clamp(collider.x, minX, Math.max(minX, maxX));
        float clampedY = MathUtils.clamp(collider.y, minY, Math.max(minY, maxY));

        collider.setPosition(clampedX, clampedY);
    }

    @Override
    public void takeDamage(float damage) {
        currentHP -= damage;
        System.out.println("Player bekommt schaden: " + damage);
        System.out.println("Player hat: " + currentHP + " Leben");

        if (currentHP <= 0) {
            currentHP = 0;
            die();
        }

    }
    @Override
    public void update(float deltaTime,GameMap map) {
        move(deltaTime);
        clampToMap(map);
    }
}
