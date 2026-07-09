package com.test.SurvivorGame.world.WaveControl;

import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.enemy.Boss;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.entity.enemy.Skeleton;
import com.test.SurvivorGame.entity.enemy.Slime;
import com.test.SurvivorGame.world.World;

public class EnemyFactory {

    public static Enemy createEnemy(EnemyType enemyType, float x, float y, World world) {
        float hpMulti = world.getPlayer().getPlayerState().getPlayerStats().getStat(StatScope.ALL, StatType.ENEMY_HP);

        switch (enemyType) {

            case SLIME:
                return new Slime(x, y, world, hpMulti);

            case SKELETON:
                return new Skeleton(x, y, world, hpMulti);

            case BOSS:
                return new Boss(x, y, world, hpMulti);

            default:
                throw new IllegalArgumentException("Unknown EnemyType: " + enemyType);
        }
    }
}
