package com.test.SurvivorGame.world.maps;

import com.test.SurvivorGame.world.wave.EnemyType;
import com.test.SurvivorGame.world.wave.MapSettings;
import com.test.SurvivorGame.world.wave.Wave;

import java.util.ArrayList;
import java.util.List;

public class SafeMeadows extends GameMap {

    private final MapSettings mapSettings =
        new MapSettings(
            new ArrayList<>(List.of(
                EnemyType.BOSS
                // Hier eig noch mehr verschiedene Boss-Arten, wenn wir welche in der Map haben
            )),
            120f,
            1.5f,
            0.3f
        );

    public SafeMeadows() {

        super(
            "safe_meadows",
            "Safe Meadows",
            "A calm training ground hiding its first real threat.",
            "Maps/Map(clear2).png",
            30,
            30
        );

        // Wave 1 -----------------------------------
        spawnProfile.addWave(
            mapSettings.getWaveTime(),
            mapSettings.getStartInterval(),
            mapSettings.getEndInterval(),
            EnemyType.BOSS
        );
        spawnProfile.addEnemyToWave(
            spawnProfile.getCurrentWave(1),
            EnemyType.SLIME,
            100
        );

        // Wave 2 -----------------------------------
        spawnProfile.addWave(
            mapSettings.getWaveTime(),
            mapSettings.getStartInterval() - 0.1f,
            mapSettings.getEndInterval() - 0.1f,
            EnemyType.BOSS
        );

        spawnProfile.addEnemyToWave(
            spawnProfile.getCurrentWave(2),
            EnemyType.SLIME,
            100
        );

        // Wave 3 -----------------------------------
        spawnProfile.addWave(
            mapSettings.getWaveTime(),
            mapSettings.getStartInterval() - 0.2f,
            mapSettings.getEndInterval() - 0.2f,
            EnemyType.BOSS
        );

        spawnProfile.addEnemyToWave(
            spawnProfile.getCurrentWave(3),
            EnemyType.SLIME,
            100
        );

        // Wave 4 -----------------------------------
        spawnProfile.addWave(
            mapSettings.getWaveTime(),
            mapSettings.getStartInterval() - 0.3f,
            mapSettings.getEndInterval() - 0.2f,
            EnemyType.BOSS
        );

        spawnProfile.addEnemyToWave(
            spawnProfile.getCurrentWave(4),
            EnemyType.SLIME,
            100
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
