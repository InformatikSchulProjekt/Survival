package com.test.SurvivorGame.world.maps.WaveControl;

import java.util.ArrayList;

public class TestSpawnProfile extends SpawnProfile{

    private ArrayList<Wave> waves = new ArrayList<>();


    public TestSpawnProfile()
    {
        Wave wave1 = new Wave(120, 2, 0.4f);
        wave1.addEnemy(new SpawnEntry(EnemyType.SLIME, 100));
        waves.add(wave1);
    }

    public void addWave(float waveTime, float startInterval, float endInterval)
    {
        waves.add(new Wave(waveTime, startInterval, endInterval));
    }

    public void addEnemyToWave(Wave wave, EnemyType enemyType, float percentage)
    {
        if(!wave.getEnemyList().isEmpty())
        {
            float percentageCounter = 0f;
            for(SpawnEntry spawnEntry : wave.getEnemyList())
            {
                percentageCounter += spawnEntry.getChance();
            }
            if(percentageCounter <= 100f && percentageCounter > 0f)
            {
                wave.addEnemy(new SpawnEntry(enemyType, percentage));
            }
            if(percentageCounter > 100f)
            {
                System.out.println("PERCENTAGECOUNT TO HIGH FOR ENEMYTYPE :" + enemyType + " in: " + wave);
            }
        }
    }

    public Wave getCurrentWave(int waveNumber) {
        return waves.get(waveNumber - 1);
    }
}
