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
        return "Earth Mage";
    }

    @Override
    public StatScope getScope() {
        return StatScope.Earth;
    }

    @Override
    public String getDescription() {
        return "Geomancers empower earth magic."; // temporär, da keine Ahnung was da rein soll
    }


}
