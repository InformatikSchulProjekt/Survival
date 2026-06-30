package com.test.SurvivorGame.world;

import com.badlogic.gdx.math.MathUtils;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.AbilityObject;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.world.maps.GameMap;

import java.util.ArrayList;

public class World {

    private Player player;

    private ArrayList<Enemy> enemies = new ArrayList<>();

    private float spawnTimer;

    private float spawnInterval = 2f;

    private float dmgTaken;

    private float damageTimer = 0f;

    private float DamageInterval = 0.5f;

    float screenWidth, screenHeight; // nur für reset-test

    private ArrayList<AbilityObject> abilityObjects = new ArrayList<>();

    public World(float screenWidth, float screenHeight, PlayerState playerState)
    {
        player = new Player(playerState); // wo er reinspawnt
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight; // nur für reset-test
    }

    private void spawnEnemy()
    {
        float distance = MathUtils.random(10f, 15f); // zufälliger radius

        float angle = MathUtils.random(0f, 360f); //zufällige richtung

        float x = player.getCenter().x +
            MathUtils.cosDeg(angle) * distance;

        float y = player.getCenter().y +
            MathUtils.sinDeg(angle) * distance;

        enemies.add(new Enemy(x, y, player));
    }

    public void update(float deltaTime, GameMap map)
    {
        player.update(deltaTime,map);

        spawnTimer += deltaTime;

        if(spawnTimer >= spawnInterval)
        {
            spawnEnemy();
            spawnTimer = 0;
        }

        for(int i = enemies.size() - 1; i >= 0; i--) // ability objects werden nacheinander durchgegangen
        {
            Enemy enemy = enemies.get(i);

            enemy.update(deltaTime, map);

            if(enemy.isDead())
            {
                enemies.remove(i);
            }
        }

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
        enemies.clear();

        spawnTimer = 0;
        damageTimer = 0;

        player.reset(screenWidth / 2, screenHeight / 2);
    }

    private void checkPlayerCollisions(float deltaTime) //überprüft collisions mit der overlap methode von GameObjects
    {
        damageTimer += deltaTime; //addiert den timer mit der sekunde seit dem letzten frame vergangen ist

        if(damageTimer >= DamageInterval) //wenn der timer das interval erreicht:
        {
            float dmgTaken = 0;

            for(Enemy enemy : enemies)
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
            for(Enemy enemy : enemies)
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
        return enemies;
    }

    public void addAbillity(AbilityObject abillityObject)
    {
        abilityObjects.add(abillityObject);
    }

    public ArrayList<AbilityObject> getAbilityObjects()
    {
        return abilityObjects;
    }


}
