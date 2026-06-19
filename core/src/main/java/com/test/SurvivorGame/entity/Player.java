package com.test.SurvivorGame.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.test.SurvivorGame.core.data.PlayerData;

public class Player extends GameObject {

    private static final float PLAYER_SIZE = 1f; //!!!ACHTUNG!!!! das mit playersize müssen wir bald gut machen und nicht wie grad so larifari
    private static float maxStartHP = 10; //standard zuweisung für den start des Spieles

    private float currentMaxHP = maxStartHP; //wenn leben durch Items upgegraded werden können muss current skalierbar sein
    private float currentHP = maxStartHP; //current verändert sich, aber ist am start max

    private PlayerData playerData;
    private int level = 1;

    private final Vector2 moveDirection =  new Vector2();
    private float movementSpeed = 2f; //nicht final, damit items anpassen können

    public Player(float x, float y, Texture texture) {
        super(x, y, PLAYER_SIZE, PLAYER_SIZE * 3, texture);   // ruft Konstruktor der Oberklasse auf und verwendet die übergebenen texture-daten des com.test.SurvivorGame.entity.Player Konstruktors
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

        float newX = collider.getX() + moveDirection.x * movementSpeed * deltaTime;
        float newY = collider.getY() + moveDirection.y * movementSpeed * deltaTime;

        collider.setPosition(newX, newY);
    }

    public void updateMoveDirection(Vector2 moveDirectionUpdate)
    {
        moveDirection.set(moveDirectionUpdate);
    }


}
