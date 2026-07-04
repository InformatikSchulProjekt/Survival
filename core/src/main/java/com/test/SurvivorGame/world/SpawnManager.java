package com.test.SurvivorGame.world;

import com.badlogic.gdx.math.MathUtils;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.enemy.Boss;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.entity.enemy.Slime;
import com.test.SurvivorGame.world.maps.GameMap;
import com.test.SurvivorGame.world.maps.WaveControl.EnemyFactory;
import com.test.SurvivorGame.world.maps.WaveControl.Wave;

import java.util.ArrayList;

public class SpawnManager {

    private float waveTime = 0f;    // wieviel Zeit ist seit start der wave vergangen
    private float spawnTimer = 0f;
    private float currentSpawnInterval;

    private boolean bossPhaseTriggered = false;
    private enum WaveState {
        NORMAL,
        BOSS
    }
    private WaveState state = WaveState.NORMAL;

    private Player player;
    private World world;
    GameMap map;

    private ArrayList<Enemy> enemies = new ArrayList<>();

    private int currentWave;
    private Wave currentWaveReference;

    public SpawnManager(World world, GameMap map)
    {
        this.world = world;
        this.player = world.getPlayer();
        currentWave = 1;
        this.map = map;

        this.currentWaveReference = map.getSpawnprofile().getCurrentWave(currentWave);
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

        currentSpawnInterval = MathUtils.lerp(
            currentWaveReference.getStartInterval(),
            currentWaveReference.getEndInterval(),
            waveTime / currentWaveReference.getWaveLifeTime());

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

        if(enemies.isEmpty() && map.getSpawnprofile().hasNextWave(currentWave))
        {
            startNextWave();
        }
        if(enemies.isEmpty() && !map.getSpawnprofile().hasNextWave(currentWave))
        {
            System.out.println("No Waves; You Completed"); // Funktion nach abschluss der map
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

        enemies.add(EnemyFactory.createEnemy(currentWaveReference.getRandomEnemy(), x, y, world));
    }

    private void spawnBoss()
    {
        float distance = MathUtils.random(10f, 15f); // zufälliger radius

        float angle = MathUtils.random(0f, 360f); //zufällige richtung

        float x = player.getCenter().x +
            MathUtils.cosDeg(angle) * distance;

        float y = player.getCenter().y +
            MathUtils.sinDeg(angle) * distance;

        enemies.add(EnemyFactory.createEnemy(currentWaveReference.getBoss(),x,y,world));
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
        if(waveTime < currentWaveReference.getWaveLifeTime())
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

    public void resetSpawn()
    {
        currentWave = 1;

        waveTime = 0f;
        spawnTimer = 0f;

        bossPhaseTriggered = false;
        state = WaveState.NORMAL;
    }

    private void startNextWave()
    {
        currentWave++;
        currentWaveReference = map.getSpawnprofile().getCurrentWave(currentWave);

        waveTime = 0f;
        spawnTimer = 0f;

        bossPhaseTriggered = false;
        state = WaveState.NORMAL;


    }
}
