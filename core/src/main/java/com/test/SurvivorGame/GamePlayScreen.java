package com.test.SurvivorGame;

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

public class GamePlayScreen extends ScreenAdapter {

    private final float screenWidth = 16f;
    private final float screenHeight = 9f;  // ACHTUNG! die x und y der Viewport Klasse heißt worldWidth / worldHeight
                                            //  habs nd so genannt, weil verwirrend sein wird, wenn wir eine map der "world" haben

    private final Viewport viewport = new FitViewport(screenWidth,screenHeight); // WICHTIG wir müssen entscheiden welches viewport, weil andre mögen evtl. advantages bringen

    private final Batch batch;

    private final Texture playerTexture = new Texture(Gdx.files.internal("Placeholder/PlayerPH.png"));
    private final Player player = new Player(screenWidth / 2, screenHeight / 2, playerTexture); //textur wird glaub von links unten gemessen, deshalb isser so weit oben rechts
    private Vector2 playerMoveDirection = new Vector2();
    GamePlayScreen(Main game)
    {
        this.batch = game.getBatch();
    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height, true);   // passt sich der Bildschirmgröße an
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

        updateLogic(deltaTime); //bis jetzt nur PlayerUpdate

        ScreenUtils.clear(Color.BLUE); // cleaner wenn man vor dem Screen den Hintergrund "wiped"

        viewport.apply();   // Ab jetzt gelten die Einstellungen von DIESEM Screen z.b. resize()
        batch.setProjectionMatrix(viewport.getCamera().combined); // sagt dem SpriteBatch wie er die 2D Welt auf den Bildschirm projizieren soll.
        batch.begin();

        player.draw(batch);

        batch.end();
    }

    private void updateLogic(float deltaTime)
    {

        player.update(deltaTime);
    }

    @Override
    public void dispose()
    {
        playerTexture.dispose();
    }
}
