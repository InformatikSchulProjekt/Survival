package com.test.SurvivorGame.ability;

import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;

import java.util.List;

// Das hier dient als eine Beispiel Ability.
public final class HealthPackAbility extends BaseAbility {
    public static final String ID = "health_pack";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Health Pack";
    }

    @Override
    public int getMaxAmount() {
        return 5;
    }

    @Override
    public String getDescription() {
        return "Diese Ability macht dich mehr healthy"; // temporär, da nur ExampleAbility eigentlich
    }

    @Override
    public List<StatModifier> getModifiers(int amount) {
        return List.of(
            new StatModifier(
                StatScope.ALL,
                StatType.MAX_HEALTH,
                amount * 3f,
                ModifierType.FLAT,
                "ability:" + ID
            )
        );
    }
}
