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
import com.test.SurvivorGame.item.BaseItem;

import java.util.function.IntConsumer;

/**
 * Chest UI: zeigt beim Öffnen einer Truhe die Item-Optionen als Karten zur Auswahl an.
 * Baut auf dem gleichen Scene2D-Stage/Skin-Muster wie PauseMenuRenderer/LevelUpUI auf.
 */
public class ChestUI {

    private final ShapeRenderer shapeRenderer;
    private final OrthographicCamera camera = new OrthographicCamera();

    private final Stage stage;
    private final Skin skin;
    private final Table cardTable;

    private IntConsumer optionChosenListener;

    public ChestUI(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;

        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Label title = new Label("Chest found!", skin);

        cardTable = new Table();

        Table root = new Table();
        root.setFillParent(true);

        root.add(title).padBottom(30);
        root.row();
        root.add(cardTable);

        stage.addActor(root);
    }

    // Wird aufgerufen, sobald der Spieler eine Karte anklickt. Übergibt den Index (0,1,2,...)
    public void setOptionChosenListener(IntConsumer listener) {
        this.optionChosenListener = listener;
    }

    // Baut die Karten für die aktuellen Item-Optionen neu auf
    public void showOptions(BaseItem[] itemChoices) {
        cardTable.clear();

        for (int i = 0; i < itemChoices.length; i++) {
            BaseItem item = itemChoices[i];
            final int optionIndex = i;

            String buttonText = item.getName() + "\n[" + item.getRarity() + "]\n\n" + item.getDescription();

            TextButton card = new TextButton(buttonText, skin);
            card.getLabel().setWrap(true);

            card.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (optionChosenListener != null) {
                        optionChosenListener.accept(optionIndex);
                    }
                }
            });

            cardTable.add(card).width(220).height(260).pad(10);
        }
    }

    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        stage.getViewport().update(width, height, true);
    }

    public void render() {
        // Hintergrund abdunkeln, genau wie beim PauseMenuRenderer/LevelUpUI
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

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
