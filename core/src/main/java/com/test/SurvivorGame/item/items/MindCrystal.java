package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.*;
import com.test.SurvivorGame.item.*;

import java.util.List;

public final class MindCrystal extends BaseItem {
    public static final String ID = "mind_crystal";

    @Override public String getID() { return ID; }
    @Override public String getName() { return "Mind Crystal"; }
    @Override public String getDescription() { return "A glowing crystal that absorbs knowledge from defeated enemies."; }
    @Override public ItemRarity getRarity() { return ItemRarity.EPIC; }

    @Override
    public List<StatModifier> getModifiers() {
        return List.of(
            new StatModifier(StatScope.ALL, StatType.XP_GAIN, 0.25f, ModifierType.PERCENT, "item:" + ID),
            new StatModifier(StatScope.ALL, StatType.MAGIC_DAMAGE, 0.10f, ModifierType.PERCENT, "item:" + ID)
        );
    }
}
