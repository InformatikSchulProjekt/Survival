package com.test.SurvivorGame.ability.stat_abilities;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;

import java.util.List;

public final class SwiftStep extends StatAbility {
    public static final String ID = "swift_step";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Swift Step";
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
                StatType.SPEED,
                amount * 0.1f,
                ModifierType.PERCENT,
                "ability:" + ID
            )
        );
    }

    @Override
    public String getDescription(int level) {
        return "Increase Speed by 10%";
    }
}
