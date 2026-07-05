package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.*;
import com.test.SurvivorGame.item.*;

import java.util.List;

public final class EagleEyeScope extends BaseItem {
    public static final String ID = "eagle_eye_scope";

    @Override public String getID() { return ID; }
    @Override public String getName() { return "Eagle Eye Scope"; }
    @Override public String getDescription() { return "Precision above all else."; }
    @Override public ItemRarity getRarity() { return ItemRarity.RARE; }

    @Override
    public List<StatModifier> getModifiers() {
        return List.of(
            new StatModifier(StatScope.ALL, StatType.CRIT_CHANCE, 0.06f, ModifierType.FLAT, "item:" + ID)
        );
    }
}
