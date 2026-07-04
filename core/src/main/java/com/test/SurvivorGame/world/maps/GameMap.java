package com.test.SurvivorGame.world.maps;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GameMap {
    private final Texture texture;
    private final float worldWidth;
    private final float worldHeight;
    private final boolean infinite;

    public GameMap() {
        this.texture = new Texture(Gdx.files.internal("Maps/Map(clear1).png"));
        this.worldWidth = 30f;
        this.worldHeight = 30f;
        this.infinite = true;
    }

    public GameMap(String texturePath, float worldWidth, float worldHeight) {
        this.texture = new Texture(Gdx.files.internal(texturePath));
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.infinite = false;
    }

    public GameMap(String texturePath, float worldWidth, float worldHeight, boolean infinite) {
        this.texture = new Texture(Gdx.files.internal(texturePath));
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.infinite = infinite;
    }

    // getters for Renderer
    public Texture getTexture() {
        return texture;
    }

    public float getWorldWidth() {
        return worldWidth;
    }

    public float getWorldHeight() {
        return worldHeight;
    }

    public boolean isInfinite() {
        return infinite;
    }

    public void dispose() {
        texture.dispose();
    }
}
