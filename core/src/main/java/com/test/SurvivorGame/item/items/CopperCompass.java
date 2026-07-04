package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.item.BaseItem;
import com.test.SurvivorGame.item.ItemRarity;

import java.util.List;

public final class CopperCompass extends BaseItem {
    public static final String ID = "copper_compass";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Copper Compass";
    }

    @Override
    public String getDescription() {
        return "A worn compass that always points toward hidden knowledge.";
    }

    @Override
    public ItemRarity getRarity() {
        return ItemRarity.COMMON;
    }

    @Override
    public List<StatModifier> getModifiers() {
        return List.of(
            new StatModifier(
                StatScope.ALL,
                StatType.XP_GAIN,
                0.08f,
                ModifierType.PERCENT,
                "item:" + ID
            )
        );
    }
}
