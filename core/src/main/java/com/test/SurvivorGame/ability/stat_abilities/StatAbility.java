package com.test.SurvivorGame.ability.stat_abilities;

import com.test.SurvivorGame.ability.AbilityType;
import com.test.SurvivorGame.ability.BaseAbility;

/**
 * Abstrakte Basisklasse für Fähigkeiten, die Werte des Spielers verändern.
 * <p>
 * Stat-Abilities besitzen keine eigene Aktivierungslogik, sondern dienen zur
 * dauerhaften Verbesserung bestimmter Spielerwerte, beispielsweise Schaden,
 * Bewegungsgeschwindigkeit, Lebenspunkte oder Regeneration.
 * <p>
 * Die Klasse legt außerdem fest, dass alle Unterklassen dem AbilityType
 * {@link AbilityType#STAT_ABILITY} zugeordnet werden.
 */
public abstract class StatAbility extends BaseAbility {

    public AbilityType getAbilityType() {
        return AbilityType.STAT_ABILITY;
    }
}
