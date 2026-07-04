package com.test.SurvivorGame.world.maps.WaveControl;

import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class Wave {

    private float waveTime;
    private float startInterval;
    private float endInterval;

    private ArrayList<SpawnEntry> enemies = new ArrayList<>();

    public Wave(float waveTime, float startInterval, float endInterval)
    {
        this.waveTime = waveTime;
        this.startInterval = startInterval;
        this.endInterval = endInterval;
    }

    public void addEnemy(SpawnEntry spawnEntry)
    {
        enemies.add(spawnEntry);
    }

    public EnemyType getRandomEnemy() {

        float random = MathUtils.random(100f);
        float sum = 0;

        for (SpawnEntry entry : enemies) {
            sum += entry.getChance();

            if (random <= sum) {
                return entry.getEnemyType();
            }
        }

        return enemies.get(enemies.size() - 1).getEnemyType();
    }

    public float getWaveTime()
    {
        return waveTime;
    }

    public float getStartInterval()
    {
        return startInterval;
    }

    public float getEndInterval()
    {
        return endInterval;
    }

    public ArrayList<SpawnEntry> getEnemyList()
    {
        return enemies;
    }
}
