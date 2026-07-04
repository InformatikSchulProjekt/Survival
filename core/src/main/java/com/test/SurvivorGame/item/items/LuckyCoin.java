package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.item.BaseItem;
import com.test.SurvivorGame.item.ItemRarity;

import java.util.List;

public final class LuckyCoin extends BaseItem {
    public static final String ID = "lucky_coin";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Lucky Coin";
    }

    @Override
    public String getDescription() {
        return "An old coin that slightly improves the chance of finding treasure.";
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
                StatType.CHEST_CHANCE,
                0.1f,
                ModifierType.PERCENT,
                "item:" + ID
            )
        );
    }
}
