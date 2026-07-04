package com.test.SurvivorGame.entity.enemy;

import com.test.SurvivorGame.world.World;

public class Slime extends Enemy{

    private static final float SIZE = 1f;
    private static final float MAX_HP = 1f;
    private static final float MOVEMENT_SPEED = 4f;
    private static final float DAMAGE = 1f;


    public Slime(float x, float y, World world) {
        super(x, y, world, SIZE, MAX_HP, MOVEMENT_SPEED, DAMAGE);
    }
}
