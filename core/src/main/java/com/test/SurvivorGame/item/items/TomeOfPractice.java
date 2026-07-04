package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.*;
import com.test.SurvivorGame.item.*;

import java.util.List;

public final class TomeOfPractice extends BaseItem {
    public static final String ID = "tome_of_practice";

    @Override public String getID() { return ID; }
    @Override public String getName() { return "Tome of Practice"; }
    @Override public String getDescription() { return "An old training tome filled with combat notes and spell markings."; }
    @Override public ItemRarity getRarity() { return ItemRarity.RARE; }

    @Override
    public List<StatModifier> getModifiers() {
        return List.of(
            new StatModifier(StatScope.ALL, StatType.XP_GAIN, 0.18f, ModifierType.PERCENT, "item:" + ID),
            new StatModifier(StatScope.ALL, StatType.MAGIC_DURATION, 0.10f, ModifierType.PERCENT, "item:" + ID)
        );
    }
}
