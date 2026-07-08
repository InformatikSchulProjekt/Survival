package com.test.SurvivorGame.world;

import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.data.DataLoader;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.AbilityObject;
import com.test.SurvivorGame.entity.drops.DroppedObject;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.world.maps.GameMap;

import java.util.ArrayList;

public class World {

    private final Player player;

    private float damageTimer = 0f;
    private final float DamageInterval = 0.5f;

    private boolean survivalTimePaused = false;

    private float survivalTime = 0f; // wie lange der aktuelle Run schon läuft, für den HUD-Timer

    private float passedTime = 0f; // wie viel ECHTE Zeit seit anfang des Runs vergangen ist

    float screenWidth, screenHeight; // nur für reset-test

    private final ArrayList<AbilityObject> abilityObjects = new ArrayList<>();
    private final ArrayList<DroppedObject> droppedObjects = new ArrayList<>();

    private final SpawnManager spawnManager;

    private final String map;
    private final DataLoader dataLoader;
    private final PlayerState playerState;

    public World(float screenWidth, float screenHeight, PlayerState playerState, GameMap gameMap, String map, DataLoader dataLoader)
    {
        player = new Player(playerState); // wo er reinspawnt
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight; // nur für reset-test

        spawnManager = new SpawnManager(this, gameMap);
        this.map = map;
        this.dataLoader = dataLoader;
        this.playerState = playerState;
    }

    public void update(float deltaTime, GameMap map)
    {
        passedTime += deltaTime;

        // Passive Health Regen:
        playerState.heal(deltaTime*playerState.getPlayerStats().getStat(StatType.HEALING));

        if (!survivalTimePaused)
        {
            survivalTime += deltaTime;
        }

        player.update(deltaTime, map);
        spawnManager.update(deltaTime, map);

        checkAbilityCollision(deltaTime);
        checkPlayerCollisions(deltaTime);

        for(int i = abilityObjects.size() - 1; i >= 0; i--) // ability objects werden nacheinander durchgegangen
        {
            AbilityObject abillityObject = abilityObjects.get(i);

            abillityObject.update(deltaTime, map);

            if(abillityObject.getExpired())
            {
                abilityObjects.remove(i);
            }
        }

        for (int i = droppedObjects.size() - 1; i >= 0; i--) {
            DroppedObject drop = droppedObjects.get(i);

            drop.update(deltaTime, map);

            if (drop.isDespawned()) {
                droppedObjects.remove(i);
            }
        }

    }

    private void checkPlayerCollisions(float deltaTime) //überprüft collisions mit der overlap methode von GameObjects
    {
        damageTimer += deltaTime; //addiert den timer mit der sekunde seit dem letzten frame vergangen ist

        if(damageTimer >= DamageInterval) //wenn der timer das interval erreicht:
        {
            float dmgTaken = 0;

            for(Enemy enemy : spawnManager.getEnemies())
            {
                if(player.overlaps(enemy))
                {
                    dmgTaken += enemy.getDamage();
                }
            }

            if(dmgTaken > 0)
            {
                player.takeDamage(dmgTaken);
            }

            damageTimer = 0;
        }
    }

    private void checkAbilityCollision(float deltaTime)
    {

        for(AbilityObject ability : abilityObjects)
        {
            for(Enemy enemy : spawnManager.getEnemies())
            {
                if(ability.overlaps(enemy))
                {
                    ability.onHit(enemy);
                }
            }
        }
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

    public void addAbility(AbilityObject abilityObject)
    {
        abilityObjects.add(abilityObject);
    }

    public ArrayList<AbilityObject> getAbilityObjects()
    {
        return abilityObjects;
    }


    public void addDrop(DroppedObject drop) {
        droppedObjects.add(drop);
    }

    public ArrayList<DroppedObject> getDroppedObjects() {
        return droppedObjects;
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
}
