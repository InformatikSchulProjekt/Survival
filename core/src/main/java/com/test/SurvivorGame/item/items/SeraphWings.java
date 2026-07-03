package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.item.BaseItem;
import com.test.SurvivorGame.item.ItemRarity;

import java.util.List;

public final class SeraphWings extends BaseItem {
    public static final String ID = "seraph_wings";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Seraph Wings";
    }

    @Override
    public String getDescription() {
        return "Divine wings filled with celestial energy. They grant extreme movement speed and make attacks much harder to land.";
    }

    @Override
    public ItemRarity getRarity() {
        return ItemRarity.LEGENDARY;
    }

    @Override
    public List<StatModifier> getModifiers() {
        return List.of(
            new StatModifier(
                StatScope.ALL,
                StatType.SPEED,
                0.50f,
                ModifierType.PERCENT,
                "item:" + ID
            ),
            new StatModifier(
                StatScope.ALL,
                StatType.DODGE_CHANCE,
                0.20f,
                ModifierType.FLAT,
                "item:" + ID
            )
        );
    }
}
