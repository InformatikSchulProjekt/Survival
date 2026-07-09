package com.test.SurvivorGame.world;

import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.data.DataLoader;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.AbilityObject;
import com.test.SurvivorGame.entity.drops.DroppedObject;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.screen.GamePlayScreen;
import com.test.SurvivorGame.world.maps.GameMap;
import com.test.SurvivorGame.world.system.AbilitySystem;
import com.test.SurvivorGame.world.system.CollisionSystem;
import com.test.SurvivorGame.world.system.DropSystem;

import java.util.ArrayList;
import java.util.List;

public class World {
    private boolean survivalTimePaused = false;

    private float survivalTime = 0f; // wie lange der aktuelle Run schon läuft, für den HUD-Timer

    private float passedTime = 0f; // wie viel ECHTE Zeit seit anfang des Runs vergangen istw

    private final Player player;
    private final SpawnManager spawnManager;
    private final AbilitySystem abilitySystem = new AbilitySystem();
    private final CollisionSystem collisionSystem = new CollisionSystem();
    private final DropSystem dropSystem = new DropSystem();

    private final String map;
    private final DataLoader dataLoader;
    private final PlayerState playerState;
    private final GameMap gameMap;

    public World(PlayerState playerState, GameMap gameMap,
                 String map, DataLoader dataLoader, GamePlayScreen gamePlayScreen)
    {
        player = new Player(playerState);

        this.map = map;
        this.gameMap = gameMap;
        this.dataLoader = dataLoader;
        this.playerState = playerState;

        spawnManager = new SpawnManager(this, gameMap, gamePlayScreen);
    }

    public void update(float deltaTime) {
        // Passive Health Regen:
        playerState.heal(deltaTime*playerState.getPlayerStats().getStat(StatType.HEALING));

        passedTime += deltaTime;
        if (!survivalTimePaused)
        {
            survivalTime += deltaTime;
        }

        player.update(deltaTime, gameMap);

        spawnManager.update(deltaTime, gameMap);
        abilitySystem.update(deltaTime, gameMap);
        dropSystem.update(deltaTime, gameMap);
        collisionSystem.checkCollisions(deltaTime, player, getEnemies(), getAbilityObjects());
    }

    public Player getPlayer()
    {
        return player;
    }

    public float getSurvivalTime()
    {
        return survivalTime;
    }

    public ArrayList<Enemy> getEnemies()
    {
        return spawnManager.getEnemies(); // Fassade für getEnemies()
    }

    public void addDrop(DroppedObject drop) {
        dropSystem.addDrop(drop);
    }

    public List<DroppedObject> getDroppedObjects() {
        return dropSystem.getDroppedObjects();
    }

    public void saveGame()
    {
        dataLoader.savePlayerData(map, player.getPlayerState().getPlayerData());
    }

    public boolean isSurvivalTimePaused()
    {
        return survivalTimePaused;
    }

    public void setSurvivalTimePaused(boolean paused) {
        this.survivalTimePaused = paused;
    }

    public float getPassedTime()
    {
        return passedTime;
    }

    public List<AbilityObject> getAbilityObjects() {
        return abilitySystem.getAbilityObjects();
    }

    public void addAbility(AbilityObject abilityObject) {
        abilitySystem.addAbility(abilityObject);
    }
}
