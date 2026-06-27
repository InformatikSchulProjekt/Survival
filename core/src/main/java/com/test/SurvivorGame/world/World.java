package com.test.SurvivorGame.world;

import com.badlogic.gdx.math.MathUtils;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.enemy.Enemy1;
import com.test.SurvivorGame.world.maps.GameMap;

import java.util.ArrayList;

public class World {

    private final Player player;

    private ArrayList<Enemy1> enemies = new ArrayList<>();

    private float spawnTimer;

    private float spawnInterval = 2f;

    private float dmgTaken;

    private float damageTimer = 0f;

    private float damageInterval = 1f;

    float screenWidth, screenHeight; // nur für reset-test

    public World(float screenWidth, float screenHeight)
    {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight; // nur für reset-test

        player = new Player(screenWidth / 2, screenHeight / 2); // wo er reinspawnt
    }

    private void spawnEnemy()
    {
        float distance = MathUtils.random(10f, 15f); // zufälliger radius

        float angle = MathUtils.random(0f, 360f); //zufällige richtung

        float x = player.getCenter().x +
            MathUtils.cosDeg(angle) * distance;

        float y = player.getCenter().y +
            MathUtils.sinDeg(angle) * distance;

        enemies.add(new Enemy1(x, y, player));
    }

    public void update(float deltaTime, GameMap map)
    {
        player.update(deltaTime);

        spawnTimer += deltaTime;

        if(spawnTimer >= spawnInterval)
        {
            spawnEnemy();
            spawnTimer = 0;
        }

        for(Enemy1 enemy : enemies) // enemy1 Update
        {
            enemy.update(deltaTime);
        }

        checkCollisions(deltaTime);

        if(!player.isAlive()) // nur für reset-test, bis wir halt wissen was bei tod passiert
        {
            resetWorld();
        }

    }

    private void resetWorld()
    {
        enemies.clear();

        spawnTimer = 0;
        damageTimer = 0;

        player.reset(screenWidth / 2, screenHeight / 2);
    }

    private void checkCollisions(float deltaTime) //überprüft collisions mit der overlap methode von GameObjects
    {
        damageTimer += deltaTime; //addiert den timer mit der sekunde seit dem letzten frame vergangen ist

        if(damageTimer >= damageInterval) //wenn der timer das interval erreicht:
        {
            float dmgTaken = 0;

            for(Enemy1 enemy : enemies)
            {
                if(player.overlaps(enemy))
                {
                    dmgTaken += enemy.getDamagePerSecond();
                }
            }

            if(dmgTaken > 0)
            {
                player.takeDamage(dmgTaken);
            }

            damageTimer = 0;
        }
    }

    public Player getPlayer()
    {
        return player;
    }

    public ArrayList<Enemy1> getEnemies1()
    {
        return enemies;
    }

    public void dispose()
    {

    }


}
