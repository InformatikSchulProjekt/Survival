package com.test.SurvivorGame.item;

import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;

public final class Iron_Chestplate extends BaseItem {
    public static final String ID = "iron_chestplate";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getName() {
        return "Iron Chestplate";
    }

    @Override
    public void onApply(PlayerState playerState) {
        onRemove(playerState);

        playerState.getPlayerStats().addModifier(
            new StatModifier(
                StatScope.ALL,
                StatType.RESISTANCE,
                0.20f,
                ModifierType.PERCENT,
                "item:" + ID
            )
        );
    }
}
