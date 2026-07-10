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

    // Speichert den aktuellen Zustand der Wave.
    // Normal bedeutet normale Gegner-Spawns, Boss bedeutet Boss-Phase.
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

    // Referenz auf die aktuell laufende Wave, damit Spawn-Werte nicht jedes Mal neu geladen werden müssen.
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

    // Aktualisiert die normale Wave und berechnet anhand der vergangenen Zeit
    // das aktuelle Spawn-Intervall zwischen Start- und Endwert.
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

        // Sobald die Wave-Zeit abgelaufen ist, wird in die Boss-Phase gewechselt.
        if (endTime()) {
            state = WaveState.BOSS;
        }
    }

    // Verwaltet die Boss-Phase.
    // Der Boss wird einmal gespawnt und danach gewartet, bis alle Gegner besiegt wurden.
    private void updateBossWave() {
        if (!bossPhaseTriggered) {
            triggerBossPhase();
        }

        if (enemies.isEmpty()) {

            world.saveGame();

            // Die Boss-Truhe erscheint nur einmal pro Boss-Wave.
            if (!bossChestSpawned) {
                spawnBossChest();
                bossChestSpawned = true;
            }

            // Startet entweder die nächste normale Wave, eine Infinite-Wave
            // oder beendet das Spiel, wenn keine Waves mehr vorhanden sind.
            if (isInfiniteMode()) {
                startNextWave();
            } else if (gameMap.getSpawnProfile().hasNextWave(playerData.wave)) {
                startNextWave();
            } else {
                gamePlayScreen.showGameFinishedUI();
            }
            world.saveGame();
        }
    }

    // Spawnt einen normalen Gegner an einer zufälligen Position um den Spieler.
    // Im Infinite Mode besteht zusätzlich die Chance, dass ein Boss erscheint.
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

    // Spawnt einen Boss an einer zufälligen Position um den Spieler.
    // Während einer Boss-Phase wird der Survival-Timer pausiert.
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

    // Aktualisiert alle Gegner und entfernt Gegner, die nicht mehr benötigt werden.
    // Rückwärts durch die Liste gehen verhindert Fehler beim Entfernen während der Iteration.
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

    // Startet die Boss-Phase und erzeugt die vorgesehenen Boss- und Zusatzgegner.
    // Die Abfrage verhindert, dass die Phase mehrfach ausgelöst wird.
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

    // Prüft, ob die aktuelle Wave ihre maximale Laufzeit erreicht hat.
    private boolean endTime() {
        return !(waveTime < currentWaveReference.getWaveLifeTime());
    }

    // Spawnt die Belohnungstruhe nach einer besiegten Boss-Wave.
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

    // Setzt alle Werte zurück und lädt die nächste Wave.
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

    // Startet den Infinite Mode ab der ersten Wave nach dem normalen Spielende.
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

    // Entscheidet anhand der Auswahl aus dem Screen am Ende einer Map, ob eine normale oder Infinite Wave geladen wird.
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

    // Erstellt eine neue zufällige Infinite-Wave und erhöht die Gegner-Skalierung.
    private void setNewInfiniteWave() {
        infiniteWaveCount++;

        enemyHpScale *= 1.15f; // um 15% erhöht

        currentWaveReference = InfiniteWaveGenerator.generate(gameMap, infiniteWaveCount);
    }

    // Prüft, ob der Spieler bereits über die normalen Maps hinaus ist.
    private boolean isInfiniteMode() {
        return playerData.wave > gameMap.getMaxWaves();
    }
}
