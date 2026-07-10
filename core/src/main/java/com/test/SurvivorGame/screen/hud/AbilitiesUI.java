package com.test.SurvivorGame.screen.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.test.SurvivorGame.ability.AbilityService;
import com.test.SurvivorGame.ability.BaseAbility;
import com.test.SurvivorGame.core.PlayerState;

import java.util.List;

/**
 * UI mit der man während des Spiels frei zwischen den 4 aktiven Ability-Slots wählen kann:
 * Zuerst einen der 4 Slots anklicken (wird markiert), danach eine bereits freigeschaltete
 * Ability aus der Liste anklicken, um sie in diesen Slot zu packen. Steckt die gewählte
 * Ability schon in einem anderen Slot, werden die beiden Slots einfach vertauscht.
 * Baut auf dem gleichen Scene2D-Stage/Skin-Muster wie InventoryUI/ChestUI auf.
 */
public class AbilitiesUI {

    private static final int SLOT_COUNT = 4;

    private final Stage stage;
    private final Skin skin;
    private final ShapeRenderer shapeRenderer;
    private final OrthographicCamera camera = new OrthographicCamera();

    private final Table slotTable;
    private final Table abilityListTable;
    private final Label hintLabel;

    private PlayerState playerState;
    private AbilityService abilityService;

    private int selectedSlot = -1;

    private Runnable backListener;

    public AbilitiesUI(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;

        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Table root = new Table();
        root.setFillParent(true);

        Label title = new Label("Abilities", skin);
        title.setFontScale(2f);
        root.add(title).padBottom(20);
        root.row();

        hintLabel = new Label("choose slot, then click on ability you want to swap.", skin);
        root.add(hintLabel).padBottom(20);
        root.row();

        slotTable = new Table();
        root.add(slotTable).padBottom(30);
        root.row();

        abilityListTable = new Table();
        abilityListTable.top();

        ScrollPane scrollPane = new ScrollPane(abilityListTable, skin);
        scrollPane.setFadeScrollBars(false);

        root.add(scrollPane).expand().fill();
        root.row();

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (backListener != null) {
                    backListener.run();
                }
            }
        });

        root.add(backButton).width(220).height(45).padTop(20);

        stage.addActor(root);
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public void setAbilityService(AbilityService abilityService) {
        this.abilityService = abilityService;
    }

    public void setBackListener(Runnable listener) {
        this.backListener = listener;
    }

    // Baut Slot-Leiste und Ability-Liste komplett neu auf. Sollte immer aufgerufen werden,
    // bevor die UI angezeigt wird (z.B. beim Öffnen aus dem Pausemenü).
    public void refresh() {
        if (playerState == null || abilityService == null) {
            return;
        }

        if (selectedSlot >= SLOT_COUNT) {
            selectedSlot = -1;
        }

        refreshSlots();
        refreshAbilityList();
    }

    private void refreshSlots() {
        slotTable.clear();

        String[] abilitySlots = playerState.getPlayerData().abilitySlots;

        for (int i = 0; i < SLOT_COUNT; i++) {
            final int slotIndex = i;

            String abilityId = i < abilitySlots.length ? abilitySlots[i] : null;
            String labelText = abilityToLabel(abilityId, "Empty");

            TextButton slotButton = new TextButton(labelText, skin);
            slotButton.getLabel().setWrap(true);
            slotButton.getLabel().setAlignment(Align.center);

            if (slotIndex == selectedSlot) {
                slotButton.setColor(Color.YELLOW);
            }

            slotButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    onSlotClicked(slotIndex);
                }
            });

            slotTable.add(slotButton).width(160).height(110).pad(8);
        }
    }

    private void refreshAbilityList() {
        abilityListTable.clear();

        List<String> unlockedIds = abilityService.getUnlockedActiveAbilityIds();

        if (unlockedIds.isEmpty()) {
            Label empty = new Label("no aktiv ability unlocked", skin);
            abilityListTable.add(empty).pad(20);
            return;
        }

        for (String abilityId : unlockedIds) {
            BaseAbility ability = abilityService.getAbilityRegistry().getAbility(abilityId);

            if (ability == null) {
                continue;
            }

            int level = playerState.getPlayerData().abilities.getOrDefault(abilityId, 0);
            int equippedSlot = abilityService.getSlotIndexOfAbility(abilityId);

            String labelText = ability.getName() + "\nLvl " + level;
            if (equippedSlot != -1) {
                labelText += "\n(Slot " + (equippedSlot + 1) + ")";
            }

            TextButton card = new TextButton(labelText, skin);
            card.getLabel().setWrap(true);
            card.getLabel().setAlignment(Align.center);

            if (equippedSlot != -1) {
                card.setColor(Color.LIGHT_GRAY);
            }

            card.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    onAbilityClicked(abilityId);
                }
            });

            abilityListTable.add(card).width(220).height(120).pad(10);
        }
    }

    private void onSlotClicked(int slotIndex) {
        selectedSlot = (selectedSlot == slotIndex) ? -1 : slotIndex;
        refreshSlots();
    }

    private void onAbilityClicked(String abilityId) {
        int targetSlot = selectedSlot;

        if (targetSlot == -1) {
            // Kein Slot ausgewählt: nimm automatisch den ersten Slot als Ziel.
            targetSlot = 0;
        }

        abilityService.equipAbilityToSlot(targetSlot, abilityId);

        selectedSlot = -1;
        refresh();
    }

    private String abilityToLabel(String abilityId, String emptyText) {
        if (abilityId == null || abilityId.isBlank()) {
            return emptyText;
        }

        BaseAbility ability = abilityService.getAbilityRegistry().getAbility(abilityId);
        return ability != null ? ability.getName() : abilityId;
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

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }

    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        stage.getViewport().update(width, height, true);
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
