package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.*;
import com.test.SurvivorGame.item.*;

import java.util.List;

public final class SharpeningStone extends BaseItem {
    public static final String ID = "sharpening_stone";

    @Override public String getID() { return ID; }
    @Override public String getName() { return "Sharpening Stone"; }
    @Override public String getDescription() { return "A simple whetstone that leaves every strike a little deadlier."; }
    @Override public ItemRarity getRarity() { return ItemRarity.COMMON; }

    @Override
    public List<StatModifier> getModifiers() {
        return List.of(
            new StatModifier(StatScope.ALL, StatType.CRIT_CHANCE, 0.03f, ModifierType.FLAT, "item:" + ID)
        );
    }
}
