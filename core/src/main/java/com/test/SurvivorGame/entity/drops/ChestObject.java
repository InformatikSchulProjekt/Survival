package com.test.SurvivorGame.entity.drops;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.world.maps.GameMap;

public class ChestObject extends DroppedObject {
    private ChestType chestType;

    private Texture texture = new Texture(Gdx.files.internal("Placeholder/PlayerPH.png"));
    private ChestState state = ChestState.CLOSED;
    private float animationTime = 0f;

    public ChestObject(float x, float y, Player player, ChestType chestType ) {
        super(x, y, 1.5f, 1f, new Texture(Gdx.files.internal("Placeholder/PlayerPH.png")), player);
        this.chestType = chestType;
    }
    public ChestState getState() {
        return state;
    }
    @Override
    public void update(float deltaTime, GameMap map) {

        animationTime += deltaTime;

        if (state == ChestState.CLOSED && overlaps(getPlayer())) {

            state = ChestState.OPENING;
            animationTime = 0f;

            getPlayer().getPlayerState().collectDrop(this);
        }

        if (state == ChestState.OPENING) {

            float OPENING_DURATION = 0.6f;
            // Dauer der Öffnungsanimation
            if (animationTime >= OPENING_DURATION) {
                state = ChestState.OPENED;
                animationTime = 0f;
            }
        }

        if (state == ChestState.OPENED) {

            if (animationTime >= 0.5f) {
                setDespawned(true);
            }
        }
    }
    public void open() {
        state = ChestState.OPENED;
    }

    public float getAnimationTime() {
        return animationTime;
    }
    @Override
    protected float getDespawnTime() {
        return 120f;
    }

    public ChestType getChestType() {
        return chestType;
    }

    public void dispose() {
        texture.dispose();
    }

}
