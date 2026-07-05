package com.test.SurvivorGame.screen.HuD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

public class PauseMenuRenderer {

    private final Batch batch;
    private final ShapeRenderer shapeRenderer;
    private final BitmapFont font;
    private final OrthographicCamera camera = new OrthographicCamera();

    public PauseMenuRenderer(Batch batch, ShapeRenderer shapeRenderer) {
        this.batch = batch;
        this.shapeRenderer = shapeRenderer;

        font = new BitmapFont();
        font.getData().setScale(2f);

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.update();
    }

    public void render() {

        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        float w = camera.viewportWidth;
        float h = camera.viewportHeight;

        // dunkler Hintergrund
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0,0,0,0.6f);
        shapeRenderer.rect(0,0,w,h);

        // Menübox
        shapeRenderer.setColor(0.2f,0.2f,0.2f,1f);
        shapeRenderer.rect(w/2-150,h/2-120,300,240);
        shapeRenderer.end();

        batch.begin();

        font.draw(batch,"PAUSE",0,h/2+70,w, Align.center,false);
        font.draw(batch,"ESC - Weiter",0,h/2+20,w,Align.center,false);
        font.draw(batch,"M - Hauptmenü",0,h/2-20,w,Align.center,false);
        font.draw(batch,"Q - Spiel verlassen",0,h/2-60,w,Align.center,false);

        batch.end();
    }

    public void dispose() {
        font.dispose();
    }
}
