package com.test.SurvivorGame.player_class;

import com.test.SurvivorGame.core.stat.StatScope;

public final class Aeromancer extends BasePlayerClass {
    public static final String ID = "aeromancer";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Aeromancer";
    }

    @Override
    public StatScope getScope() {
        return StatScope.WIND;
    }

    @Override
    public String getDescription() {
        return "A storm-touched mage who moves with the wind and strikes before enemies can react. Aeromancers empower wind magic and flow through battle with speed and precision.";
    }


}
