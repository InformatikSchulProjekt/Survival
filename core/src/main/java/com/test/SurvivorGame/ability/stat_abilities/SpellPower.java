package com.test.SurvivorGame.ability.stat_abilities;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;

import java.util.List;

public final class SpellPower extends StatAbility {
    public static final String ID = "spell_power";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Spell Power";
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
                StatType.MAGIC_DAMAGE,
                amount * 0.1f,
                ModifierType.FLAT,
                "ability:" + ID
            )
        );
    }

    @Override
    public String getDescription(int level) {
        return "Increase Magic Damage by 10%";
    }

}
