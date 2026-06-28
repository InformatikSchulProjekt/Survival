package com.test.SurvivorGame.stat;

public final class StatModifier {
    private final StatScope scope;
    private final StatType statType;
    private final float value;
    private final ModifierType modifierType;
    private final String sourceId;

    public StatModifier(
        StatScope scope,
        StatType statType,
        float value,
        ModifierType modifierType,
        String sourceId
    ) {
        this.scope = scope;
        this.statType = statType;
        this.value = value;
        this.modifierType = modifierType;
        this.sourceId = sourceId;
    }

    public StatScope getScope() {
        return scope;
    }

    public StatType getStatType() {
        return statType;
    }

    public float getValue() {
        return value;
    }

    public ModifierType getModifierType() {
        return modifierType;
    }

    public String getSourceId() {
        return sourceId;
    }
}
