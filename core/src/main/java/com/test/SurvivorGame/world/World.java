package com.test.SurvivorGame.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.enemy.enemy1;
import com.test.SurvivorGame.screen.GamePlayScreen;

import java.util.ArrayList;

public class World {

    private final Player player;

    private ArrayList<enemy1> enemies = new ArrayList<>();

    private float spawnTimer;

    private float spawnInterval = 2f;

    public World(float screenWidth, float screenHeight)
    {
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

        enemies.add(new enemy1(x, y, player));
    }

    public void update(float deltaTime)
    {
        player.update(deltaTime);

        spawnTimer += deltaTime;

        if(spawnTimer >= spawnInterval)
        {
            spawnEnemy();
            spawnTimer = 0;
        }

        for(enemy1 enemy : enemies)
        {
            enemy.update(deltaTime);
        }
    }

    private void checkCollisions()
    {
        //wenn gegner hinzugefügt werden kann man hier die enemy list in player.overlaps durchgehen
    }

    public Player getPlayer()
    {
        return player;
    }

    public ArrayList<enemy1> getEnemies()
    {
        return enemies;
    }

    public void dispose()
    {

    }


}
