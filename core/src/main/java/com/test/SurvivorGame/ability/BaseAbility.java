package com.test.SurvivorGame.ability;

import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;

import java.util.List;

public abstract class BaseAbility {
    public abstract String getID();

    public abstract String getName();

    public abstract int getMaxAmount();

    public String getDescription(int level) {
        return "No description set.";
    }

    public List<StatModifier> getModifiers(int amount) {
        return List.of();
    }

    public void onApply(PlayerStats playerStats, int amount) {
        onRemove(playerStats);

        for(StatModifier statMod : getModifiers(amount)) {
            playerStats.addModifier(statMod);
            getModifiers(amount);
        }
        System.out.println("Applied Ability: "+ getID() + " | " + amount); // debug
    }

    public abstract AbilityType getAbilityType();

    public StatScope getScope() {
        return null;
    }

    public void onRemove(PlayerStats playerStats) {
        playerStats.removeModifiersFromSource("ability:" + getID());
    }

    public void activate(){}; // nicht jede ability erzeugt objekte


}
