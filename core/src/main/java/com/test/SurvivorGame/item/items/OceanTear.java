package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.item.BaseItem;
import com.test.SurvivorGame.item.ItemRarity;

import java.util.List;

public final class OceanTear extends BaseItem {
    public static final String ID = "ocean_tear";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Ocean Tear";
    }

    @Override
    public String getDescription() {
        return "A rare water gem that restores strength through flowing magic.";
    }

    @Override
    public ItemRarity getRarity() {
        return ItemRarity.EPIC;
    }

    @Override
    public List<StatModifier> getModifiers() {
        return List.of(
            new StatModifier(
                StatScope.WATER,
                StatType.MAGIC_COOLDOWN_REDUCTION,
                0.1f,
                ModifierType.PERCENT,
                "item:" + ID
            )
        );
    }
}
