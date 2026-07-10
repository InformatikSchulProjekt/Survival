package com.test.SurvivorGame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.test.SurvivorGame.Main;
import com.test.SurvivorGame.core.data.DataLoader;

public class TitleScreen extends ScreenAdapter {

    private final Main main;
    private final DataLoader dataLoader;

    private final Stage stage;
    private final Skin skin;

    public TitleScreen(Main main, DataLoader dataLoader)
    {
        this.main = main;
        this.dataLoader = dataLoader;

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Gdx.input.setInputProcessor(stage);

        Label title = new Label("Overrun - Survive the Hordes", skin);

        TextButton playButton = new TextButton("Play", skin);
        TextButton highscoreButton = new TextButton("Highscore", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                main.setScreen(new MapSelectionScreen(main, dataLoader));

                dispose();
            }
        });

        highscoreButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                main.setScreen(new HighscoreScreen(main, dataLoader));

                dispose();
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Table table = new Table();

        table.setFillParent(true);

        table.add(title).padBottom(60);

        table.row();

        table.add(playButton)
            .width(250)
            .height(60)
            .pad(10);

        table.row();

        table.add(highscoreButton)
            .width(250)
            .height(60)
            .pad(10);

        table.row();

        table.add(exitButton)
            .width(250)
            .height(60)
            .pad(10);

        stage.addActor(table);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0.08f,0.08f,0.08f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height)
    {
        stage.getViewport().update(width,height,true);
    }

    @Override
    public void dispose()
    {
        stage.dispose();
        skin.dispose();
    }
}
