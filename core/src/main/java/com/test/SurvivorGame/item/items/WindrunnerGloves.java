package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.item.BaseItem;
import com.test.SurvivorGame.item.ItemRarity;

import java.util.List;

public final class WindrunnerGloves extends BaseItem {
    public static final String ID = "windrunner_gloves";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Windrunner Gloves";
    }

    @Override
    public String getDescription() {
        return "Light gloves wrapped in faint wind magic.";
    }

    @Override
    public ItemRarity getRarity() {
        return ItemRarity.RARE;
    }

    @Override
    public List<StatModifier> getModifiers() {
        return List.of(
            new StatModifier(
                StatScope.ALL,
                StatType.SPEED,
                0.10f,
                ModifierType.PERCENT,
                "item:" + ID
            ),
            new StatModifier(
                StatScope.ALL,
                StatType.DODGE_CHANCE,
                0.05f,
                ModifierType.FLAT,
                "item:" + ID
            )
        );
    }
}
