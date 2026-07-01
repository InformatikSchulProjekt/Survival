package com.test.SurvivorGame.ability;

import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.core.stat.StatModifier;

import java.util.List;

public abstract class BaseAbility {
    public abstract String getID();

    public abstract String getName();

    public abstract int getMaxAmount();

    public abstract String getDescription();

    public abstract List<StatModifier> getModifiers(int amount);

    public void onApply(PlayerStats playerStats, int amount) {
        onRemove(playerStats);

        for(StatModifier statMod : getModifiers(amount)) {
            playerStats.addModifier(statMod);
        }
        System.out.println("Applied Ability: "+ getID() + " | " + amount); // debug
    }

    public void onRemove(PlayerStats playerStats) {
        playerStats.removeModifiersFromSource("ability:" + getID());
    }

    public void activate(){}; // nicht jede ability erzeugt objekte
}
