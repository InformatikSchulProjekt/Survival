package com.test.SurvivorGame.entity;

import com.badlogic.gdx.math.Vector2;
import com.test.SurvivorGame.core.data.PlayerData;
import com.test.SurvivorGame.stat.PlayerStats;
import com.test.SurvivorGame.stat.StatScope;
import com.test.SurvivorGame.stat.StatType;

public class Player extends GameObject {
    private final PlayerStats playerStats = new PlayerStats();

    private Direction facingDirection = Direction.DOWN;
    private PlayerData playerData;
    private int level = 1;

    private float currentHP;

    private final Vector2 moveDirection =  new Vector2();

    public Player(float x, float y) {
        super(x, y, 2f, 3f); // temporär ist size hardcoded   // ruft Konstruktor der Oberklasse auf und verwendet die übergebenen texture-daten des com.test.SurvivorGame.entity.Player Konstruktors
        currentHP = playerStats.getStat(StatScope.ALL, StatType.MAX_HEALTH);
    }

    // data handling
    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
        this.level = calcLevel();
    }

    // statshandling
    public PlayerStats getPlayerStats() {
        return playerStats;
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
        if(level>this.level) {
            this.level = level;
            System.out.println("LEVEL UP: "+level);
            // hier dann level up einleiten
        }
    }

    public int getLevel() {
        return level;
    }
    // data handling

    public void reset(float x, float y)
    {
        collider.setPosition(x,y);

        currentMaxHP = maxStartHP;
        currentHP = maxStartHP;
    }
    @Override
    public void update(float deltaTime)
    {
        move((deltaTime));
    }

    public void move(float deltaTime)
    {
        if(moveDirection.isZero()) return;

        float speed = playerStats.getStat(StatScope.ALL, StatType.SPEED);
        float newX = collider.getX() + moveDirection.x * speed * deltaTime;
        float newY = collider.getY() + moveDirection.y * speed * deltaTime;

        collider.setPosition(newX, newY);
    }

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
    }//Diese Methode prüft einerseits welche Richtung der Spieler läuft andernseits berechnet sie in welche Richtung er stärker läuft...

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
