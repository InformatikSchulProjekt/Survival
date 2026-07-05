package com.test.SurvivorGame.ability.activeAbilty;

import com.test.SurvivorGame.ability.AbilityType;
import com.test.SurvivorGame.ability.BaseAbility;

public abstract class ActiveAbility extends BaseAbility {

    public AbilityType getAbilityType() {
        return AbilityType.ACTIVE_ABILITY;
    }
}
