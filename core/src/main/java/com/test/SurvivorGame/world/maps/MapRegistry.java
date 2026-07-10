package com.test.SurvivorGame.world.maps;

import java.util.List;

public class MapRegistry {

    private static final List<GameMap> MAPS = List.of(
        new SafeMeadows(),
        new DarkFrontier()
    );

    public static List<GameMap> getMaps() {
        return MAPS;
    }

    public static GameMap getMap(String id) {

        for (GameMap map : MAPS) {

            if (map.getId().equals(id)) {
                return map;
            }
        }

        throw new IllegalArgumentException("Unknown map: " + id);
    }
}
