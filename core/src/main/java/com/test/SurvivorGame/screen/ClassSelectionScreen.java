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
import com.test.SurvivorGame.core.data.PlayerData;

public class ClassSelectionScreen extends ScreenAdapter {

    private final Main main;
    private final DataLoader dataLoader;

    private final Stage stage;
    private final Skin skin;

    private final String map;

    public ClassSelectionScreen(Main main, DataLoader dataLoader, String map) {
        this.main = main;
        this.dataLoader = dataLoader;
        this.map = map;

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Gdx.input.setInputProcessor(stage);

        Label title = new Label("SELECT CLASS", skin);

        TextButton aeromancerButton = new TextButton("Aeromancer", skin);
        TextButton geomancerButton = new TextButton("Geomancer", skin);
        TextButton hydromancerButton = new TextButton("Hydromancer", skin);
        TextButton pyromancerButton = new TextButton("Pyromancer", skin);
        TextButton backButton = new TextButton("Back", skin);

        aeromancerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectClass("aeromancer");
            }
        });

        geomancerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectClass("geomancer");
            }
        });

        hydromancerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectClass("hydromancer");
            }
        });

        pyromancerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectClass("pyromancer");
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new MapSelectionScreen(main, dataLoader));
                dispose();
            }
        });

        Table table = new Table();
        table.setFillParent(true);

        table.add(title).padBottom(60);
        table.row();

        table.add(aeromancerButton).width(250).height(60).pad(10);
        table.row();

        table.add(geomancerButton).width(250).height(60).pad(10);
        table.row();

        table.add(hydromancerButton).width(250).height(60).pad(10);
        table.row();

        table.add(pyromancerButton).width(250).height(60).pad(10);
        table.row();

        table.add(backButton).width(250).height(60).padTop(40);

        stage.addActor(table);
    }

    private void selectClass(String classId) {
        // Easter egg:
        boolean shouldBeMerz = Math.random() < 0.03;
        if (shouldBeMerz) classId = "merzmancer";

        System.out.println("Selected class: " + classId);
        PlayerData playerData = new PlayerData();
        playerData.playerClass = classId;
        dataLoader.savePlayerData(map, playerData);

        main.setScreen(new GamePlayScreen(main, dataLoader, map));
        dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.08f, 0.08f, 0.08f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
