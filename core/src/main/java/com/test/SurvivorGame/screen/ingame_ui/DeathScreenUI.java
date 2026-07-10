package com.test.SurvivorGame.screen.ingame_ui;

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

/**
 * Overlay, das angezeigt wird, sobald der Spieler stirbt. Zeigt die Überlebenszeit
 * des Runs und bietet die Wahl zwischen Neustart und Rückkehr zum Titelbildschirm.
 * Baut auf dem gleichen Scene2D-Stage/Skin-Muster wie MapFinishedUI/PauseMenuUI auf.
 */
public class DeathScreenUI {

    private final ShapeRenderer shapeRenderer;
    private final OrthographicCamera camera = new OrthographicCamera();

    private final Stage stage;
    private final Skin skin;

    private final Label timeLabel;

    private final TextButton restartButton;
    private final TextButton backToTitleButton;

    private Runnable restartListener;
    private Runnable backToTitleListener;

    public DeathScreenUI(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;

        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Label title = new Label("You Died", skin);
        title.setFontScale(2f);

        timeLabel = new Label("Survived: 00:00", skin);

        restartButton = new TextButton("Restart", skin);
        backToTitleButton = new TextButton("Back to Title", skin);

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (restartListener != null) {
                    restartListener.run();
                }
            }
        });

        backToTitleButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (backToTitleListener != null) {
                    backToTitleListener.run();
                }
            }
        });

        Table table = new Table();
        table.setFillParent(true);

        table.add(title).padBottom(20);
        table.row();

        table.add(timeLabel).padBottom(40);
        table.row();

        table.add(restartButton).width(300).height(50).pad(5);
        table.row();

        table.add(backToTitleButton).width(300).height(50).pad(5);

        stage.addActor(table);
    }

    // Baut den Screen mit der übergebenen Überlebenszeit (in Sekunden) neu auf.
    public void show(float survivalTimeSeconds) {
        int totalSeconds = Math.max(0, (int) survivalTimeSeconds);
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        timeLabel.setText(String.format("Survived: %02d:%02d", minutes, seconds));
    }

    public void setRestartListener(Runnable listener) {
        this.restartListener = listener;
    }

    public void setBackToTitleListener(Runnable listener) {
        this.backToTitleListener = listener;
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
        shapeRenderer.setColor(0, 0, 0, 0.85f);
        shapeRenderer.rect(0, 0, camera.viewportWidth, camera.viewportHeight);
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        stage.act();
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
