package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.item.BaseItem;
import com.test.SurvivorGame.item.ItemRarity;

import java.util.List;

public final class TitanBelt extends BaseItem {
    public static final String ID = "titan_belt";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Titan Belt";
    }

    @Override
    public String getDescription() {
        return "A massive belt carrying the weight of ancient stone giants.";
    }

    @Override
    public ItemRarity getRarity() {
        return ItemRarity.EPIC;
    }

    @Override
    public List<StatModifier> getModifiers() {
        return List.of(
            new StatModifier(
                StatScope.ALL,
                StatType.RESISTANCE,
                0.35f,
                ModifierType.PERCENT,
                "item:" + ID
            ),
            new StatModifier(
                StatScope.ALL,
                StatType.MAX_HEALTH,
                0.15f,
                ModifierType.PERCENT,
                "item:" + ID
            ),
            new StatModifier(
                StatScope.ALL,
                StatType.SPEED,
                -0.08f,
                ModifierType.PERCENT,
                "item:" + ID
            )
        );
    }
}
