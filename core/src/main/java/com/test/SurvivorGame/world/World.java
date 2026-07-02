package com.test.SurvivorGame.world;

import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.AbilityObject;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.world.maps.GameMap;

import java.util.ArrayList;

public class World {

    private final Player player;

    private float damageTimer = 0f;
    private final float DamageInterval = 0.5f;

    float screenWidth, screenHeight; // nur für reset-test

    private ArrayList<AbilityObject> abilityObjects = new ArrayList<>();

    private SpawnManager spawnManager;

    public World(float screenWidth, float screenHeight, PlayerState playerState)
    {
        player = new Player(playerState); // wo er reinspawnt
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight; // nur für reset-test

        spawnManager = new SpawnManager(player);
    }


    public void update(float deltaTime, GameMap map)
    {
        spawnManager.update(deltaTime, map);

        checkAbilityCollision(deltaTime);
        checkPlayerCollisions(deltaTime);

        if(!player.isAlive()) // nur für reset-test, bis wir halt wissen was bei tod passiert
        {
            resetWorld();
        }

        for(int i = abilityObjects.size() - 1; i >= 0; i--) // ability objects werden nacheinander durchgegangen
        {
            AbilityObject abillityObject = abilityObjects.get(i);

            abillityObject.update(deltaTime, map);

            if(abillityObject.getExpired())
            {
                abilityObjects.remove(i);
            }
        }

    }

    private void resetWorld()
    {
        spawnManager.getEnemies().clear();

        spawnManager.resetSpawn();

        damageTimer = 0;

        player.reset(screenWidth / 2, screenHeight / 2);
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


}
