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
        return "Water mage";
    }

    @Override
    public StatScope getScope() {
        return StatScope.Water;
    }

    @Override
    public String getDescription() {
        return "A water-bound mage who bends tides."; // temporär, da keine Ahnung was da rein soll
    }


}
