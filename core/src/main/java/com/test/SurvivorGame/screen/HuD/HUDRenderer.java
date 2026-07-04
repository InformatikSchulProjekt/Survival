package com.test.SurvivorGame.screen.HuD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.test.SurvivorGame.core.PlayerState;

public class HUDRenderer {

    private final Batch batch;
    private final ShapeRenderer shapeRenderer;
    private final BitmapFont font;

    private final OrthographicCamera hudCamera = new OrthographicCamera();

    private int lastWidth = -1;
    private int lastHeight = -1;

    private static final float PADDING = 20f;
    private static final float HP_BAR_WIDTH = 220f;
    private static final float HP_BAR_HEIGHT = 22f;
    private static final float XP_BAR_WIDTH = 160f;
    private static final float XP_BAR_HEIGHT = 10f;
    private static final float XP_BAR_GAP = 6f;
    private static final float SLOT_SIZE = 56f;
    private static final float SLOT_GAP = 12f;

    private static final Color HP_COLOR = new Color(0.85f, 0.15f, 0.15f, 1f);
    private static final Color HP_BG_COLOR = new Color(0.25f, 0.05f, 0.05f, 1f);
    private static final Color XP_COLOR = new Color(0.25f, 0.55f, 0.95f, 1f);
    private static final Color XP_BG_COLOR = new Color(0.08f, 0.12f, 0.2f, 1f);
    private static final Color SLOT_FILLED_COLOR = new Color(0.18f, 0.18f, 0.28f, 0.9f);
    private static final Color SLOT_EMPTY_COLOR = new Color(0.1f, 0.1f, 0.1f, 0.6f);

    public HUDRenderer(Batch batch, ShapeRenderer shapeRenderer) {
        this.batch = batch;
        this.shapeRenderer = shapeRenderer;

        this.font = new BitmapFont();
        font.getData().setScale(1.2f);

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void resize(int width, int height) {
        hudCamera.setToOrtho(false, width, height);
        hudCamera.update();

        lastWidth = width;
        lastHeight = height;
    }

    public void render(PlayerState playerState, float survivalTimeSeconds) {
        int currentWidth = Gdx.graphics.getWidth();
        int currentHeight = Gdx.graphics.getHeight();

        if (currentWidth != lastWidth || currentHeight != lastHeight) {
            resize(currentWidth, currentHeight);
        }

        // wichtig: HUD soll auf dem ganzen Fenster rendern, nicht nur im World-Viewport
        Gdx.gl.glViewport(0, 0, currentWidth, currentHeight);

        hudCamera.update();
        shapeRenderer.setProjectionMatrix(hudCamera.combined);
        batch.setProjectionMatrix(hudCamera.combined);

        float screenHeight = hudCamera.viewportHeight;
        float screenWidth = hudCamera.viewportWidth;

        drawHpBar(playerState, screenHeight);
        drawXpBar(playerState, screenHeight);
        drawSurvivalTimer(survivalTimeSeconds, screenWidth, screenHeight);
        drawAbilityBar(playerState.getPlayerData().abilitySlots, screenWidth);
    }

    private void drawHpBar(PlayerState playerState, float screenHeight) {
        float hp = Math.max(0f, playerState.getHP());
        float maxHp = playerState.getMaxHealth();
        float ratio = maxHp > 0f ? hp / maxHp : 0f;

        float x = PADDING;
        float y = screenHeight - PADDING - HP_BAR_HEIGHT;

        drawBar(x, y, HP_BAR_WIDTH, HP_BAR_HEIGHT, ratio, HP_BG_COLOR, HP_COLOR);

        batch.begin();
        font.setColor(Color.WHITE);
        String hpText = String.format("HP: %.0f / %.0f", hp, maxHp);
        font.draw(batch, hpText, x, y + HP_BAR_HEIGHT - 5, HP_BAR_WIDTH, Align.center, false);
        batch.end();
    }

    private void drawXpBar(PlayerState playerState, float screenHeight) {
        float x = PADDING;
        float y = screenHeight - PADDING - HP_BAR_HEIGHT - XP_BAR_GAP - XP_BAR_HEIGHT;

        drawBar(x, y, XP_BAR_WIDTH, XP_BAR_HEIGHT, playerState.getXpProgress(), XP_BG_COLOR, XP_COLOR);

        batch.begin();
        font.setColor(Color.WHITE);
        font.draw(batch, "Lvl " + playerState.getLevel(), x + XP_BAR_WIDTH + 8, y + XP_BAR_HEIGHT + 4);
        batch.end();
    }

    private void drawBar(float x, float y, float width, float height, float ratio, Color backgroundColor, Color fillColor) {
        float clampedRatio = MathUtils.clamp(ratio, 0f, 1f);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(backgroundColor);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.setColor(fillColor);
        shapeRenderer.rect(x, y, width * clampedRatio, height);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
    }

    private void drawSurvivalTimer(float survivalTimeSeconds, float screenWidth, float screenHeight) {
        int totalSeconds = Math.max(0, (int) survivalTimeSeconds);
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        String text = String.format("%02d:%02d", minutes, seconds);

        batch.begin();
        font.setColor(Color.WHITE);
        font.draw(batch, text, 0f, screenHeight - PADDING, screenWidth, Align.center, false);
        batch.end();
    }

    private void drawAbilityBar(String[] abilitySlots, float screenWidth) {
        if (abilitySlots == null || abilitySlots.length == 0) {
            return;
        }

        int slotCount = abilitySlots.length;
        float totalWidth = slotCount * SLOT_SIZE + (slotCount - 1) * SLOT_GAP;
        float startX = (screenWidth - totalWidth) / 2f;
        float y = PADDING;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < slotCount; i++) {
            float x = startX + i * (SLOT_SIZE + SLOT_GAP);
            shapeRenderer.setColor(isSlotFilled(abilitySlots[i]) ? SLOT_FILLED_COLOR : SLOT_EMPTY_COLOR);
            shapeRenderer.rect(x, y, SLOT_SIZE, SLOT_SIZE);
        }
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        for (int i = 0; i < slotCount; i++) {
            float x = startX + i * (SLOT_SIZE + SLOT_GAP);
            shapeRenderer.rect(x, y, SLOT_SIZE, SLOT_SIZE);
        }
        shapeRenderer.end();

        batch.begin();
        for (int i = 0; i < slotCount; i++) {
            float x = startX + i * (SLOT_SIZE + SLOT_GAP);

            font.setColor(Color.LIGHT_GRAY);
            font.draw(batch, String.valueOf(i + 1), x + 6, y + SLOT_SIZE - 6);

            if (isSlotFilled(abilitySlots[i])) {
                font.setColor(Color.WHITE);
                font.draw(batch, abilitySlots[i], x, y + SLOT_SIZE / 2f + 6, SLOT_SIZE, Align.center, true);
            }
        }
        batch.end();
    }

    private boolean isSlotFilled(String abilityId) {
        return abilityId != null && !abilityId.isBlank();
    }

    public void dispose() {
        font.dispose();
    }
}
