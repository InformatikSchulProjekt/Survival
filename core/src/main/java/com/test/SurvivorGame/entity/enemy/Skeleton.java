package com.test.SurvivorGame.entity.enemy;

import com.test.SurvivorGame.entity.Player;

public class Skeleton extends Enemy{

    private static final float SIZE = 1f;
    private static final float MAX_HP = 2f;
    private static final float MOVEMENT_SPEED = 4f;
    private static final float DAMAGE = 1f;


    public Skeleton(float x, float y, Player player) {
        super(x, y, player, SIZE, MAX_HP, MOVEMENT_SPEED, DAMAGE);
    }
}
