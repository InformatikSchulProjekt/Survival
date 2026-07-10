package com.test.SurvivorGame.world.system;

import com.badlogic.gdx.math.MathUtils;
import com.test.SurvivorGame.core.data.PlayerData;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.drops.ChestObject;
import com.test.SurvivorGame.entity.drops.ChestType;
import com.test.SurvivorGame.entity.enemy.Agis;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.screen.GamePlayScreen;
import com.test.SurvivorGame.world.World;
import com.test.SurvivorGame.world.maps.GameMap;
import com.test.SurvivorGame.world.wave.EnemyFactory;
import com.test.SurvivorGame.world.wave.EnemyType;
import com.test.SurvivorGame.world.wave.InfiniteWaveGenerator;
import com.test.SurvivorGame.world.wave.Wave;

import java.util.ArrayList;

public class SpawnSystem {

    private float waveTime = 0f;    // wieviel Zeit ist seit start der wave vergangen
    private float spawnTimer = 0f;
    private float currentSpawnInterval;

    private boolean bossPhaseTriggered = false;

    private enum WaveState {
        NORMAL,
        BOSS
    }

    private WaveState state = WaveState.NORMAL;

    private final PlayerData playerData;
    private final Player player;
    private final World world;
    private final GameMap gameMap;
    private final GamePlayScreen gamePlayScreen;

    private ArrayList<Enemy> enemies = new ArrayList<>();

    private Wave currentWaveReference;

    private int infiniteWaveCount = 0;
    private float enemyHpScale = 1f;

    private boolean bossChestSpawned = false;

    public SpawnSystem(World world, GameMap gameMap, GamePlayScreen gamePlayScreen) {
        this.world = world;
        this.player = world.getPlayer();
        this.playerData = player.getPlayerState().getPlayerData();
        this.gamePlayScreen = gamePlayScreen;
        this.gameMap = gameMap;

        setNewCurrentWave();
    }

    public void update(float deltaTime, GameMap map) {
        switch (state) {
            case NORMAL -> updateNormalWave(deltaTime);
            case BOSS -> updateBossWave();
        }

        updateEnemy(deltaTime, map);

    }

    public void updateNormalWave(float deltaTime) {
        waveTime += deltaTime;
        spawnTimer += deltaTime;

        currentSpawnInterval = MathUtils.lerp(
            currentWaveReference.getStartInterval(),
            currentWaveReference.getEndInterval(),
            waveTime / currentWaveReference.getWaveLifeTime());

        if (spawnTimer >= currentSpawnInterval) {
            spawnEnemy();
            spawnTimer = 0;
        }

        if (endTime()) {
            state = WaveState.BOSS;
        }
    }

    private void updateBossWave() {
        if (!bossPhaseTriggered) {
            triggerBossPhase();
        }

        if (enemies.isEmpty()) {

            world.saveGame();

            if (!bossChestSpawned) {
                spawnBossChest();
                bossChestSpawned = true;
            }

            if (isInfiniteMode()) {
                startNextWave();
            } else if (gameMap.getSpawnProfile().hasNextWave(playerData.wave)) {
                startNextWave();
            } else {
                gamePlayScreen.showGameFinishedUI();
            }
        }

    }

    private void spawnEnemy() {
        float distance = MathUtils.random(10f, 15f); // zufälliger radius

        float angle = MathUtils.random(0f, 360f); //zufällige richtung

        float x = player.getCenter().x +
            MathUtils.cosDeg(angle) * distance;

        float y = player.getCenter().y +
            MathUtils.sinDeg(angle) * distance;

        // für infinite mode
        if (MathUtils.random(100f) < currentWaveReference.getBossSpawnChance()) {

            EnemyType boss =
                currentWaveReference.getRandomBoss();

            enemies.add(
                EnemyFactory.createEnemy(boss, x, y, world, enemyHpScale)
            );
            return;
        }

        enemies.add(EnemyFactory.createEnemy(currentWaveReference.getRandomEnemy(), x, y, world, enemyHpScale));
    }

    private void spawnBoss() {
        float distance = MathUtils.random(10f, 15f); // zufälliger radius

        float angle = MathUtils.random(0f, 360f); //zufällige richtung

        float x = player.getCenter().x +
            MathUtils.cosDeg(angle) * distance;

        float y = player.getCenter().y +
            MathUtils.sinDeg(angle) * distance;

        enemies.add(EnemyFactory.createEnemy(currentWaveReference.getBoss(), x, y, world, enemyHpScale));

        world.setSurvivalTimePaused(true); // timer stop
    }

    private void updateEnemy(float deltaTime, GameMap map) {
        for (int i = enemies.size() - 1; i >= 0; i--) // ability objects werden nacheinander durchgegangen
        {
            Enemy enemy = enemies.get(i);
            enemy.update(deltaTime, map);

            if (enemy.isRemovable()) {
                enemies.remove(i);
            }
        }
    }

    private void triggerBossPhase() {
        if (!bossPhaseTriggered && endTime()) {
            for (int i = 1; i < Agis.getBossWaveCount(); i++) {
                spawnEnemy();
            }
            for (int i = 0; i < Agis.getBossCount(); i++) {
                spawnBoss();
            }
            bossPhaseTriggered = true;
        }
    }


    private boolean endTime() {
        return !(waveTime < currentWaveReference.getWaveLifeTime());
    }

    private void spawnBossChest() {
        float distance = MathUtils.random(2f, 4f);
        float angle = MathUtils.random(0f, 360f);

        float x = player.getCenter().x +
            MathUtils.cosDeg(angle) * distance;

        float y = player.getCenter().y +
            MathUtils.sinDeg(angle) * distance;

        world.addDrop(new ChestObject(x, y, player, ChestType.BOSS));
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    private void startNextWave() {
        playerData.wave++;

        setNewCurrentWave();

        waveTime = 0f;
        spawnTimer = 0f;

        bossChestSpawned = false;
        world.setSurvivalTimePaused(false);

        bossPhaseTriggered = false;
        state = WaveState.NORMAL;
    }

    public void startInfiniteMode() {
        playerData.wave = gameMap.getMaxWaves() + 1;

        setNewCurrentWave();

        waveTime = 0f;
        spawnTimer = 0f;

        bossChestSpawned = false;
        bossPhaseTriggered = false;

        state = WaveState.NORMAL;

        world.setSurvivalTimePaused(false);
    }

    private void setNewCurrentWave() {
        if (isInfiniteMode()) {
            setNewInfiniteWave();
        } else {
            setNewNormalWave();
        }
    }

    private void setNewNormalWave() {
        currentWaveReference = gameMap.getSpawnProfile().getCurrentWave(playerData.wave);
    }

    private void setNewInfiniteWave() {
        infiniteWaveCount++;

        enemyHpScale *= 1.15f; // um 15% erhöht

        currentWaveReference = InfiniteWaveGenerator.generate(gameMap, infiniteWaveCount);
    }

    private boolean isInfiniteMode() {
        return playerData.wave > gameMap.getMaxWaves();
    }
}
