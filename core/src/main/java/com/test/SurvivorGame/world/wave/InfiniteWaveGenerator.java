package com.test.SurvivorGame.world.wave;

import com.badlogic.gdx.math.MathUtils;
import com.test.SurvivorGame.world.maps.GameMap;

import java.util.ArrayList;

public class InfiniteWaveGenerator {

    /**
     * Erstellt aus der letzten regulären Wave eine neue Endlos-Wave.
     * Dabei werden alle normalen Gegner übernommen, ein zufälliger Boss
     * gewählt und die Chance auf zufällige Boss-Spawns mit jeder
     * Endlos-Wave erhöht.
     */
    public static Wave generate(GameMap gameMap, int infiniteWaveCount) {

        ArrayList<EnemyType> bossTypes = gameMap.getMapsettings().getBossTypes();
        EnemyType endBoss = bossTypes.get(MathUtils.random(bossTypes.size() - 1));

        Wave templateWave = gameMap.getFinalWave();

        Wave newWave = new Wave(
            gameMap.getMapsettings().getWaveTime(),
            gameMap.getMapsettings().getStartInterval(),
            gameMap.getMapsettings().getEndInterval(),
            endBoss
        );

        newWave.setBossTypes(gameMap.getMapsettings().getBossTypes()); //für random-Boss encounters während der normalen wave phase

        newWave.setBossSpawnChance(Math.min(50f, infiniteWaveCount * 2f));

        for (SpawnEntry entry : templateWave.getEnemyList())
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
