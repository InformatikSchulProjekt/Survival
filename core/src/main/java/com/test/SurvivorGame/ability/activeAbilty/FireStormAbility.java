package com.test.SurvivorGame.ability.activeAbilty;

public class FireStormAbility extends ActiveAbility{

    public static final String ID = "firestorm";

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Firestorm Ability";
    }

    @Override
    public int getMaxAmount() {
        return 5;
    }
}
