package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.item.BaseItem;
import com.test.SurvivorGame.item.ItemRarity;

import java.util.List;

public final class SmallHealthCharm extends BaseItem {
    public static final String ID = "small_health_charm";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Small Health Charm";
    }

    @Override
    public String getDescription() {
        return "A small charm that strengthens the body slightly.";
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
                StatType.MAX_HEALTH,
                0.10f,
                ModifierType.PERCENT,
                "item:" + ID
            )
        );
    }
}
