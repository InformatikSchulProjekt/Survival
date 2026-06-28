package com.test.SurvivorGame.ability;

import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.stat.ModifierType;
import com.test.SurvivorGame.stat.StatModifier;
import com.test.SurvivorGame.stat.StatScope;
import com.test.SurvivorGame.stat.StatType;

// Das hier dient als eine Beispiel Ability.
public final class HealthPackAbility extends BaseAbility {
    public static final String ID = "health_pack";

    @Override
    public String getId() {
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
    public void onApply(PlayerState playerState, int amount) {
        onRemove(playerState);

        playerState.getPlayerStats().addModifier(
            new StatModifier(
                StatScope.ALL,
                StatType.MAX_HEALTH,
                amount * 3f,
                ModifierType.FLAT,
                "ability:" + ID
            )
        );
        //debug:
        System.out.println("Applied Ability: "+ ID + " | " + amount);
    }
}
