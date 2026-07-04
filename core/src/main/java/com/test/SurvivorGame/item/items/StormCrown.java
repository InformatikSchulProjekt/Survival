package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.item.BaseItem;
import com.test.SurvivorGame.item.ItemRarity;

import java.util.List;

public final class StormCrown extends BaseItem {
    public static final String ID = "storm_crown";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Storm Crown";
    }

    @Override
    public String getDescription() {
        return "A cracked crown filled with violent storm energy.";
    }

    @Override
    public ItemRarity getRarity() {
        return ItemRarity.EPIC;
    }

    @Override
    public List<StatModifier> getModifiers() {
        return List.of(
            new StatModifier(
                StatScope.WIND,
                StatType.MAGIC_DAMAGE,
                0.25f,
                ModifierType.PERCENT,
                "item:" + ID
            )
        );
    }
}
