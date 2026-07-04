package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.*;
import com.test.SurvivorGame.item.*;

import java.util.List;

public final class ScholarsPendant extends BaseItem {
    public static final String ID = "scholars_pendant";

    @Override public String getID() { return ID; }
    @Override public String getName() { return "Scholar's Pendant"; }
    @Override public String getDescription() { return "A pendant carried by mages who learn from every battle."; }
    @Override public ItemRarity getRarity() { return ItemRarity.RARE; }

    @Override
    public List<StatModifier> getModifiers() {
        return List.of(
            new StatModifier(StatScope.ALL, StatType.XP_GAIN, 0.12f, ModifierType.PERCENT, "item:" + ID),
            new StatModifier(StatScope.ALL, StatType.MAGIC_DAMAGE, 0.05f, ModifierType.PERCENT, "item:" + ID)
        );
    }
}
