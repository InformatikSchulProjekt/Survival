package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.*;
import com.test.SurvivorGame.item.*;

import java.util.List;

public final class EyeOfThePredator extends BaseItem {
    public static final String ID = "eye_of_the_predator";

    @Override public String getID() { return ID; }
    @Override public String getName() { return "Eye of the Predator"; }
    @Override public String getDescription() { return "Those who meet its gaze rarely survive the next strike."; }
    @Override public ItemRarity getRarity() { return ItemRarity.LEGENDARY; }

    @Override
    public List<StatModifier> getModifiers() {
        return List.of(
            new StatModifier(StatScope.ALL, StatType.CRIT_CHANCE, 0.10f, ModifierType.FLAT, "item:" + ID),
            new StatModifier(StatScope.ALL, StatType.CRIT_MULTIPLIER, 1f, ModifierType.FLAT, "item:" + ID)
        );
    }
}
