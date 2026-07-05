package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.*;
import com.test.SurvivorGame.item.*;

import java.util.List;

public final class DuelistsRapier extends BaseItem {
    public static final String ID = "duelists_rapier";

    @Override public String getID() { return ID; }
    @Override public String getName() { return "Duelist's Rapier"; }
    @Override public String getDescription() { return "Elegant, swift, and always aimed at the heart."; }
    @Override public ItemRarity getRarity() { return ItemRarity.EPIC; }

    @Override
    public List<StatModifier> getModifiers() {
        return List.of(
            new StatModifier(StatScope.ALL, StatType.CRIT_CHANCE, 0.08f, ModifierType.FLAT, "item:" + ID),
            new StatModifier(StatScope.ALL, StatType.SPEED, 0.10f, ModifierType.PERCENT, "item:" + ID)
        );
    }
}
