package com.test.SurvivorGame.ability.stat_abilities;

import com.test.SurvivorGame.ability.AbilityType;
import com.test.SurvivorGame.ability.BaseAbility;

public abstract class StatAbility extends BaseAbility {

    public AbilityType getAbilityType() {
        return AbilityType.STAT_ABILITY;
    }
}
