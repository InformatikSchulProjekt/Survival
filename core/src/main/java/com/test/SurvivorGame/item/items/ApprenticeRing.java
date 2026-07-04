package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.item.BaseItem;
import com.test.SurvivorGame.item.ItemRarity;

import java.util.List;

public final class ApprenticeRing extends BaseItem {
    public static final String ID = "apprentice_ring";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Apprentice Ring";
    }

    @Override
    public String getDescription() {
        return "A basic magic ring used by beginner mages.";
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
                StatType.MAGIC_DAMAGE,
                0.10f,
                ModifierType.PERCENT,
                "item:" + ID
            )
        );
    }
}
