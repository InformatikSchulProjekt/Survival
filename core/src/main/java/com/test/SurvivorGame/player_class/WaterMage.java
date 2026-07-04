package com.test.SurvivorGame.player_class;

import com.test.SurvivorGame.core.stat.StatScope;

public final class WaterMage extends BasePlayerClass {
    public static final String ID = "Water mage";

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
        return "Water mage ."; // temporär, da keine Ahnung was da rein soll
    }


}
