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
import com.test.SurvivorGame.world.system.*;

import java.util.List;

public class World {
    private final Player player;
    private final SpawnSystem spawnSystem;
    private final AbilitySystem abilitySystem = new AbilitySystem();
    private final CollisionSystem collisionSystem;
    private final DropSystem dropSystem = new DropSystem();
    private final RunTimerSystem runTimerSystem;

    private final String map;
    private final DataLoader dataLoader;
    private final PlayerState playerState;
    private final GameMap gameMap;

    public World(PlayerState playerState, GameMap gameMap,
                 String map, DataLoader dataLoader, GamePlayScreen gamePlayScreen) {
        this.map = map;
        this.gameMap = gameMap;
        this.dataLoader = dataLoader;
        this.playerState = playerState;

        player = new Player(playerState);
        collisionSystem = new CollisionSystem(player);
        runTimerSystem = new RunTimerSystem(playerState.getPlayerData());
        spawnSystem = new SpawnSystem(this, gameMap, gamePlayScreen);
    }

    public void update(float deltaTime) {
        // Passive Health Regen:
        playerState.heal(deltaTime * playerState.getPlayerStats().getStat(StatType.HEALING));

        player.update(deltaTime, gameMap);

        spawnSystem.update(deltaTime, gameMap);
        runTimerSystem.update(deltaTime);
        abilitySystem.update(deltaTime, gameMap);
        dropSystem.update(deltaTime, gameMap);
        collisionSystem.checkCollisions(getEnemies(), getAbilityObjects());
    }

    public Player getPlayer() {
        return player;
    }

    public float getSurvivalTime() {
        return runTimerSystem.getSurvivalTime();
    }

    public List<Enemy> getEnemies() {
        return spawnSystem.getEnemies(); // Fassade für getEnemies()
    }

    public void addDrop(DroppedObject drop) {
        dropSystem.addDrop(drop);
    }

    public List<DroppedObject> getDroppedObjects() {
        return dropSystem.getDroppedObjects();
    }

    public void saveGame() {
        dataLoader.savePlayerData(map, player.getPlayerState().getPlayerData());
    }

    public void setSurvivalTimePaused(boolean paused) {
        runTimerSystem.setSurvivalTimePaused(paused);
    }

    public float getPassedTime() {
        return runTimerSystem.getPassedTime();
    }

    public List<AbilityObject> getAbilityObjects() {
        return abilitySystem.getAbilityObjects();
    }

    public void addAbility(AbilityObject abilityObject) {
        abilitySystem.addAbility(abilityObject);
    }
}
