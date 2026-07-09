package com.test.SurvivorGame.world.maps;

import com.test.SurvivorGame.world.wave.EnemyType;
import com.test.SurvivorGame.world.wave.MapSettings;
import com.test.SurvivorGame.world.wave.Wave;

import java.util.ArrayList;
import java.util.List;

public class TestMap extends GameMap {

    private final MapSettings mapSettings =
        new MapSettings(
            new ArrayList<>(List.of(
                EnemyType.BOSS
                //Hier eig noch mehr verschiedene Boss-Arten, wenn wir welche haben
            )),
            10f, //gemeinsam überlegen wie wavetime hardcoded sein soll und co.
            1.5f,
            0.3f
        );

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
        return 1;
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
