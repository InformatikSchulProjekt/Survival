package com.test.SurvivorGame.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.screen.GamePlayScreen;

public class World {
    private GamePlayScreen gamePlayScreen;

    private Texture playerTexture = new Texture(Gdx.files.internal("Placeholder/PlayerPH.png")); // new Texture() später mit texture loader ykyk
    public final Player player;

    public World(GamePlayScreen gamePlayScreen)
    {
        this.gamePlayScreen = gamePlayScreen;
        player = new Player(gamePlayScreen.screenWidth / 2, gamePlayScreen.screenHeight / 2, playerTexture);

    }

    public void update(float deltaTime)
    {
        player.update(deltaTime);
    }

    private void checkCollisions()
    {
        //wenn gegner hinzugefügt werden kann man hier die enemy list in player.overlaps durchgehen
    }

    public void render(Batch batch)
    {
        player.draw(batch);
    }

    public void dispose()
    {
        playerTexture.dispose();
    }


}
