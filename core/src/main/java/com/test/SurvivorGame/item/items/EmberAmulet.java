package com.test.SurvivorGame.item.items;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.item.BaseItem;
import com.test.SurvivorGame.item.ItemRarity;

import java.util.List;

public final class EmberAmulet extends BaseItem {
    public static final String ID = "ember_amulet";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Ember Amulet";
    }

    @Override
    public String getDescription() {
        return "A warm amulet that strengthens destructive fire magic.";
    }

    @Override
    public ItemRarity getRarity() {
        return ItemRarity.RARE;
    }

    @Override
    public List<StatModifier> getModifiers() {
        return List.of(
            new StatModifier(
                StatScope.FIRE,
                StatType.MAGIC_DAMAGE,
                0.20f,
                ModifierType.PERCENT,
                "item:" + ID
            )
        );
    }
}
