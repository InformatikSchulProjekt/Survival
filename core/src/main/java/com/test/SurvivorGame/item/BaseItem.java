package com.test.SurvivorGame.item;

import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.core.stat.StatModifier;

import java.util.List;

public abstract class BaseItem {
    public abstract String getID();

    public abstract String getName();

    public abstract String getDescription();

    public abstract List<StatModifier> getModifiers();

    public void onApply(PlayerStats playerStats) {
        onRemove(playerStats);

        for(StatModifier statMod : getModifiers()) {
            playerStats.addModifier(statMod);
        }
        System.out.println("Applied Item: "+ getID()); // debug
    }

    public void onRemove(PlayerStats playerStats) {
        playerStats.removeModifiersFromSource("item:" + getID());
    }
}
