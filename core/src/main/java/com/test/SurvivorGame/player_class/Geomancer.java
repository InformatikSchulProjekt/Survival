package com.test.SurvivorGame.player_class;

import com.test.SurvivorGame.core.stat.StatScope;

public final class Geomancer extends BasePlayerClass {
    public static final String ID = "geomancer";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Geomancer";
    }

    @Override
    public StatScope getScope() {
        return StatScope.EARTH;
    }

    @Override
    public String getStartAbility() {
        return "rock_blast";
    }

    @Override
    public String getDescription() {
        return "An earth-forged mage who draws strength from stone and soil. Geomancers empower earth magic and stand firm against even the heaviest attacks.";
    }
}
