package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.item.BaseItem;
import com.test.SurvivorGame.item.ItemRarity;

import java.util.List;

public final class LeatherBoots extends BaseItem {
    public static final String ID = "leather_boots";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Leather Boots";
    }

    @Override
    public String getDescription() {
        return "Simple travel boots that make movement slightly faster.";
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
                StatType.SPEED,
                0.08f,
                ModifierType.PERCENT,
                "item:" + ID
            )
        );
    }
}
