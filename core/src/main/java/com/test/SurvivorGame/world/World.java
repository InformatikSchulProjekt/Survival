package com.test.SurvivorGame.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.screen.GamePlayScreen;

public class World {

    private final Player player;

    public World(float screenWidth, float screenHeight, PlayerState playerState)
    {
        player = new Player(playerState); // wo er reinspawnt

    }

    public void update(float deltaTime)
    {
        player.update(deltaTime);
    }

    private void checkCollisions()
    {
        //wenn gegner hinzugefügt werden kann man hier die enemy list in player.overlaps durchgehen
    }

    public Player getPlayer()
    {
        return player;
    }

    public void render(Batch batch)
    {
        player.draw(batch);
    }

    public void dispose()
    {

    }


}
