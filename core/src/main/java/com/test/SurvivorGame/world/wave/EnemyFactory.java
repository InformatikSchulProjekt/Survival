package com.test.SurvivorGame.world.wave;

import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.enemy.*;
import com.test.SurvivorGame.world.World;

public class EnemyFactory {

    /**
     * Erstellt abhängig vom übergebenen EnemyType den passenden Gegner.
     * Dabei werden die aktuellen Spieler-Modifikatoren sowie der übergebene
     * Skalierungsfaktor auf die Gegnerwerte angewendet.
     */
    public static Enemy createEnemy(EnemyType enemyType, float x, float y, World world, float scale) {

        float hpMulti = world.getPlayer().getPlayerState().getPlayerStats().getStat(StatScope.ALL, StatType.ENEMY_HP);
        hpMulti *= scale;
        switch (enemyType) {

            case SLIME:
                return new Slime(x, y, world, hpMulti, scale);

            case SKELETON:
                return new Skeleton(x, y, world, hpMulti, scale);

            case BOSS:
                return new Agis(x, y, world, hpMulti, scale);

            case WATCHER:
                return new WatcherBoss(x, y, world, hpMulti, scale);

            case PENGUIN:
                return new Penguin(x, y, world, hpMulti, scale);

            default:
                throw new IllegalArgumentException("Unknown EnemyType: " + enemyType);
        }
    }
}
