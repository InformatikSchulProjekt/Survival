package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.item.BaseItem;
import com.test.SurvivorGame.item.ItemRarity;

import java.util.List;

public final class MysticHourglass extends BaseItem {
    public static final String ID = "mystic_hourglass";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Mystic Hourglass";
    }

    @Override
    public String getDescription() {
        return "A strange hourglass that bends spell timing.";
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
                StatType.MAGIC_DURATION,
                0.15f,
                ModifierType.PERCENT,
                "item:" + ID
            ),
            new StatModifier(
                StatScope.ALL,
                StatType.MAGIC_COOLDOWN_REDUCTION,
                0.10f,
                ModifierType.PERCENT,
                "item:" + ID
            )
        );
    }
}
