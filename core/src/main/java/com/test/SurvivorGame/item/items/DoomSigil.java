package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.item.BaseItem;
import com.test.SurvivorGame.item.ItemRarity;

import java.util.List;

public final class DoomSigil extends BaseItem {
    public static final String ID = "doom_sigil";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Doom Sigil";
    }

    @Override
    public String getDescription() {
        return "An ancient symbol that curses every enemy entering the battlefield.";
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
                StatType.ENEMY_HP,
                -0.10f,
                ModifierType.PERCENT,
                "item:" + ID
            ),
            new StatModifier(
                StatScope.ALL,
                StatType.MAGIC_DAMAGE,
                0.15f,
                ModifierType.PERCENT,
                "item:" + ID
            )
        );
    }
}
