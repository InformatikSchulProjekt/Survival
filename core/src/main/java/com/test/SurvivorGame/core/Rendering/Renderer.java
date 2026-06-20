package com.test.SurvivorGame.core.Rendering;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.entity.Player;

public class Renderer {
    private final Batch batch;
    private final Viewport viewport;

    public Renderer(Batch batch, float screenWidth, float screenHeight) {
        this.batch = batch;
        this.viewport = new FitViewport(screenWidth, screenHeight);
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void render(Player player) {
        ScreenUtils.clear(Color.BLUE);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        renderPlayer(player);

        batch.end();
    }
    private void renderPlayer(Player player) {
        player.draw(batch);
    }
    public Viewport getViewport() {
        return viewport;
    }

}
