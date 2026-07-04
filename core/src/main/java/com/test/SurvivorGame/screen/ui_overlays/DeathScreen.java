package com.test.SurvivorGame.screen.ui_overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.test.SurvivorGame.Main;
import com.test.SurvivorGame.core.data.DataLoader;
import com.test.SurvivorGame.screen.GamePlayScreen;

public class DeathScreen extends ScreenAdapter {
    private Main game;
    private DataLoader dataLoader;
    private Stage stage;
    private Skin skin;
    private int score;

    // Der Konstruktor nimmt das Spiel, den DataLoader und den erreichten Score (z.B. XP) entgegen
    public DeathScreen(Main game, DataLoader dataLoader, int score) {
        this.game = game;
        this.dataLoader = dataLoader;
        this.score = score;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage); // Maus für diesen Screen aktivieren
        skin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));

        // Ein Table ist perfekt, um Dinge automatisch mittig anzuordnen
        Table table = new Table();
        table.setFillParent(true); // Tabelle füllt den ganzen Bildschirm
        stage.addActor(table);

        // Score Label und Button erstellen
        Label scoreLabel = new Label("DU BIST GESTORBEN!\nDein Score (XP): " + score, skin);
        scoreLabel.setAlignment(Align.center);

        TextButton respawnButton = new TextButton("Respawn", skin);

        // Was passiert, wenn man auf Respawn klickt?
        respawnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Wir werfen den Spieler einfach in einen komplett neuen GamePlayScreen!
                game.setScreen(new GamePlayScreen(game, dataLoader));

            }
        });

        // Elemente zur Tabelle hinzufügen
        table.add(scoreLabel).padBottom(30).row();
        table.add(respawnButton).width(200).height(60);
    }

    @Override
    public void render(float delta) {
        // Ein dunkler, rötlicher Hintergrund für den Death Screen
        Gdx.gl.glClearColor(0.3f, 0f, 0f, 1f);
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
