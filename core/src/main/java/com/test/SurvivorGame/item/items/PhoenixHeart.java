package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.item.BaseItem;
import com.test.SurvivorGame.item.ItemRarity;

import java.util.List;

public final class PhoenixHeart extends BaseItem {
    public static final String ID = "phoenix_heart";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Phoenix Heart";
    }

    @Override
    public String getDescription() {
        return "A burning heart crystal that refuses to fully die out.";
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
                StatType.MAX_HEALTH,
                0.25f,
                ModifierType.PERCENT,
                "item:" + ID
            ),
            new StatModifier(
                StatScope.ALL,
                StatType.HEALING,
                0.25f,
                ModifierType.PERCENT,
                "item:" + ID
            )
        );
    }
}
