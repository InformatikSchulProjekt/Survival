package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.item.BaseItem;
import com.test.SurvivorGame.item.ItemRarity;

import java.util.List;

public final class ImmortalSoulstone extends BaseItem {
    public static final String ID = "immortal_soulstone";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Immortal Soulstone";
    }

    @Override
    public String getDescription() {
        return "A forbidden stone that anchors the soul to the body after death.";
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
                StatType.REVIVES,
                1f,
                ModifierType.FLAT,
                "item:" + ID
            ),
            new StatModifier(
                StatScope.ALL,
                StatType.MAX_HEALTH,
                0.30f,
                ModifierType.PERCENT,
                "item:" + ID
            )
        );
    }
}
