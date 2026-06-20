package com.test.SurvivorGame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.test.SurvivorGame.Main;
import com.test.SurvivorGame.core.Rendering.Renderer;
import com.test.SurvivorGame.core.data.DataLoader;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.world.maps.GameMap;

public class GamePlayScreen extends ScreenAdapter {

    private final float screenWidth = 16f;

    private final float screenHeight = 9f;  // ACHTUNG! die x und y der Viewport Klasse heißt worldWidth / worldHeight
                                            //  habs nd so genannt, weil verwirrend sein wird, wenn wir eine map der "world" haben
    private final Renderer renderer;

    private DataLoader dataLoader;


    private final Player player = new Player(screenWidth / 2, screenHeight / 2); //textur wird glaub von links unten gemessen, deshalb isser so weit oben rechts

    private Vector2 playerMoveDirection = new Vector2();
    private final GameMap map = new GameMap();

    public GamePlayScreen(Main game, DataLoader dataLoader)
    {
        // testing für data:

        this.renderer = new Renderer(game.getBatch(), screenWidth, screenHeight);
        this.dataLoader = dataLoader;
        player.setPlayerData(dataLoader.getPlayerData("TestMap"));
    }

    @Override
    public void resize(int width, int height)
    {
        renderer.resize(width, height);   // passt sich der Bildschirmgröße an
    }

    private void processInput() // sollte später eigene klasse werde oder? hier nur zum rumtesten ig
    {
        playerMoveDirection.setZero(); // damits nicht wächst
        if(Gdx.input.isKeyPressed(Input.Keys.W))
        {
            playerMoveDirection.y += 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S))
        {
            playerMoveDirection.y -= 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D))
        {
            playerMoveDirection.x += 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A))
        {
            playerMoveDirection.x -= 1;
        }
        // TEST KEY
        if(Gdx.input.isKeyJustPressed(Input.Keys.T))
        {
            System.out.println();
            System.out.println(player.getLevel()+" Level");
            player.giveXP(1);
            dataLoader.savePlayerData("TestMap", player.getPlayerData());

        } // TEST KEY FOR TESTING DATA

        if(!playerMoveDirection.isZero()) //wenns schräg geht normalisieren, aber wenn sich der Player nicht bewegt wird (x = 0,y = 0) / 0
        {
            playerMoveDirection.nor();
        }
        player.updateMoveDirection(playerMoveDirection);
    }

    @Override
    public void render(float deltaTime)
    {
        processInput();

        updateLogic(deltaTime,map); //bis jetzt nur PlayerUpdate
        renderer.render(map,player,deltaTime);

    }

    private void updateLogic(float deltaTime,GameMap map)
    {

        player.update(deltaTime,map);
    }

    @Override
    public void dispose()
    {
        renderer.dispose();
        map.dispose();
    }
}
