package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.item.BaseItem;
import com.test.SurvivorGame.item.ItemRarity;

import java.util.List;

public final class Spores extends BaseItem {
    public static final String ID = "spores";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Spores";
    }

    @Override
    public String getDescription() {
        return "Invisible spores drift through the battlefield, permanently decreasing the strength of all who breathe them.";
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
                StatType.ENEMY_HP,
                -0.04f,
                ModifierType.PERCENT,
                "item:" + ID
            )
        );
    }
}
