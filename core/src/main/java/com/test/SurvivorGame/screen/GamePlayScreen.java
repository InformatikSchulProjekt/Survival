package com.test.SurvivorGame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.Main;
import com.test.SurvivorGame.core.data.DataLoader;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.world.World;

public class GamePlayScreen extends ScreenAdapter {

    public final float screenWidth = 16f;
    public final float screenHeight = 9f;  // ACHTUNG! die x und y der Viewport Klasse heißt worldWidth / worldHeight
                                            //  habs nd so genannt, weil verwirrend sein wird, wenn wir eine map der "world" haben


    private DataLoader dataLoader;

    private final Viewport viewport = new FitViewport(screenWidth,screenHeight); // WICHTIG wir müssen entscheiden welches viewport, weil andre mögen evtl. advantages bringen

    private final Batch batch;

    private World world;

    private Vector2 playerMoveDirection = new Vector2();
    public GamePlayScreen(Main game, DataLoader dataLoader)
    {
        this.batch = game.getBatch();

        world = new World(this);
        // testing für data:
        this.dataLoader = dataLoader;
        world.player.setPlayerData(dataLoader.getPlayerData("TestMap"));
    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height, true);   // passt sich der Bildschirmgröße an
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
        if(Gdx.input.isKeyJustPressed(Input.Keys.T))
        {
            System.out.println();
            System.out.println(world.player.getLevel()+" Level");
            world.player.giveXP(1);
            dataLoader.savePlayerData("TestMap", world.player.getPlayerData());

        } // TEST KEY FOR TESTING DATA

        if(!playerMoveDirection.isZero()) //wenns schräg geht normalisieren, aber wenn sich der Player nicht bewegt wird (x = 0,y = 0) / 0
        {
            playerMoveDirection.nor();
        }
        world.player.updateMoveDirection(playerMoveDirection);
    }

    @Override
    public void render(float deltaTime)
    {
        processInput();

        world.update(deltaTime); //bis jetzt nur PlayerUpdate

        ScreenUtils.clear(Color.BLUE); // cleaner wenn man vor dem Screen den Hintergrund "wiped"

        viewport.apply();   // Ab jetzt gelten die Einstellungen von DIESEM Screen z.b. resize()
        batch.setProjectionMatrix(viewport.getCamera().combined); // sagt dem SpriteBatch wie er die 2D Welt auf den Bildschirm projizieren soll.
        batch.begin();

        world.render(batch);

        batch.end();
    }


    @Override
    public void dispose()
    {
        world.dispose();
    }
}
