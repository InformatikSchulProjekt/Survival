package com.test.SurvivorGame.world.maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.test.SurvivorGame.world.maps.WaveControl.SpawnProfile;

public abstract class GameMap {

    private final String id;
    private final String displayName;
    private final String description;

    protected Texture texture;
    protected float worldWidth;
    protected float worldHeight;
    protected boolean infinite;

    protected final SpawnProfile spawnProfile = new SpawnProfile();

    protected GameMap(
        String id,
        String displayName,
        String description,
        String texturePath,
        float worldWidth,
        float worldHeight,
        boolean infinite) {

        this.id = id;
        this.displayName = displayName;
        this.description = description;

        texture = new Texture(Gdx.files.internal(texturePath));

        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.infinite = infinite;
    }

    // ---------- Map Informationen ----------

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    // ---------- Renderer ----------

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

    // ---------- Gameplay ----------

    public SpawnProfile getSpawnProfile() {
        return spawnProfile;
    }

    // ---------- Cleanup ----------

    public void dispose() {
        texture.dispose();
    }
}
