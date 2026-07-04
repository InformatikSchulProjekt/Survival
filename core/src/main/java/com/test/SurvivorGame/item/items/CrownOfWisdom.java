package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.*;
import com.test.SurvivorGame.item.*;

import java.util.List;

public final class CrownOfWisdom extends BaseItem {
    public static final String ID = "crown_of_wisdom";

    @Override public String getID() { return ID; }
    @Override public String getName() { return "Crown of Wisdom"; }
    @Override public String getDescription() { return "A royal crown that turns every victory into greater understanding."; }
    @Override public ItemRarity getRarity() { return ItemRarity.EPIC; }

    @Override
    public List<StatModifier> getModifiers() {
        return List.of(
            new StatModifier(StatScope.ALL, StatType.XP_GAIN, 0.35f, ModifierType.PERCENT, "item:" + ID),
            new StatModifier(StatScope.ALL, StatType.MAGIC_SIZE, 0.15f, ModifierType.PERCENT, "item:" + ID)
        );
    }
}
