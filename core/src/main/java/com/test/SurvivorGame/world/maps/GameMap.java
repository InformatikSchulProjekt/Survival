package com.test.SurvivorGame.world.maps;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GameMap {
    private final Texture texture;
    private final float worldWidth;
    private final float worldHeight;

    public GameMap() {
        this.texture = new Texture(Gdx.files.internal("Maps/Map(clear1).png"));
        this.worldWidth = 50f;
        this.worldHeight = 50f;
    }

    public GameMap(String texturePath, float worldWidth, float worldHeight) {
        this.texture = new Texture(Gdx.files.internal(texturePath));
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
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

    public void dispose() {
        texture.dispose();
    }
}
