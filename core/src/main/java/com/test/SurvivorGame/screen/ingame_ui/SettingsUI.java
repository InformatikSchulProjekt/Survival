package com.test.SurvivorGame.screen.ingame_ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.test.SurvivorGame.core.input.Action;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.test.SurvivorGame.core.data.DataLoader;

import java.util.HashMap;
import java.util.Map;

public class SettingsUI {

    private final Stage stage;
    private final Skin skin;
    private final ShapeRenderer shapeRenderer;
    private final DataLoader dataLoader;
    private final OrthographicCamera camera = new OrthographicCamera();

    private final Map<Action, TextButton> keyButtons = new HashMap<>();

    private Action waitingForKey;

    private Runnable backListener;
    private Runnable resetListener;

    public SettingsUI(DataLoader dataLoader, ShapeRenderer shapeRenderer) {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        this.dataLoader = dataLoader;

        this.shapeRenderer = shapeRenderer;
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Table table = new Table();
        table.setFillParent(true);

        stage.addActor(table);

        addKeyButton(table, "Move Up", Action.MOVE_UP);
        addKeyButton(table, "Move Down", Action.MOVE_DOWN);
        addKeyButton(table, "Move Left", Action.MOVE_LEFT);
        addKeyButton(table, "Move Right", Action.MOVE_RIGHT);

        addKeyButton(table, "Ability 1", Action.ABILITY_1);
        addKeyButton(table, "Ability 2", Action.ABILITY_2);
        addKeyButton(table, "Ability 3", Action.ABILITY_3);
        addKeyButton(table, "Ability 4", Action.ABILITY_4);

        addKeyButton(table, "Pause", Action.PAUSE);

        TextButton backButton = new TextButton("Back", skin);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (backListener != null) {
                    backListener.run();
                }
            }
        });

        TextButton resetButton = new TextButton("Reset Keys", skin);

        resetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (resetListener != null) {
                    resetListener.run();
                }
            }
        });

        table.row().padTop(20);

        table.add(resetButton).width(220).padRight(10);
        table.add(backButton).width(220).padLeft(10);
    }

    private void addKeyButton(Table table, String text, Action action) {

        Label label = new Label(text, skin);

        TextButton button = new TextButton(
            Input.Keys.toString(dataLoader.getKeybind(action)),
            skin
        );

        keyButtons.put(action, button);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                waitingForKey = action;
                button.setText("Press key...");
            }
        });

        table.row().pad(10);

        table.add(label).left().width(200);
        table.add(button).width(200);
    }

    public void render() {
        if (waitingForKey != null) {
            for (int key = 0; key < Input.Keys.MAX_KEYCODE; key++) {
                if (Gdx.input.isKeyJustPressed(key)) {
                    dataLoader.setKeybind(waitingForKey, key);

                    keyButtons.get(waitingForKey)
                        .setText(Input.Keys.toString(key));

                    waitingForKey = null;
                    break;
                }
            }
        }

        shapeRenderer.setProjectionMatrix(camera.combined);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.85f);
        shapeRenderer.rect(0, 0, camera.viewportWidth, camera.viewportHeight);
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }

    public void setBackListener(Runnable backListener) {
        this.backListener = backListener;
    }

    public void setResetListener(Runnable resetListener) {
        this.resetListener = resetListener;
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        stage.getViewport().update(width, height, true);
    }

    public void refreshButtons() {
        for (Action action : Action.values()) {
            keyButtons.get(action)
                .setText(Input.Keys.toString(dataLoader.getKeybind(action)));
        }
    }


}
