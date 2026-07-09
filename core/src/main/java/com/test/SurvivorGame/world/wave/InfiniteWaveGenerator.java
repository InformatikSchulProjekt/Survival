package com.test.SurvivorGame.world.wave;

import com.test.SurvivorGame.world.maps.GameMap;

public class InfiniteWaveGenerator {

    private static float infiniteWaveCount = 0;

    public static Wave generate(GameMap gameMap) {

        infiniteWaveCount++;

        Wave finalWave = gameMap.getFinalWave();


        Wave newWave = new Wave(
            gameMap.getMapsettings().getWaveTime(),
            gameMap.getMapsettings().getStartInterval(),
            gameMap.getMapsettings().getEndInterval(),
            EnemyType.BOSS // muss man noch randomizen
        );

        newWave.setBossSpawnChance(
            Math.min(50f, infiniteWaveCount * 2f)
        );

        for (SpawnEntry entry : finalWave.getEnemyList())
        {
            newWave.addEnemy(
                new SpawnEntry(
                    entry.getEnemyType(),
                    entry.getChance()
                )
            );
        }

        return newWave;
    }

}
