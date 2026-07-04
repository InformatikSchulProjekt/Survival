package com.test.SurvivorGame.world;

import com.badlogic.gdx.math.MathUtils;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.enemy.Boss;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.world.maps.GameMap;

import java.util.ArrayList;

public class SpawnManager {

    private float waveLifeTime = 20f;
    private float waveTime = 0f;    //wieviel Zeit ist seit start der wave vergangen
    private float spawnTimer = 0f;


    private float startInterval;
    private float endInterval;
    private float currentSpawnInterval = startInterval; // am anfang gesetzter beispiel interval;

    private boolean bossPhaseTriggered = false;
    private enum WaveState {
        NORMAL,
        BOSS
    }
    private WaveState state = WaveState.NORMAL;

    private Player player;

    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Boss> boss = new ArrayList<>();

    public SpawnManager(Player player)
    {
        this.player = player;
    }

    public void update(float deltaTime, GameMap map)
    {
        switch (state)
        {
            case NORMAL -> updateNormalWave(deltaTime);
            case BOSS -> updateBossWave();
        }

        updateEnemy(deltaTime, map);
    }

    public void updateNormalWave(float deltaTime)
    {
        waveTime += deltaTime;
        spawnTimer += deltaTime;

        currentSpawnInterval = MathUtils.lerp(2.0f, 0.4f, waveTime / waveLifeTime);

        if (spawnTimer >= currentSpawnInterval)
        {
            spawnEnemy();
            spawnTimer = 0;
        }

        if(endTime())
        {
            state = WaveState.BOSS;
        }

    }

    private void updateBossWave()
    {
        if(!bossPhaseTriggered)
        {
            triggerBossPhase();
        }
    }

    private void spawnEnemy()
    {
        float distance = MathUtils.random(10f, 15f); // zufälliger radius

        float angle = MathUtils.random(0f, 360f); //zufällige richtung

        float x = player.getCenter().x +
            MathUtils.cosDeg(angle) * distance;

        float y = player.getCenter().y +
            MathUtils.sinDeg(angle) * distance;

        enemies.add(new Enemy(x, y, player));
    }

    private void spawnBoss()
    {
        float distance = MathUtils.random(10f, 15f); // zufälliger radius

        float angle = MathUtils.random(0f, 360f); //zufällige richtung

        float x = player.getCenter().x +
            MathUtils.cosDeg(angle) * distance;

        float y = player.getCenter().y +
            MathUtils.sinDeg(angle) * distance;

        enemies.add(new Boss(x, y, player));
    }

    private void updateEnemy(float deltaTime, GameMap map)
    {
        for(int i = enemies.size() - 1; i >= 0; i--) // ability objects werden nacheinander durchgegangen
        {
            Enemy enemy = enemies.get(i);

            enemy.update(deltaTime, map);

            if(enemy.isDead())
            {
                enemies.remove(i);
            }
        }
    }

    private void triggerBossPhase()
    {
        if(!bossPhaseTriggered && endTime())
        {
            for(int i = 1; i < Boss.getBossWaveCount(); i++)
            {
                spawnEnemy();
            }
            for (int i = 0; i < Boss.getBossCount(); i++)
            {
                spawnBoss();
            }

            bossPhaseTriggered = true;
        }
    }

    private boolean endTime()
    {
        if(waveTime < waveLifeTime)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public ArrayList<Enemy> getEnemies()
    {
        return enemies;
    }
    public ArrayList<Boss> getBoss()
    {
        return boss;
    }

    public void resetSpawn()
    {
        waveTime = 0f;
        spawnTimer = 0f;

        bossPhaseTriggered = false;
        state = WaveState.NORMAL;
    }
}
