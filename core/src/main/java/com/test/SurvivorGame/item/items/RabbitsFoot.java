package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.item.BaseItem;
import com.test.SurvivorGame.item.ItemRarity;

import java.util.List;

public final class RabbitsFoot extends BaseItem {
    public static final String ID = "rabbits_foot";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Rabbit's Foot";
    }

    @Override
    public String getDescription() {
        return "A strange relic that seems to twist probability in your favor.";
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
                StatType.CHEST_CHANCE,
                0.50f,
                ModifierType.PERCENT,
                "item:" + ID
            ),
            new StatModifier(
                StatScope.ALL,
                StatType.DODGE_CHANCE,
                0.15f,
                ModifierType.FLAT,
                "item:" + ID
            ),
            new StatModifier(
                StatScope.ALL,
                StatType.SPEED,
                0.10f,
                ModifierType.PERCENT,
                "item:" + ID
            )
        );
    }
}
