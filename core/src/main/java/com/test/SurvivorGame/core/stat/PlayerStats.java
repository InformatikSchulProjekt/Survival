package com.test.SurvivorGame.core.stat;

import java.util.ArrayList;
import java.util.EnumMap;

public final class PlayerStats {
    private final EnumMap<StatType, Float> baseStats = new EnumMap<>(StatType.class);
    private final ArrayList<StatModifier> modifiers = new ArrayList<>();

    public PlayerStats() {
        baseStats.put(StatType.MAX_HEALTH, 10f);
        baseStats.put(StatType.SPEED, 5f);
        baseStats.put(StatType.HEALING, 0.1f);
        baseStats.put(StatType.PLAYER_SIZE, 1f);
        baseStats.put(StatType.RESISTANCE, 1f);
        baseStats.put(StatType.DODGE_CHANCE, 0f);
        baseStats.put(StatType.CRIT_CHANCE, 0.01f);
        baseStats.put(StatType.CRIT_MULTIPLIER, 1f);

        baseStats.put(StatType.CHEST_CHANCE, 0.01f);
        baseStats.put(StatType.REVIVES, 0f);
        baseStats.put(StatType.LIFE_STEAL, 0f);
        baseStats.put(StatType.XP_GAIN, 1f);

        baseStats.put(StatType.ENEMY_HP, 1f);

        baseStats.put(StatType.MAGIC_DURATION, 1f);
        baseStats.put(StatType.MAGIC_DAMAGE, 1f);
        baseStats.put(StatType.MAGIC_COOLDOWN, 1f);
        baseStats.put(StatType.MAGIC_SIZE, 1f);
    }

    public void addModifier(StatModifier modifier) {
        modifiers.add(modifier);
    }

    public void removeModifiersFromSource(String sourceId) {
        modifiers.removeIf(modifier -> modifier.getSourceId().equals(sourceId));
    }

    public float getStat(StatScope scope, StatType statType) {
        float baseValue = baseStats.getOrDefault(statType, 0f);
        float flatBonus = 0f;
        float percentBonus = 0f;

        for (StatModifier modifier : modifiers) {
            if (modifier.getStatType() != statType) {
                continue;
            }

            if (modifier.getScope() != StatScope.ALL && modifier.getScope() != scope) {
                continue;
            }

            if (modifier.getModifierType() == ModifierType.FLAT) {
                flatBonus += modifier.getValue();
            } else if (modifier.getModifierType() == ModifierType.PERCENT) {
                percentBonus += modifier.getValue();
            }
        }

        return (baseValue + flatBonus) * (1f + percentBonus);
    }

    public float getStat(StatType statType) {
        return getStat(StatScope.ALL, statType);
    }
}
