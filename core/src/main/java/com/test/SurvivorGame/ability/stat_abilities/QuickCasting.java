package com.test.SurvivorGame.ability.stat_abilities;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;

import java.util.List;

public final class QuickCasting extends StatAbility {
    public static final String ID = "quick_casting";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Quick Casting";
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
                StatType.MAGIC_COOLDOWN_REDUCTION,
                amount * 0.05f,
                ModifierType.FLAT,
                "ability:" + ID
            )
        );
    }

    @Override
    public String getDescription(int level) {
        return "Reduce Magic Cooldown by 5%";
    }
}
