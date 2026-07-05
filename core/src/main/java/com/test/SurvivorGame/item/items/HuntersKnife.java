package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.*;
import com.test.SurvivorGame.item.*;

import java.util.List;

public final class HuntersKnife extends BaseItem {
    public static final String ID = "hunters_knife";

    @Override public String getID() { return ID; }
    @Override public String getName() { return "Hunter's Knife"; }
    @Override public String getDescription() { return "A lightweight blade made to exploit the smallest weaknesses."; }
    @Override public ItemRarity getRarity() { return ItemRarity.COMMON; }

    @Override
    public List<StatModifier> getModifiers() {
        return List.of(
            new StatModifier(StatScope.ALL, StatType.CRIT_CHANCE, 0.02f, ModifierType.FLAT, "item:" + ID)
        );
    }
}
