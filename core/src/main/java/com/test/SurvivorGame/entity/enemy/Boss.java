package com.test.SurvivorGame.entity.enemy;

import com.test.SurvivorGame.entity.Player;

public class Boss extends Enemy {

    private static final float SIZE = 5f;
    private static final float MAX_HP = 30f;
    private static final float MOVEMENT_SPEED = 1f;
    private static final float DAMAGE = 3f;

    private static int bossCount = 1;
    private static int bossWaveCount = 30;

    public Boss(float x, float y, Player player) {
        super(x, y, player, SIZE, MAX_HP, MOVEMENT_SPEED, DAMAGE);
    }

    public static int getBossCount() {
        return bossCount;
    }

    public static int getBossWaveCount() {
        return bossWaveCount;
    }
}
