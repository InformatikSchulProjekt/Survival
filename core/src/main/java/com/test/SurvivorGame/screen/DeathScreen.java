package com.test.SurvivorGame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;

/**
 * Overlay, das angezeigt wird wenn der Spieler stirbt (ähnlich dem Minecraft-Death-Screen).
 * Rendert über dem eingefrorenen Spiel und bietet einen Neustart-Button an.
 * Läuft komplett eigenständig auf ShapeRenderer/Batch, wie der HUDRenderer.
 */
public class DeathScreen {

    private final Batch batch;
    private final ShapeRenderer shapeRenderer;
    private final BitmapFont titleFont;
    private final BitmapFont font;

    private final OrthographicCamera camera = new OrthographicCamera();

    private int lastWidth = -1;
    private int lastHeight = -1;

    private static final Color OVERLAY_COLOR = new Color(0f, 0f, 0f, 0.7f);
    private static final Color TITLE_COLOR = new Color(0.8f, 0.1f, 0.1f, 1f);
    private static final Color BUTTON_COLOR = new Color(0.55f, 0.12f, 0.12f, 1f);
    private static final Color BUTTON_HOVER_COLOR = new Color(0.75f, 0.2f, 0.2f, 1f);

    private static final float BUTTON_WIDTH = 260f;
    private static final float BUTTON_HEIGHT = 60f;

    private final Rectangle restartButtonBounds = new Rectangle();

    public DeathScreen(Batch batch, ShapeRenderer shapeRenderer) {
        this.batch = batch;
        this.shapeRenderer = shapeRenderer;

        this.titleFont = new BitmapFont();
        titleFont.getData().setScale(3f);

        this.font = new BitmapFont();
        font.getData().setScale(1.4f);

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.update();

        lastWidth = width;
        lastHeight = height;

        restartButtonBounds.set(
            width / 2f - BUTTON_WIDTH / 2f,
            height / 2f - 120f,
            BUTTON_WIDTH,
            BUTTON_HEIGHT
        );
    }

    public void render(float survivalTimeSeconds) {
        int currentWidth = Gdx.graphics.getWidth();
        int currentHeight = Gdx.graphics.getHeight();

        if (currentWidth != lastWidth || currentHeight != lastHeight) {
            resize(currentWidth, currentHeight);
        }

        // Overlay soll über das ganze Fenster gehen, genau wie das HUD
        Gdx.gl.glViewport(0, 0, currentWidth, currentHeight);

        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);

        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;

        boolean hovering = isMouseOverButton();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(OVERLAY_COLOR);
        shapeRenderer.rect(0, 0, screenWidth, screenHeight);

        shapeRenderer.setColor(hovering ? BUTTON_HOVER_COLOR : BUTTON_COLOR);
        shapeRenderer.rect(restartButtonBounds.x, restartButtonBounds.y, restartButtonBounds.width, restartButtonBounds.height);
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(restartButtonBounds.x, restartButtonBounds.y, restartButtonBounds.width, restartButtonBounds.height);
        shapeRenderer.end();

        int totalSeconds = Math.max(0, (int) survivalTimeSeconds);
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        String timeText = String.format("Überlebt: %02d:%02d", minutes, seconds);

        batch.begin();

        titleFont.setColor(TITLE_COLOR);
        titleFont.draw(batch, "DU BIST GESTORBEN", 0, screenHeight / 2f + 160f, screenWidth, Align.center, false);

        font.setColor(Color.WHITE);
        font.draw(batch, timeText, 0, screenHeight / 2f + 80f, screenWidth, Align.center, false);

        font.draw(batch, "Neustart", restartButtonBounds.x, restartButtonBounds.y + restartButtonBounds.height / 2f + 8f,
            restartButtonBounds.width, Align.center, false);

        font.draw(batch, "[ENTER] oder Klick zum Neustarten", 0, restartButtonBounds.y - 20f, screenWidth, Align.center, false);

        batch.end();
    }

    private boolean isMouseOverButton() {
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Gdx-Input ist Y-down, unser Camera-Space Y-up

        return restartButtonBounds.contains(mouseX, mouseY);
    }

    public boolean isRestartClicked() {
        return Gdx.input.justTouched() && isMouseOverButton();
    }

    public void dispose() {
        titleFont.dispose();
        font.dispose();
    }
}
