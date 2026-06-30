package com.test.SurvivorGame.item;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;

import java.util.List;

public final class Iron_Chestplate extends BaseItem {
    public static final String ID = "iron_chestplate";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Iron Chestplate";
    }

    @Override
    public String getDescription() {
        return "Diese Armor macht dich resistenter."; // temporär, da nur ExampleItem eigentlich
    }

    @Override
    public List<StatModifier> getModifiers() {
        return List.of(
            new StatModifier(
                StatScope.ALL,
                StatType.RESISTANCE,
                0.20f,
                ModifierType.PERCENT,
                "item:" + ID
            )
        );
    }
}
