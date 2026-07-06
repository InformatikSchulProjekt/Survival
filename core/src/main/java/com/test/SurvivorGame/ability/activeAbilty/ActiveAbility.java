package com.test.SurvivorGame.ability.activeAbilty;

import com.test.SurvivorGame.ability.AbilityType;
import com.test.SurvivorGame.ability.BaseAbility;

public abstract class ActiveAbility extends BaseAbility {

    private float cooldownStartTime = 0f;

    public AbilityType getAbilityType() {
        return AbilityType.ACTIVE_ABILITY;
    }

    protected abstract void activate();

    public abstract float getCooldown();

    public float getDuration() {
        return 0f;
    }

    public void tryActivate(float survivalTime) {
        if (isOnCooldown(survivalTime)) {
            return;
        }

        activate();
        cooldownStartTime = survivalTime + getDuration();
    }

    private boolean isOnCooldown(float survivalTime) {
        float timeLeft = getCooldown() - (survivalTime - cooldownStartTime);

        if (timeLeft > 0) {
            System.out.println("[DEBUG] " + getName() + " cooldown left: " + timeLeft + "s");
            return true;
        }

        return false;
    }
}
