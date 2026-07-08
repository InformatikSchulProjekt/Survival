package com.test.SurvivorGame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.test.SurvivorGame.Main;
import com.test.SurvivorGame.core.data.DataLoader;
import com.test.SurvivorGame.core.data.MapSaveData;
import com.test.SurvivorGame.world.maps.GameMap;
import com.test.SurvivorGame.world.maps.MapRegistry;

public class MapSelection extends ScreenAdapter {

    private final Main main;
    private final DataLoader dataLoader;

    private final Stage stage;
    private final Skin skin;

    public MapSelection(Main main, DataLoader dataLoader) {

        this.main = main;
        this.dataLoader = dataLoader;

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        root.setFillParent(true);

        Label title = new Label("Select Map", skin);
        title.setFontScale(2f);

        root.add(title).padBottom(40);
        root.row();

        Table mapTable = new Table();

        for (GameMap map : MapRegistry.getMaps()) {

            MapSaveData save = dataLoader.getMapSaveData(map.getId());

            Table card = new Table(skin);
            card.defaults().pad(5);

            Label mapName = new Label(map.getDisplayName(), skin);
            mapName.setFontScale(1.4f);

            Label description = new Label(map.getDescription(), skin);
            description.setWrap(true);
            description.setAlignment(Align.center);
            Label bestTime = new Label(
                "Best Survival Time: " + formatTime(save.bestSurvivalTime),
                skin
            );

            TextButton playButton = new TextButton("Play", skin);

            playButton.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {

                    main.setScreen(
                        new GamePlayScreen(
                            main,
                            dataLoader,
                            map.getId()
                        )
                    );

                    dispose();
                }
            });

            card.add(mapName).padTop(10);
            card.row();

            card.add(description).width(350);
            card.row();

            card.add(bestTime);
            card.row();

            card.add(playButton)
                .width(200)
                .height(45)
                .padTop(15);

            mapTable.add(card)
                .width(450)
                .padBottom(30);

            mapTable.row();
        }

        ScrollPane scrollPane = new ScrollPane(mapTable, skin);
        scrollPane.setFadeScrollBars(false);

        root.add(scrollPane).expand().fill();

        stage.addActor(root);
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
