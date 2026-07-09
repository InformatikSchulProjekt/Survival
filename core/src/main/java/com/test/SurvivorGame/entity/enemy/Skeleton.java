package com.test.SurvivorGame.entity.enemy;

import com.badlogic.gdx.math.Vector2;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.world.World;

public class Skeleton extends Enemy{

    private static final Vector2 SIZE = new Vector2(1f,1.5f);
    private static final float MAX_HP = 2f;
    private static final float MOVEMENT_SPEED = 4f;
    private static final float DAMAGE = 1f;


    public Skeleton(float x, float y, World world, float hpMultiplier) {
        super(x, y, world, SIZE, MAX_HP, MOVEMENT_SPEED, DAMAGE, hpMultiplier);
    }
}
