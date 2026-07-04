package com.test.SurvivorGame.player_class;

import com.test.SurvivorGame.core.stat.StatScope;

public final class WindMage extends BasePlayerClass {
    public static final String ID = "Wind mage";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Wind mage";
    }

    @Override
    public StatScope getScope() {
        return StatScope.WIND;
    }

    @Override
    public String getDescription() {
        return "Wind mage ."; // temporär, da keine Ahnung was da rein soll
    }


}
