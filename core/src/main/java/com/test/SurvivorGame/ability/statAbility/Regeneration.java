package com.test.SurvivorGame.ability.statAbility;

import com.test.SurvivorGame.ability.BaseAbility;
import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;

import java.util.List;

public final class Regeneration extends BaseAbility {
    public static final String ID = "regeneration";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Regeneration";
    }

    @Override
    public int getMaxAmount() {
        return 4;
    }

    @Override
    public List<StatModifier> getModifiers(int amount) {
        return List.of(
            new StatModifier(
                StatScope.ALL,
                StatType.HEALING,
                amount * 0.05f,
                ModifierType.FLAT,
                "ability:" + ID
            )
        );
    }

    @Override
    public String getDescription(int level) {
        return "Increase Healing by 0.05";
    }
}
