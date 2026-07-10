package com.test.SurvivorGame.player_class;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;

import java.util.List;

public final class Merzmancer extends BasePlayerClass {
    public static final String ID = "merzmancer";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Merzmancer";
    }

    @Override
    public StatScope getScope() {
        return StatScope.ALL;
    }

    @Override
    public String getDescription() {
        return "A mysterious chancellor of chaos whose speeches are almost as devastating as his magic. Believes every problem can be solved by working longer hours, much to the despair of the working class.";
    }

    @Override
    public List<StatModifier> getModifiers() {
        return List.of(
            new StatModifier(
                getScope(),
                StatType.MAGIC_DURATION,
                0.67f,
                ModifierType.PERCENT,
                "class:" + getID()
            ),
            new StatModifier(
                getScope(),
                StatType.MAGIC_DAMAGE,
                0.67f,
                ModifierType.PERCENT,
                "class:" + getID()
            ),
            new StatModifier(
                getScope(),
                StatType.MAGIC_COOLDOWN_REDUCTION,
                0.67f,
                ModifierType.PERCENT,
                "class:" + getID()
            ),
            new StatModifier(
                getScope(),
                StatType.MAGIC_SIZE,
                0.67f,
                ModifierType.PERCENT,
                "class:" + getID()
            ),
            new StatModifier(
                getScope(),
                StatType.MAX_HEALTH,
                0.67f,
                ModifierType.PERCENT,
                "class:" + getID()
            ),
            new StatModifier(
                getScope(),
                StatType.SPEED,
                -5.1f, // merzmancer sind alt und senil und laufen deswegen (langsam) immer in die Falsche Richtung
                ModifierType.FLAT,
                "class:" + getID()
            )
        );
    }

    @Override
    public String getStartAbility() {
        return "fire_storm";
    }


}
