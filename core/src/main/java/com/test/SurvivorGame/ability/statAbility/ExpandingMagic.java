package com.test.SurvivorGame.ability.statAbility;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;

import java.util.List;

public final class ExpandingMagic extends StatAbility {
    public static final String ID = "expanding_magic";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Expanding Magic";
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
                StatType.MAGIC_SIZE,
                amount * 0.10f,
                ModifierType.FLAT,
                "ability:" + ID
            )
        );
    }

    @Override
    public String getDescription(int level) {
        return "Increase Magic Size by 10%";
    }
}
