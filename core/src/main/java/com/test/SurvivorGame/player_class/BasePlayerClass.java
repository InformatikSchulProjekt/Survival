package com.test.SurvivorGame.player_class;

import com.test.SurvivorGame.core.stat.*;

import java.util.List;

public abstract class BasePlayerClass {

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getID();

    public abstract StatScope getScope();

    public List<StatModifier> getModifiers() {
        return List.of(
            new StatModifier(
                getScope(),
                StatType.MAGIC_DURATION,
                0.20f,
                ModifierType.PERCENT,
                "class:" + getID()
            ),
            new StatModifier(
                getScope(),
                StatType.MAGIC_DAMAGE,
                0.30f,
                ModifierType.PERCENT,
                "class:" + getID()
            ),
            new StatModifier(
                getScope(),
                StatType.MAGIC_COOLDOWN_REDUCTION,
                0.10f,
                ModifierType.PERCENT,
                "class:" + getID()
            ),
            new StatModifier(
                getScope(),
                StatType.MAGIC_SIZE,
                0.15f,
                ModifierType.PERCENT,
                "class:" + getID()
            )
        );
    }

    // wird aufgerufen wenn Klasse initialisiert wird
    public void onApply(PlayerStats playerStats) {
        for(StatModifier statMod : getModifiers()) {
            playerStats.addModifier(statMod);
        }
        System.out.println("Applied Class: "+ getID()); // debug
    }

    public abstract String getStartAbility();
}
