package com.test.SurvivorGame.world;

import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.data.DataLoader;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.AbilityObject;
import com.test.SurvivorGame.entity.drops.ChestObject;
import com.test.SurvivorGame.entity.drops.DroppedObject;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.world.maps.GameMap;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class World {

    private final Player player;

    private float damageTimer = 0f;
    private final float DamageInterval = 0.5f;

    private float survivalTime = 0f; // wie lange der aktuelle Run schon läuft, für den HUD-Timer

    float screenWidth, screenHeight; // nur für reset-test

    private ArrayList<AbilityObject> abilityObjects = new ArrayList<>();
    private ArrayList<DroppedObject> droppedObjects = new ArrayList<>();

    private SpawnManager spawnManager;

    private String map;
    private DataLoader dataLoader;

    public World(float screenWidth, float screenHeight, PlayerState playerState, GameMap gameMap, String map, DataLoader dataLoader)
    {
        player = new Player(playerState); // wo er reinspawnt
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight; // nur für reset-test

        spawnManager = new SpawnManager(this, gameMap);
        this.map = map;
        this.dataLoader = dataLoader;
    }

    public void update(float deltaTime, GameMap map)
    {
        survivalTime += deltaTime;
        System.out.println(survivalTime);

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
}
