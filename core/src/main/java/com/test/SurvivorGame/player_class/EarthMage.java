package com.test.SurvivorGame.player_class;

import com.test.SurvivorGame.core.stat.StatScope;

public final class EarthMage extends BasePlayerClass {
    public static final String ID = "EarthMage";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "EarthMage";
    }

    @Override
    public StatScope getScope() {
        return StatScope.Earth;
    }

    @Override
    public String getDescription() {
        return "Earth mage ."; // temporär, da keine Ahnung was da rein soll
    }


}
