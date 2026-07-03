package com.test.SurvivorGame.item;

import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;

import java.util.List;

public final class ChainmailHauberk extends BaseItem {
    public static final String ID = "chainmail_hauberk";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Chainmail Hauberk";
    }

    @Override
    public String getDescription() {
        return "Heavy chainmail armor that makes you harder to kill.";
    }

    @Override
    public List<StatModifier> getModifiers() {
        return List.of(
            new StatModifier(
                StatScope.ALL,
                StatType.RESISTANCE,
                0.15f,
                ModifierType.PERCENT,
                "item:" + ID
            ),
            new StatModifier(
                StatScope.ALL,
                StatType.SPEED,
                -0.15f,
                ModifierType.PERCENT,
                "item:" + ID
            )
        );
    }
}
