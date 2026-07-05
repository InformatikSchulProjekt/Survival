package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.*;
import com.test.SurvivorGame.item.*;

import java.util.List;

public final class AssassinsDagger extends BaseItem {
    public static final String ID = "assassins_dagger";

    @Override public String getID() { return ID; }
    @Override public String getName() { return "Assassin's Dagger"; }
    @Override public String getDescription() { return "Favored by killers who never waste an opportunity."; }
    @Override public ItemRarity getRarity() { return ItemRarity.RARE; }

    @Override
    public List<StatModifier> getModifiers() {
        return List.of(
            new StatModifier(StatScope.ALL, StatType.CRIT_CHANCE, 0.05f, ModifierType.FLAT, "item:" + ID)
        );
    }
}
