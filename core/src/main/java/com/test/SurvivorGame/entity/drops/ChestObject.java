package com.test.SurvivorGame.entity.drops;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.test.SurvivorGame.entity.Player;

public class ChestObject extends DroppedObject {
    private ChestType chestType;
    private Texture texture = new Texture(Gdx.files.internal("Placeholder/PlayerPH.png"));

    public ChestObject(float x, float y, Player player, ChestType chestType ) {
        super(x, y, 3f, 4f, new Texture(Gdx.files.internal("Placeholder/PlayerPH.png")), player);
        this.chestType = chestType;
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
