package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.item.BaseItem;
import com.test.SurvivorGame.item.ItemRarity;

import java.util.List;

public final class RogueScarf extends BaseItem {
    public static final String ID = "rogue_scarf";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Rogue Scarf";
    }

    @Override
    public String getDescription() {
        return "A light scarf worn by quick fighters. Makes it slightly easier to avoid incoming attacks.";
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
                StatType.DODGE_CHANCE,
                0.08f,
                ModifierType.FLAT,
                "item:" + ID
            )
        );
    }
}

