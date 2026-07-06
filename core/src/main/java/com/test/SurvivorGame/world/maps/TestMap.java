package com.test.SurvivorGame.world.maps;

import com.test.SurvivorGame.world.maps.WaveControl.EnemyType;

public class TestMap extends GameMap {

    public TestMap() {

        super(
            "TestMap",
            "Test Map",
            "The first testing map.",
            "Maps/Map(clear2).png",
            30,
            30,
            true
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
}
