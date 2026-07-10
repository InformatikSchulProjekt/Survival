package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.item.BaseItem;
import com.test.SurvivorGame.item.ItemRarity;

import java.util.List;

public final class CrownOfTheArchmage extends BaseItem {
    public static final String ID = "crown_of_the_archmage";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Crown of the Archmage";
    }

    @Override
    public String getDescription() {
        return "A legendary crown that floods every spell with overwhelming arcane force.";
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
                StatType.MAGIC_DAMAGE,
                0.40f,
                ModifierType.PERCENT,
                "item:" + ID
            ),
            new StatModifier(
                StatScope.ALL,
                StatType.MAGIC_COOLDOWN_REDUCTION,
                0.20f,
                ModifierType.PERCENT,
                "item:" + ID
            ),
            new StatModifier(
                StatScope.ALL,
                StatType.MAGIC_SIZE,
                0.20f,
                ModifierType.PERCENT,
                "item:" + ID
            )
        );
    }
}
