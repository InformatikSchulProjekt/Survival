package com.test.SurvivorGame.entity.enemy;

import com.badlogic.gdx.math.Vector2;
import com.test.SurvivorGame.world.World;

public class Agis extends Enemy {

    private static final Vector2 SIZE = new Vector2(3f, 3f);
    private static final float MAX_HP = 30f;
    private static final float MOVEMENT_SPEED = 1f;
    private static final float DAMAGE = 3f;

    private static int bossCount = 1;
    private static int bossWaveCount = 30;

    public Agis(float x, float y, World world, float hpMultiplier) {
        super(x, y, world, SIZE, MAX_HP, MOVEMENT_SPEED, DAMAGE, hpMultiplier, EnemyType.AGIS);
    }

    public static int getBossCount() {
        return bossCount;
    }

    public static int getBossWaveCount() {
        return bossWaveCount;
    }

    @Override
    protected float getChestChance() {
        return 1f; // 100%
    }

    @Override
    public int getXPWorth() {
        return 25;
    }
}
