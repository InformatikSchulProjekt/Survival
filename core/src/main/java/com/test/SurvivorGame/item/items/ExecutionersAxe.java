package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.*;
import com.test.SurvivorGame.item.*;

import java.util.List;

public final class ExecutionersAxe extends BaseItem {
    public static final String ID = "executioners_axe";

    @Override public String getID() { return ID; }
    @Override public String getName() { return "Executioner's Axe"; }
    @Override public String getDescription() { return "Every swing seeks a killing blow."; }
    @Override public ItemRarity getRarity() { return ItemRarity.EPIC; }

    @Override
    public List<StatModifier> getModifiers() {
        return List.of(
            new StatModifier(StatScope.ALL, StatType.CRIT_MULTIPLIER, 0.5f, ModifierType.FLAT, "item:" + ID)
        );
    }
}
