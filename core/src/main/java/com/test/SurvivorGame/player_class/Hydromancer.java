package com.test.SurvivorGame.player_class;

import com.test.SurvivorGame.core.stat.StatScope;

public final class Hydromancer extends BasePlayerClass {
    public static final String ID = "hydromancer";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Hydromancer";
    }

    @Override
    public StatScope getScope() {
        return StatScope.Water;
    }

    @Override
    public String getDescription() {
        return "A water-bound mage who bends tides into protection and recovery. Hydromancers empower water magic and outlast enemies through healing and steady control.";
    }


}
