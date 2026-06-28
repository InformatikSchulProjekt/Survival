package com.test.SurvivorGame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.test.SurvivorGame.Main;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.Rendering.Renderer;
import com.test.SurvivorGame.core.data.DataLoader;
import com.test.SurvivorGame.world.maps.GameMap;
import com.test.SurvivorGame.core.data.PlayerData;
import com.test.SurvivorGame.world.World;

public class GamePlayScreen extends ScreenAdapter {
    private DataLoader dataLoader;
    private PlayerState playerState;

    public final float screenWidth = 16f;

    public final float screenHeight = 9f;  // ACHTUNG! die x und y der Viewport Klasse heißt worldWidth / worldHeight
                                            //  habs nd so genannt, weil verwirrend sein wird, wenn wir eine map der "world" haben
    private final Renderer renderer;

    private World world;

    private Vector2 playerMoveDirection = new Vector2();
    private final GameMap map = new GameMap();

    public GamePlayScreen(Main game, DataLoader dataLoader)
    {
        // "TestMap" ist obv. temporär da soll dann die ausgewählte Map rein.
        this.dataLoader = dataLoader;
        PlayerData playerData = dataLoader.getPlayerData("TestMap");
        this.playerState = new PlayerState(playerData);

        this.world = new World(screenWidth, screenHeight, playerState);
        this.renderer = new Renderer(game.getBatch(), screenWidth, screenHeight, world);
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

        if(!playerMoveDirection.isZero()) //wenns schräg geht normalisieren, aber wenn sich der Player nicht bewegt wird (x = 0,y = 0) / 0
        {
            playerMoveDirection.nor();
        }
        world.getPlayer().updateMoveDirection(playerMoveDirection);

        // temporär um zu saven, weil es noch keine andere Optionen gibt.
        dataLoader.savePlayerData("TestMap", playerState.getPlayerData());
    }

    @Override
    public void render(float deltaTime)
    {
        processInput();

        updateLogic(deltaTime, map);

        renderer.render(map, world,deltaTime); //animationen
    }

    private void updateLogic(float deltaTime, GameMap map)
    {
        world.update(deltaTime, map);
    }

    @Override
    public void dispose()
    {
        renderer.dispose();
        map.dispose();
    }
}
