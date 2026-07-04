package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.*;
import com.test.SurvivorGame.item.*;

import java.util.List;

public final class PhilosophersStone extends BaseItem {
    public static final String ID = "philosophers_stone";

    @Override public String getID() { return ID; }
    @Override public String getName() { return "Philosopher's Stone"; }
    @Override public String getDescription() { return "A legendary stone that transforms battle experience into impossible growth."; }
    @Override public ItemRarity getRarity() { return ItemRarity.LEGENDARY; }

    @Override
    public List<StatModifier> getModifiers() {
        return List.of(
            new StatModifier(StatScope.ALL, StatType.XP_GAIN, 0.50f, ModifierType.PERCENT, "item:" + ID),
            new StatModifier(StatScope.ALL, StatType.CHEST_CHANCE, 0.20f, ModifierType.PERCENT, "item:" + ID)
        );
    }
}
