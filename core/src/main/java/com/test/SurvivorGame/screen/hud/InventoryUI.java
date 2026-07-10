package com.test.SurvivorGame.screen.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.item.BaseItem;
import com.test.SurvivorGame.item.ItemRarity;

public class InventoryUI {


    private final Stage stage;
    private final Skin skin;

    private final ShapeRenderer shapeRenderer;

    private final OrthographicCamera camera = new OrthographicCamera();

    private final Table itemTable;

    private PlayerState playerState;

    private Runnable backListener;

    public InventoryUI(ShapeRenderer shapeRenderer) {

        this.shapeRenderer = shapeRenderer;

        camera.setToOrtho(
            false,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight()
        );

        stage = new Stage(new ScreenViewport());

        skin = new Skin(
            Gdx.files.internal("uiskin.json")
        );

        Table root = new Table();

        root.setFillParent(true);

        Label title =
            new Label("Inventory", skin);

        title.setFontScale(2f);

        root.add(title)
            .padBottom(40);

        root.row();

        itemTable = new Table();

        itemTable.top();

        ScrollPane scrollPane =
            new ScrollPane(itemTable, skin);

        scrollPane.setFadeScrollBars(false);

        root.add(scrollPane)
            .expand()
            .fill();

        root.row();

        TextButton backButton =
            new TextButton(
                "Back",
                skin
            );

        backButton.addListener(
            new ClickListener() {

                @Override
                public void clicked(
                    InputEvent event,
                    float x,
                    float y
                ) {

                    if(backListener != null)
                        backListener.run();

                }
            }
        );

        root.add(backButton)
            .width(220)
            .height(45)
            .padTop(20);

        stage.addActor(root);

    }

    public void setPlayerState(PlayerState playerState){

        this.playerState = playerState;

    }

    public void refresh(){

        itemTable.clear();

        if(playerState == null)
            return;

        for(String itemID :
            playerState.getPlayerData().items){


            BaseItem item =
                playerState
                    .getItemRegistry()
                    .getItem(itemID);

            if(item == null)
                continue;

            Table card =
                new Table(skin);

            card.defaults()
                .pad(5);


            Label name =
                new Label(
                    item.getName(),
                    skin
                );

            name.setFontScale(1.3f);


            Label rarity =
                new Label(
                    item.getRarity().toString(),
                    skin
                );

            rarity.setColor(
                getRarityColor(
                    item.getRarity()
                )
            );


            Label description =
                new Label(
                    item.getDescription(),
                    skin
                );


            description.setWrap(true);
            description.setAlignment(
                Align.center
            );



            card.add(name);
            card.row();


            card.add(rarity);
            card.row();


            card.add(description)
                .width(350)
                .height(60);



            itemTable.add(card)
                .width(450)
                .padBottom(25);


            itemTable.row();

        }
    }

    private Color getRarityColor(ItemRarity rarity){

        switch(rarity){

            case COMMON:
                return Color.WHITE;

            case RARE:
                return Color.BLUE;

            case EPIC:
                return Color.PURPLE;

            case LEGENDARY:
                return Color.YELLOW;

        }

        return Color.WHITE;
    }

    public void render(){


        shapeRenderer.setProjectionMatrix(
            camera.combined
        );

        Gdx.gl.glEnable(
            GL20.GL_BLEND
        );

        Gdx.gl.glBlendFunc(
            GL20.GL_SRC_ALPHA,
            GL20.GL_ONE_MINUS_SRC_ALPHA
        );


        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );

        shapeRenderer.setColor(
            0,
            0,
            0,
            0.85f
        );

        shapeRenderer.rect(
            0,
            0,
            camera.viewportWidth,
            camera.viewportHeight
        );

        shapeRenderer.end();

        Gdx.gl.glDisable(
            GL20.GL_BLEND
        );

        stage.act(
            Gdx.graphics.getDeltaTime()
        );

        stage.draw();

    }

    public Stage getStage(){
        return stage;
    }

    public void setBackListener(
        Runnable listener
    ){

        this.backListener = listener;

    }

    public void resize(
        int width,
        int height
    ){

        camera.setToOrtho(
            false,
            width,
            height
        );

        stage.getViewport()
            .update(
                width,
                height,
                true
            );
    }

    public void dispose(){

        stage.dispose();
        skin.dispose();

    }
}
