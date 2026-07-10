package com.test.SurvivorGame.world.maps;

import com.test.SurvivorGame.world.wave.EnemyType;
import com.test.SurvivorGame.world.wave.MapSettings;
import com.test.SurvivorGame.world.wave.Wave;

import java.util.ArrayList;
import java.util.List;

public class DarkFrontier extends GameMap {

    private final MapSettings mapSettings =
        new MapSettings(
            new ArrayList<>(List.of(
                EnemyType.WATCHER
                //Hier eig noch mehr verschiedene Boss-Arten, wenn wir welche haben
            )),
            120f,
            1.5f,
            0.3f
        );

    public DarkFrontier() {

        super(
            "dark_frontier",
            "Dark Frontier",
            "A corrupted borderland where skeletons rise from the dark.",
            "Maps/Map(clear1).png",
            30,
            30
        );

        // Wave 1 -----------------------------------
        spawnProfile.addWave(
            mapSettings.getWaveTime(),
            mapSettings.getStartInterval(),
            mapSettings.getEndInterval(),
            EnemyType.WATCHER
        );

        spawnProfile.addEnemyToWave(
            spawnProfile.getCurrentWave(1),
            EnemyType.PENGUIN,
            80
        );

        spawnProfile.addEnemyToWave(
            spawnProfile.getCurrentWave(1),
            EnemyType.SKELETON,
            20
        );

        // Wave 2 -----------------------------------
        spawnProfile.addWave(
            mapSettings.getWaveTime(),
            mapSettings.getStartInterval() - 0.1f,
            mapSettings.getEndInterval() - 0.1f,
            EnemyType.WATCHER
        );

        spawnProfile.addEnemyToWave(
            spawnProfile.getCurrentWave(2),
            EnemyType.PENGUIN,
            70
        );

        spawnProfile.addEnemyToWave(
            spawnProfile.getCurrentWave(2),
            EnemyType.SKELETON,
            30
        );

        // Wave 3 -----------------------------------
        spawnProfile.addWave(
            mapSettings.getWaveTime(),
            mapSettings.getStartInterval() - 0.2f,
            mapSettings.getEndInterval() - 0.2f,
            EnemyType.WATCHER
        );

        spawnProfile.addEnemyToWave(
            spawnProfile.getCurrentWave(3),
            EnemyType.PENGUIN,
            60
        );

        spawnProfile.addEnemyToWave(
            spawnProfile.getCurrentWave(3),
            EnemyType.SKELETON,
            40
        );

        // Wave 4 -----------------------------------
        spawnProfile.addWave(
            mapSettings.getWaveTime(),
            mapSettings.getStartInterval() - 0.3f,
            mapSettings.getEndInterval() - 0.2f,
            EnemyType.WATCHER
        );

        spawnProfile.addEnemyToWave(
            spawnProfile.getCurrentWave(4),
            EnemyType.PENGUIN,
            50
        );

        spawnProfile.addEnemyToWave(
            spawnProfile.getCurrentWave(4),
            EnemyType.SKELETON,
            50
        );

    }

    @Override
    public int getMaxWaves() {
        return 4;
    }

    @Override
    public MapSettings getMapsettings() {
        return mapSettings;
    }

    public Wave getFinalWave()
    {
        return spawnProfile.getCurrentWave(getMaxWaves());
    }

}
