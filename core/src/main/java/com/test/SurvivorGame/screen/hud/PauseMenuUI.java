package com.test.SurvivorGame.screen.hud;

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

public class PauseMenuUI {

    private final ShapeRenderer shapeRenderer;
    private final OrthographicCamera camera = new OrthographicCamera();

    private final Stage stage;
    private final Skin skin;

    // Buttons
    private final TextButton resumeButton;
    private final TextButton backToMenuButton;
    private final TextButton giveUpButton;
    private final TextButton settingsButton;
    private final TextButton inventoryButton;
    private final TextButton abilitiesButton;

    private Runnable resumeListener;
    private Runnable backToMenuListener;
    private Runnable giveUpListener;
    private Runnable settingsListener;
    private Runnable inventoryListener;
    private Runnable abilitiesListener;

    public PauseMenuUI(ShapeRenderer shapeRenderer) {

        this.shapeRenderer = shapeRenderer;

        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(new ScreenViewport());

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Buttons erstellen
        resumeButton = new TextButton("Resume", skin);
        backToMenuButton = new TextButton("Back to Menu", skin);
        giveUpButton = new TextButton("Give Up", skin);
        settingsButton = new TextButton("Settings", skin);
        inventoryButton = new TextButton("Inventory", skin);
        abilitiesButton = new TextButton("Abilities", skin);

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (resumeListener != null) {
                    resumeListener.run();
                }
            }
        });

        backToMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (backToMenuListener != null) {
                    backToMenuListener.run();
                }
            }
        });

        giveUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (giveUpListener != null) {
                    giveUpListener.run();
                }
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (settingsListener != null) {
                    settingsListener.run();
                }
            }
        });

        inventoryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (inventoryListener != null) {
                    inventoryListener.run();
                }
            }
        });

        abilitiesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (abilitiesListener != null) {
                    abilitiesListener.run();
                }
            }
        });

        // Überschrift
        Label title = new Label("PAUSE", skin);

        // Tabelle für das Layout
        Table table = new Table();
        table.setFillParent(true);

        table.add(title).padBottom(30);
        table.row();

        table.add(resumeButton).width(250).pad(5);
        table.row();

        table.add(backToMenuButton).width(250).pad(5);
        table.row();

        table.add(giveUpButton).width(250).pad(5);
        table.row();

        table.add(settingsButton).width(250).pad(5);
        table.row();

        table.add(inventoryButton).width(250).pad(5);
        table.row();

        table.add(abilitiesButton).width(250).pad(5);

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
        shapeRenderer.setColor(0, 0, 0, 0.6f);
        shapeRenderer.rect(0, 0, camera.viewportWidth, camera.viewportHeight);
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        stage.act();
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }

    public void setResumeListener(Runnable listener) {
        this.resumeListener = listener;
    }

    public void setBackToMenuListener(Runnable listener) {
        this.backToMenuListener = listener;
    }

    public void setGiveUpListener(Runnable listener)
    {
        this.giveUpListener = listener;
    }

    public void setSettingsListener(Runnable listener)
    {
        this.settingsListener = listener;
    }

    public void setInventoryListener(Runnable listener)
    {
        this.inventoryListener = listener;
    }

    public void setAbilitiesListener(Runnable listener)
    {
        this.abilitiesListener = listener;
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
