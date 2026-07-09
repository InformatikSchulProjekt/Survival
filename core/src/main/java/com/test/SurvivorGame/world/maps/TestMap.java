package com.test.SurvivorGame.world.maps;

import com.test.SurvivorGame.world.wave.EnemyType;

import java.util.ArrayList;

public class TestMap extends GameMap {

    public TestMap() {

        super(
            "TestMap",
            "Test Map",
            "The first testing map.",
            "Maps/Map(clear2).png",
            30,
            30
        );

        spawnProfile.addWave(
            10f,
            1.5f,
            0.3f,
            EnemyType.BOSS
        );

        spawnProfile.addEnemyToWave(
            spawnProfile.getCurrentWave(1),
            EnemyType.SLIME,
            100
        );
    }

    @Override
    public int getMaxWaves() {
        return 2;
    }

    @Override
    public ArrayList<EnemyType> getEnemyTypes() {
        ArrayList<EnemyType> enemyTypes = new ArrayList<>();
        enemyTypes.add(EnemyType.SLIME);

        return enemyTypes;
    }
}
