package com.test.SurvivorGame.player_class;

import java.util.HashMap;
import java.util.Map;

public final class PlayerClassRegistry {
    private final Map<String, BasePlayerClass> playerClasses = new HashMap<>();

    public PlayerClassRegistry() {
        register(new Pyromancer());
        register(new EarthMage());
    }

    public BasePlayerClass getPlayerClass(String playerClassId) {
        return playerClasses.get(playerClassId);
    }

    private void register(BasePlayerClass playerClass) {
        playerClasses.put(playerClass.getID(), playerClass);
    }
}
