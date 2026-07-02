package com.test.SurvivorGame.ability.activeAbility;

import com.test.SurvivorGame.ability.AbilityType;
import com.test.SurvivorGame.ability.BaseAbility;

public abstract class ActiveAbility extends BaseAbility {

    public AbilityType getAbilityType() {
        return AbilityType.ACTIVE_ABILITY;
    }
}
