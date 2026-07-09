package com.test.SurvivorGame.screen.HuD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MapFinishedUI {

    private final ShapeRenderer shapeRenderer;
    private final OrthographicCamera camera = new OrthographicCamera();

    private final Stage stage;
    private final Skin skin;

    private final TextButton backToMenuButton;
    private final TextButton keepPlayingButton;

    private Runnable backToMenuListener;
    private Runnable keepPlayingListener;

    public MapFinishedUI(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;

        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Label title = new Label("Map Complete!", skin);

        backToMenuButton = new TextButton("Back to Menu", skin);
        keepPlayingButton = new TextButton("Keep Playing (Infinite Mode)", skin);

        backToMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (backToMenuListener != null) {
                    backToMenuListener.run();
                }
            }
        });

        keepPlayingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (keepPlayingListener != null) {
                    keepPlayingListener.run();
                }
            }
        });

        Table table = new Table();
        table.setFillParent(true);

        table.add(title).padBottom(30);
        table.row();

        table.add(backToMenuButton).width(300).pad(5);
        table.row();

        table.add(keepPlayingButton).width(300).pad(5);

        stage.addActor(table);
    }

    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        stage.getViewport().update(width, height, true);
    }

    public void render() {
        shapeRenderer.setProjectionMatrix(camera.combined);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.75f);
        shapeRenderer.rect(0, 0, camera.viewportWidth, camera.viewportHeight);
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        stage.act();
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }

    public void setBackToMenuListener(Runnable listener) {
        this.backToMenuListener = listener;
    }

    public void setKeepPlayingListener(Runnable listener) {
        this.keepPlayingListener = listener;
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
