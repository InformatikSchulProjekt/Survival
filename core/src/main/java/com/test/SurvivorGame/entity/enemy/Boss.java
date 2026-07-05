package com.test.SurvivorGame.entity.enemy;

import com.test.SurvivorGame.world.World;

public class Boss extends Enemy {

    private static final float SIZE = 5f;
    private static final float MAX_HP = 30f;
    private static final float MOVEMENT_SPEED = 1f;
    private static final float DAMAGE = 3f;

    private static int bossCount = 1;
    private static int bossWaveCount = 30;

    public Boss(float x, float y, World world, float hpMultiplier) {
        super(x, y, world, SIZE, MAX_HP, MOVEMENT_SPEED, DAMAGE, hpMultiplier);
    }

    public static int getBossCount() {
        return bossCount;
    }

    public static int getBossWaveCount() {
        return bossWaveCount;
    }
}
