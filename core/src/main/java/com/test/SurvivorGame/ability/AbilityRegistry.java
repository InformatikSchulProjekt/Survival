package com.test.SurvivorGame.ability;

import java.util.HashMap;
import java.util.Map;

public final class AbilityRegistry {
    private final Map<String, BaseAbility> abilities = new HashMap<>();

    public AbilityRegistry() {
        register(new HealthPackAbility());
    }

    public BaseAbility getAbility(String abilityId) {
        return abilities.get(abilityId);
    }

    private void register(BaseAbility ability) {
        abilities.put(ability.getId(), ability);
    }
}
