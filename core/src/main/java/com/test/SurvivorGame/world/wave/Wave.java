package com.test.SurvivorGame.world.wave;

import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class Wave {

    private float waveLifeTime;
    private float startInterval;
    private float endInterval;

    private ArrayList<SpawnEntry> enemies = new ArrayList<>();
    private EnemyType bossType;

    public Wave(float waveTime, float startInterval, float endInterval, EnemyType bossType)
    {
        this.waveLifeTime = waveTime;
        this.startInterval = startInterval;
        this.endInterval = endInterval;

        this.bossType = bossType;
    }

    public void addEnemy(SpawnEntry spawnEntry)
    {
        enemies.add(spawnEntry);
    }

    public EnemyType getRandomEnemy() {
        if (enemies.isEmpty()) {
            throw new IllegalStateException("Wave enthält keine Gegner!");
        }

        float random = MathUtils.random(100f);
        float sum = 0;

        for (SpawnEntry entry : enemies) {
            sum += entry.getChance();

            if (random <= sum) {
                return entry.getEnemyType();
            }
        }
        System.out.println("WARNING: Enemy spawn is not 100%, it is: " + sum + "%");
        return enemies.get(enemies.size() - 1).getEnemyType(); // absicherung, falls es insgesamt keine 100% sind, gibt letzten gegner zurück
    }

    public float getWaveLifeTime()
    {
        return waveLifeTime;
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

    public EnemyType getBoss()
    {
        return bossType;
    }
}
