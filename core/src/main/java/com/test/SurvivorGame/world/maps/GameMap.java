package com.test.SurvivorGame.world.maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.test.SurvivorGame.world.WaveControl.EnemyType;
import com.test.SurvivorGame.world.WaveControl.SpawnProfile;

import java.util.ArrayList;

public abstract class GameMap {

    private final String id;
    private final String displayName;
    private final String description;

    protected Texture texture;
    protected float worldWidth;
    protected float worldHeight;

    protected final SpawnProfile spawnProfile = new SpawnProfile();

    protected GameMap(
        String id,
        String displayName,
        String description,
        String texturePath,
        float worldWidth,
        float worldHeight) {

        this.id = id;
        this.displayName = displayName;
        this.description = description;

        texture = new Texture(Gdx.files.internal(texturePath));

        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
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

    public abstract ArrayList<EnemyType> getEnemyTypes();

    // returned die Anzahl an Waves die man besiegen muss, um die Map zu completen
    public abstract int getMaxWaves();

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

    // ---------- Gameplay ----------

    public SpawnProfile getSpawnProfile() {
        return spawnProfile;
    }

    // ---------- Cleanup ----------

    public void dispose() {
        texture.dispose();
    }
}
