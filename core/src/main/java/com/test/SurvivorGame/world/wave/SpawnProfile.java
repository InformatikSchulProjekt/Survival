package com.test.SurvivorGame.world.wave;

import java.util.ArrayList;

public class SpawnProfile {

    private ArrayList<Wave> waves = new ArrayList<>();


    public SpawnProfile()
    {

    }

    public void addWave(float waveTime, float startInterval, float endInterval, EnemyType bossType)
    {
        waves.add(new Wave(waveTime, startInterval, endInterval, bossType));
    }

    public void addEnemyToWave(Wave wave, EnemyType enemyType, float percentage)
    {
        float percentageCounter = 0f;

        for (SpawnEntry spawnEntry : wave.getEnemyList()) {
            percentageCounter += spawnEntry.getChance();
        }

        if (percentageCounter + percentage <= 100f) {
            wave.addEnemy(new SpawnEntry(enemyType, percentage));
        } else {
            System.out.println("Percentage zu hoch!");
        }
    }

    public Wave getCurrentWave(int waveNumber) {
        return waves.get(waveNumber - 1);
    }

    public boolean hasNextWave(int currentWave) {
        return currentWave < waves.size();
    }
}
