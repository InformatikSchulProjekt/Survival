package com.test.SurvivorGame.entity.drops;

import com.test.SurvivorGame.entity.Player;

public class ChestObject extends DroppedObject {
    private ChestType chestType;

    public ChestObject(float x, float y, Player player, ChestType chestType ) {
        super(x, y, 3f, 4f, player);
    }

    @Override
    protected float getDespawnTime() {
        return 120f;
    }

    public ChestType getChestType() {
        return chestType;
    }



}
