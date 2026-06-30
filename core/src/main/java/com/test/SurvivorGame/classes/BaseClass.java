package com.test.SurvivorGame.classes;

import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.core.stat.StatModifier;

import java.util.List;

public abstract class BaseClass {

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getID();

    public abstract List<StatModifier> getModifiers();

    // wird aufgerufen wenn Klasse initialisiert wird
    public void onApply(PlayerStats playerStats) {
        for(StatModifier statMod : getModifiers()) {
            playerStats.addModifier(statMod);
        }
    }
}
