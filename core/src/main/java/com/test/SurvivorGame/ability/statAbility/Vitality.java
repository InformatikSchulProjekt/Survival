package com.test.SurvivorGame.ability.statAbility;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;

import java.util.List;

public final class Vitality extends StatAbility {
    public static final String ID = "vitality";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Vitality";
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
                StatType.MAX_HEALTH,
                amount * 0.15f,
                ModifierType.PERCENT,
                "ability:" + ID
            )
        );
    }

    @Override
    public String getDescription(int level) {
        return "Increase Max Health by 15%";
    }
}
