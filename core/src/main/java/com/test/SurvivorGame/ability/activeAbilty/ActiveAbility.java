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

    protected abstract float getCooldownReduction();

    public void tryActivate(float survivalTime) {
        if (isOnCooldown(survivalTime)) {
            return;
        }

        activate();
        cooldownStartTime = survivalTime + getDuration();
    }

    private float calcRealCooldown() {
        float cooldownReduction = getCooldownReduction(); // 0.5f = 50%

        cooldownReduction = Math.max(0f, cooldownReduction);

        float effectiveReduction;

        if (cooldownReduction <= 0.5f) {
            effectiveReduction = cooldownReduction;
        } else {
            float overflow = cooldownReduction - 0.5f;
            float maxReduction = 0.8f;

            effectiveReduction =
                0.5f
                    + (maxReduction - 0.5f)
                    * (overflow / (overflow + 0.5f));
        }

        return getCooldown() * (1f - effectiveReduction);
    }

    private boolean isOnCooldown(float survivalTime) {
        float timeLeft = calcRealCooldown() - (survivalTime - cooldownStartTime);

        if (timeLeft > 0) {
            System.out.println("[DEBUG] " + getName() + " cooldown left: " + timeLeft + "s");
            return true;
        }

        return false;
    }
}
