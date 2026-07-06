package com.test.SurvivorGame.world.maps;

import com.test.SurvivorGame.world.maps.WaveControl.EnemyType;

public class SecondTestMap extends GameMap {

    public SecondTestMap() {

        super(
            "SecondTestMap",
            "Second Test Map",
            "The second testing map.",
            "Maps/Map(clear1).png",
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
            EnemyType.SKELETON,
            100
        );
    }
}

