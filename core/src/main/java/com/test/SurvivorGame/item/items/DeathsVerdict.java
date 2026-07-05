package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.*;
import com.test.SurvivorGame.item.*;

import java.util.List;

public final class DeathsVerdict extends BaseItem {
    public static final String ID = "deaths_verdict";

    @Override public String getID() { return ID; }
    @Override public String getName() { return "Death's Verdict"; }
    @Override public String getDescription() { return "A weapon feared not for how often it strikes, but for how final each strike becomes."; }
    @Override public ItemRarity getRarity() { return ItemRarity.LEGENDARY; }

    @Override
    public List<StatModifier> getModifiers() {
        return List.of(
            new StatModifier(StatScope.ALL, StatType.CRIT_MULTIPLIER, 2f, ModifierType.FLAT, "item:" + ID)
        );
    }
}
