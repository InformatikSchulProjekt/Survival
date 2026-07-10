package com.test.SurvivorGame.world.wave;

import java.util.ArrayList;

public class SpawnProfile {

    private ArrayList<Wave> waves = new ArrayList<>();

    public SpawnProfile() {}

    // Fügt dem Spawn-Profil eine neue Wave mit den angegebenen Spawn-Einstellungen hinzu.
    public void addWave(float waveTime, float startInterval, float endInterval, EnemyType bossType)
    {
        waves.add(new Wave(waveTime, startInterval, endInterval, bossType));
    }

    // Fügt einer Wave einen Gegnertyp mit einer bestimmten Spawn-Wahrscheinlichkeit hinzu.
    // Die Wahrscheinlichkeiten aller Gegner in einer Wave dürfen zusammen nicht über 100% liegen.
    public void addEnemyToWave(Wave wave, EnemyType enemyType, float percentage)
    {
        float percentageCounter = 0f;

        // Berechnet die bereits vergebenen Spawn-Chancen dieser Wave.
        for (SpawnEntry spawnEntry : wave.getEnemyList()) {
            percentageCounter += spawnEntry.getChance();
        }

        // Fügt den Gegner nur hinzu, wenn die maximale Wahrscheinlichkeit von 100% nicht überschritten wird.
        if (percentageCounter + percentage <= 100f) {
            wave.addEnemy(new SpawnEntry(enemyType, percentage));
        } else {
            System.out.println("Percentage zu hoch!");
        }
    }

    // Gibt die Wave mit der angegebenen Nummer zurück.
    // Da Listen bei 0 anfangen, muss die Wave-Nummer um 1 reduziert werden.
    public Wave getCurrentWave(int waveNumber) {
        return waves.get(waveNumber - 1);
    }

    // Prüft, ob nach der aktuellen Wave noch weitere Waves vorhanden sind.
    public boolean hasNextWave(int currentWave) {
        return currentWave < waves.size();
    }
}
