package com.test.SurvivorGame.world.wave;

import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.enemy.*;
import com.test.SurvivorGame.world.World;

public class EnemyFactory {

    public static Enemy createEnemy(EnemyType enemyType, float x, float y, World world, float hpScale) {

        float hpMulti = world.getPlayer().getPlayerState().getPlayerStats().getStat(StatScope.ALL, StatType.ENEMY_HP);
        hpMulti *= hpScale;
        switch (enemyType) {

            case SLIME:
                return new Slime(x, y, world, hpMulti);

            case SKELETON:
                return new Skeleton(x, y, world, hpMulti);

            case BOSS:
                return new Boss(x, y, world, hpMulti);

            case WATCHER:
                return new WatcherBoss(x, y, world, hpMulti);

            default:
                throw new IllegalArgumentException("Unknown EnemyType: " + enemyType);
        }
    }
}
