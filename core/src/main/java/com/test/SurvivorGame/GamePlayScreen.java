package com.test.SurvivorGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GamePlayScreen extends ScreenAdapter {
    private final Batch batch;
    private final Viewport viewport = new FitViewport(16f,9f); // WICHTIG wir müssen entscheiden welches viewport, weil andre mögen evtl. advantages bringen

    private final Texture playerTexture = new Texture(Gdx.files.internal("Placeholder/PlayerPH.png"));

    GamePlayScreen(Main game)
    {
        this.batch = game.getBatch();
    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height, true);   // passt sich der Bildschirmgröße an
    }

    @Override
    public void render(float delta)
    {
        ScreenUtils.clear(Color.BLUE); // cleaner wenn man vor dem Screen den Hintergrund "wiped"

        viewport.apply();   // Ab jetzt gelten die Einstellungen von DIESEM Screen z.b. resize()
        batch.setProjectionMatrix(viewport.getCamera().combined); // sagt dem SpriteBatch wie er die 2D Welt auf den Bildschirm projizieren soll.
        batch.begin();

        batch.draw(playerTexture, 7, 3.5f, 1, 3); // ungefähr mittig

        batch.end();
    }

    @Override
    public void dispose()
    {

    }
}
