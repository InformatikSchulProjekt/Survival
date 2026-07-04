package com.test.SurvivorGame.world.maps.WaveControl;

import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.enemy.Boss;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.entity.enemy.Skeleton;
import com.test.SurvivorGame.entity.enemy.Slime;

public class EnemyFactory {

    public static Enemy createEnemy(EnemyType enemyType, float x, float y, Player player) {

        switch (enemyType) {

            case SLIME:
                return new Slime(x, y, player);

            case SKELETON:
                return new Skeleton(x, y, player);

            case BOSS:
                return new Boss(x, y, player);

            default:
                throw new IllegalArgumentException("Unknown EnemyType: " + enemyType);
        }
    }
}
