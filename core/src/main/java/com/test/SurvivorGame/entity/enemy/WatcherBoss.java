package com.test.SurvivorGame.entity.enemy;

import com.badlogic.gdx.math.Vector2;
import com.test.SurvivorGame.world.World;

public class WatcherBoss extends Enemy{

    private static final Vector2 SIZE = new Vector2(2.5f, 3f);
    private static final float MAX_HP = 250f;
    private static final float MOVEMENT_SPEED = 1.5f;
    private static final float DAMAGE = 15f;

    private static int bossCount = 1;
    private static int bossWaveCount = 0;
    public WatcherBoss(float x, float y, World world, float hpMultiplier) {
        super(x, y, world, SIZE, MAX_HP, MOVEMENT_SPEED, DAMAGE, hpMultiplier);
    }

    public static int getBossCount() {
        return bossCount;
    }

    public static int getBossWaveCount() {
        return bossWaveCount;
    }
}
