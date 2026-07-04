package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.item.BaseItem;
import com.test.SurvivorGame.item.ItemRarity;

import java.util.List;

public final class BloodChalice extends BaseItem {
    public static final String ID = "blood_chalice";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Blood Chalice";
    }

    @Override
    public String getDescription() {
        return "An ancient chalice overflowing with cursed blood. Every wound you inflict feeds your own life force.";
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
                StatType.LIFE_STEAL,
                0.10f,
                ModifierType.FLAT,
                "item:" + ID
            )
        );
    }
}
