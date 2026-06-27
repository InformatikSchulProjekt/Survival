package com.test.SurvivorGame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.test.SurvivorGame.Main;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.Rendering.Renderer;
import com.test.SurvivorGame.core.data.DataLoader;
import com.test.SurvivorGame.core.data.PlayerData;
import com.test.SurvivorGame.world.World;

public class GamePlayScreen extends ScreenAdapter {

    public final float screenWidth = 16f;

    public final float screenHeight = 9f;  // ACHTUNG! die x und y der Viewport Klasse heißt worldWidth / worldHeight
                                            //  habs nd so genannt, weil verwirrend sein wird, wenn wir eine map der "world" haben
    private final Renderer renderer;

    private World world;

    private Vector2 playerMoveDirection = new Vector2();

    public GamePlayScreen(Main game, DataLoader dataLoader)
    {
        // "TestMap" ist obv. temporär da soll dann die ausgewählte Map rein.
        PlayerData playerData = dataLoader.getPlayerData("TestMap");
        PlayerState playerState = new PlayerState(playerData);

        this.world = new World(screenWidth, screenHeight, playerState);
        this.renderer = new Renderer(game.getBatch(), screenWidth, screenHeight);
    }

    @Override
    public void resize(int width, int height)
    {
        renderer.resize(width, height);   // passt sich der Bildschirmgröße an
    }

    private void processInput() // sollte später eigene klasse werde, oder? hier nur zum, rumtesten ig
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
//        if(Gdx.input.isKeyJustPressed(Input.Keys.T))
//        {
//            System.out.println();
//            System.out.println(world.getPlayer().getLevel()+" Level");
//            world.getPlayer().giveXP(1);
//            dataLoader.savePlayerData("TestMap", world.getPlayer().getPlayerData());
//
//        } // TEST KEY FOR TESTING DATA

        if(!playerMoveDirection.isZero()) //wenns schräg geht normalisieren, aber wenn sich der Player nicht bewegt wird (x = 0,y = 0) / 0
        {
            playerMoveDirection.nor();
        }
        world.getPlayer().updateMoveDirection(playerMoveDirection);
    }

    @Override
    public void render(float deltaTime)
    {
        processInput();

        updateLogic(deltaTime); //bis jetzt nur PlayerUpdate, dafür er sich bewegt

        renderer.render(world.getPlayer(),deltaTime); //animationen
    }

    private void updateLogic(float deltaTime)
    {

        world.getPlayer().update(deltaTime);
    }

    @Override
    public void dispose()
    {
        renderer.dispose();
    }
}
