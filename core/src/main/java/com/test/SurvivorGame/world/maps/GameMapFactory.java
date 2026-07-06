package com.test.SurvivorGame.world.maps;

public class GameMapFactory {

    public static GameMap create(String mapId) {

        switch (mapId) {

            case "TestMap":
                return new TestMap();

            case "SecondTestMap":
                return new SecondTestMap();

            default:
                throw new IllegalArgumentException(
                    "Unknown map: " + mapId
                );
        }
    }
}
