package com.test.SurvivorGame.entity;

import com.badlogic.gdx.math.MathUtils;
import com.test.SurvivorGame.core.data.PlayerData;
import com.test.SurvivorGame.world.maps.GameMap;

public class Player extends Entity {
    private static final float PLAYER_SIZE = 1f;
    private final float maxStartHP = 10f;

    private PlayerData playerData;
    private int level = 1;

    public Player(float x, float y) {
        super(x, y, PLAYER_SIZE * 2, PLAYER_SIZE * 3);
        maxHP = maxStartHP;
        currentHP = maxHP;
    }

    // data handling
    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
        this.level = calcLevel();
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    private int calcLevel() {
        if (playerData == null) {
            System.out.println("playerData in Player is NULL !!!!");
            return 1;
        }

        return playerData.xp / 3 + 1;
    }

    public void giveXP(int xp) {
        playerData.xp += xp;
        int level = calcLevel();
        if (level > this.level) {
            this.level = level;
            System.out.println("LEVEL UP: " + level);
            // hier dann level up einleiten
        }
    }

    public int getLevel() {
        return level;
    }
    // data handling

    public void reset(float x, float y) {
        alive = true;
        collider.setPosition(x, y);

        maxHP = maxStartHP;
        currentHP = maxHP;

        moveDirection.setZero();
        isMoving = false;
        facingDirection = Direction.DOWN;
    }



    public void move(float deltaTime) {
        if (moveDirection.isZero()) return;

        float newX = collider.getX() + moveDirection.x * movementSpeed * deltaTime;
        float newY = collider.getY() + moveDirection.y * movementSpeed * deltaTime;

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
