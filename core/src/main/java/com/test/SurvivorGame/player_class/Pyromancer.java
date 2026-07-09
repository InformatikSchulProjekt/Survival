package com.test.SurvivorGame.player_class;

import com.test.SurvivorGame.core.stat.StatScope;

public final class Pyromancer extends BasePlayerClass {
    public static final String ID = "pyromancer";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Pyromancer";
    }

    @Override
    public StatScope getScope() {
        return StatScope.FIRE;
    }

    @Override
    public String getStartAbility() {
        return "fire_arrow";
    }

    @Override
    public String getDescription() {
        return "A flame-wielding mage who turns raw fire magic into overwhelming destruction. Pyromancers empower fire spells and burn through enemies before they can get close.";
    }


}
