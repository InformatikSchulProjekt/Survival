package com.test.SurvivorGame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.test.SurvivorGame.Main;
import com.test.SurvivorGame.core.data.BestRunData;
import com.test.SurvivorGame.core.data.DataLoader;

public class HighscoreScreen extends ScreenAdapter {

    private final Main main;
    private final DataLoader dataLoader;

    private final Stage stage;
    private final Skin skin;

    public HighscoreScreen(Main main, DataLoader dataLoader) {

        this.main = main;
        this.dataLoader = dataLoader;

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        root.setFillParent(true);

        Label title = new Label("Highscore", skin);
        title.setFontScale(2f);

        root.add(title).padBottom(40);
        root.row();

        BestRunData bestRun = dataLoader.getBestRun();

        Table card = new Table(skin);
        card.defaults().pad(6);

        if (bestRun == null) {

            Label emptyLabel = new Label("no run completed", skin);
            card.add(emptyLabel).padTop(10);

        } else {

            Label timeLabel = new Label("best Time: " + formatTime(bestRun.survivalTime), skin);
            timeLabel.setFontScale(1.4f);

            Label mapLabel = new Label("Map: " + bestRun.mapName, skin);
            Label classLabel = new Label("Class: " + bestRun.playerClassName, skin);
            Label killsLabel = new Label("Kills: " + bestRun.enemiesKilled, skin);
            Label spellsLabel = new Label("Abilitys: " + formatSpells(bestRun.spellNames), skin);
            spellsLabel.setWrap(true);
            spellsLabel.setAlignment(Align.center);

            card.add(timeLabel).padTop(10);
            card.row();

            card.add(mapLabel);
            card.row();

            card.add(classLabel);
            card.row();

            card.add(killsLabel);
            card.row();

            card.add(spellsLabel).width(400);
            card.row();
        }

        root.add(card).width(450).padBottom(40);
        root.row();

        TextButton backButton = new TextButton("back", skin);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new TitleScreen(main, dataLoader));
                dispose();
            }
        });

        root.add(backButton)
            .width(250)
            .height(60)
            .pad(10);

        stage.addActor(root);
    }

    private String formatSpells(String[] spellNames) {
        if (spellNames == null || spellNames.length == 0) {
            return "-";
        }

        return String.join(", ", spellNames);
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

    private String formatTime(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;

        if (hours > 0) {
            return String.format("%d:%02d:%02d", hours, minutes, secs);
        }

        return String.format("%02d:%02d", minutes, secs);
    }
}
