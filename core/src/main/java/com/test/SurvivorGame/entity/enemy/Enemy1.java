package com.test.SurvivorGame.entity.enemy;

import com.badlogic.gdx.math.Vector2;
import com.test.SurvivorGame.entity.Entity;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.world.maps.GameMap;

public class Enemy1 extends Entity {

    private static final float ENEMY_SIZE = 1f; //!ACHTUNG! das mit der size müssen wir bald gut machen und nicht wie grad so larifari


    private Player player;

    private float damagePerSecond = 1f;

    public Enemy1(float x, float y, Player player)
    {
        super(x, y, ENEMY_SIZE, ENEMY_SIZE);
        this.player = player;
        movementSpeed = 2f;
    }

    @Override
    public void update(float deltaTime, GameMap map)
    {
        Vector2 directionToPlayer = player.getCenter().sub(this.getCenter());

        if (!directionToPlayer.isZero()) {
            directionToPlayer.nor();
        }

        updateMoveDirection(directionToPlayer);

        collider.setPosition(
            collider.getX() + moveDirection.x * movementSpeed * deltaTime,
            collider.getY() + moveDirection.y * movementSpeed * deltaTime
        );
    }

    public float getDamagePerSecond()
    {
        return damagePerSecond;
    }
}
