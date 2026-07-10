package com.test.SurvivorGame.world;

import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.data.DataLoader;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.ability_objects.AbilityObject;
import com.test.SurvivorGame.entity.drops.DroppedObject;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.screen.GamePlayScreen;
import com.test.SurvivorGame.world.maps.GameMap;
import com.test.SurvivorGame.world.system.*;

import java.util.List;

/**
 * Verwaltet den gesamten Spielzustand während einer Spielrunde.
 *
 * Die World dient als zentrale Verwaltungsklasse (Fassade) für alle
 * Spielsysteme. Sie speichert den Spieler, die Karte sowie sämtliche
 * Systeme (Spawn-, Kollisions-, Fähigkeiten-, Drop- und Timer-System)
 * und koordiniert deren Zusammenarbeit.
 */
public class World {

    // Der Spieler der aktuellen Spielrunde
    private final Player player;

    // Verwaltet das Erzeugen neuer Gegner
    private final SpawnSystem spawnSystem;

    // Verwaltet alle aktiven Fähigkeiten und Projektile
    private final AbilitySystem abilitySystem = new AbilitySystem();

    // Prüft Kollisionen zwischen Gegnern, Spieler und Fähigkeiten
    private final CollisionSystem collisionSystem;

    // Verwaltet alle gedroppten Objekte (z.B. Erfahrungskugeln)
    private final DropSystem dropSystem = new DropSystem();

    // Misst die bisherige Überlebenszeit des Spielers
    private final RunTimerSystem runTimerSystem;

    // Name der aktuell geladenen Karte
    private final String map;

    // Verantwortlich für das Speichern der Spielstände
    private final DataLoader dataLoader;

    // Enthält Lebenspunkte, Erfahrung, Statistiken usw.
    private final PlayerState playerState;

    // Aktuelle Spielkarte
    private final GameMap gameMap;

    public World(PlayerState playerState, GameMap gameMap,
                 String map, DataLoader dataLoader, GamePlayScreen gamePlayScreen) {

        this.map = map;
        this.gameMap = gameMap;
        this.dataLoader = dataLoader;
        this.playerState = playerState;

        // Erzeugt den Spieler
        player = new Player(playerState);

        // Initialisiert alle benötigten Spielsysteme
        collisionSystem = new CollisionSystem(player);
        runTimerSystem = new RunTimerSystem(playerState.getPlayerData());
        spawnSystem = new SpawnSystem(this, gameMap, gamePlayScreen);
    }

    /**
     * Aktualisiert die komplette Spielwelt.
     * Diese Methode wird einmal pro Frame aufgerufen.
     */
    public void update(float deltaTime) {

        // Passive Lebensregeneration abhängig vom Healing-Wert.
        playerState.heal(
            deltaTime *
                playerState.getPlayerStats().getStat(StatType.HEALING)
        );

        // Aktualisiert den Spieler.
        player.update(deltaTime, gameMap);

        // Aktualisiert alle Spielsysteme.
        spawnSystem.update(deltaTime, gameMap);
        runTimerSystem.update(deltaTime);
        abilitySystem.update(deltaTime, gameMap);
        dropSystem.update(deltaTime, gameMap);

        // Prüft Kollisionen zwischen Gegnern und Fähigkeiten.
        collisionSystem.checkCollisions(
            getEnemies(),
            getAbilityObjects()
        );
    }

    /**
     * Gibt den Spieler zurück.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gibt die bisherige Überlebenszeit zurück.
     */
    public float getSurvivalTime() {
        return runTimerSystem.getSurvivalTime();
    }

    /**
     * Gibt alle aktuell existierenden Gegner zurück.
     * Die World stellt dabei lediglich eine Fassade für das SpawnSystem dar.
     */
    public List<Enemy> getEnemies() {
        return spawnSystem.getEnemies();
    }

    /**
     * Fügt ein neues Drop-Objekt der Spielwelt hinzu.
     */
    public void addDrop(DroppedObject drop) {
        dropSystem.addDrop(drop);
    }

    /**
     * Gibt alle aktuell vorhandenen Drop-Objekte zurück.
     */
    public List<DroppedObject> getDroppedObjects() {
        return dropSystem.getDroppedObjects();
    }

    /**
     * Speichert den aktuellen Spielstand.
     */
    public void saveGame() {
        dataLoader.savePlayerData(
            map,
            player.getPlayerState().getPlayerData()
        );
    }

    /**
     * Pausiert oder startet den Überlebens-Timer.
     */
    public void setSurvivalTimePaused(boolean paused) {
        runTimerSystem.setSurvivalTimePaused(paused);
    }

    /**
     * Gibt zurück, ob der Überlebens-Timer pausiert ist.
     */
    public boolean isSurvivalTimePaused() {
        return runTimerSystem.isSurvivalTimePaused();
    }

    /**
     * Liefert Zugriff auf das SpawnSystem.
     */
    public SpawnSystem getSpawnSystem() {
        return spawnSystem;
    }

    /**
     * Gibt die seit Spielbeginn vergangene Zeit zurück.
     */
    public float getPassedTime() {
        return runTimerSystem.getPassedTime();
    }

    /**
     * Gibt alle aktuell aktiven Fähigkeiten bzw. Projektile zurück.
     */
    public List<AbilityObject> getAbilityObjects() {
        return abilitySystem.getAbilityObjects();
    }

    /**
     * Fügt ein neues AbilityObject (z.B. Projektil oder Flächenangriff)
     * der Spielwelt hinzu.
     */
    public void addAbility(AbilityObject abilityObject) {
        abilitySystem.addAbility(abilityObject);
    }
}
