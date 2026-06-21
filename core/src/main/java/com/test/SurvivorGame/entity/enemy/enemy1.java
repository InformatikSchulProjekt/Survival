package com.test.SurvivorGame.entity.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.test.SurvivorGame.entity.GameObject;
import com.test.SurvivorGame.entity.Player;

public class enemy1 extends GameObject {

    private static final float ENEMY_SIZE = 1f; //!ACHTUNG! das mit der size müssen wir bald gut machen und nicht wie grad so larifari


    private Player player;

    private Vector2 moveDirection;

    private float movementSpeed = 2f;

    public enemy1(float x, float y, Player player)
    {
        super(x, y, ENEMY_SIZE, ENEMY_SIZE,new Texture(Gdx.files.internal("Placeholder/enemy1PH.png")));
        this.player = player;
    }

    @Override
    public void update(float deltaTime)
    {
        moveDirection = player.getCenter().sub(this.getCenter()).nor().scl(movementSpeed * deltaTime);

        collider.setPosition(collider.getX() + moveDirection.x, collider.getY() + moveDirection.y);
    }
}
