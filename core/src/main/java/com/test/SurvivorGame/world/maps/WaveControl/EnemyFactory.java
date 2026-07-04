package com.test.SurvivorGame.world.maps.WaveControl;

import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.enemy.Boss;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.entity.enemy.Skeleton;
import com.test.SurvivorGame.entity.enemy.Slime;
import com.test.SurvivorGame.world.World;

public class EnemyFactory {

    public static Enemy createEnemy(EnemyType enemyType, float x, float y, World world) {

        switch (enemyType) {

            case SLIME:
                return new Slime(x, y, world);

            case SKELETON:
                return new Skeleton(x, y, world);

            case BOSS:
                return new Boss(x, y, world);

            default:
                throw new IllegalArgumentException("Unknown EnemyType: " + enemyType);
        }
    }
}
