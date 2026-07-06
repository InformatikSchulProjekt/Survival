package com.test.SurvivorGame.world.maps;

import java.util.List;

public class MapRegistry {

    private static final List<MapInfo> MAPS = List.of(

        new MapInfo(
            "TestMap",
            "Test Map",
            "The first testing map.",
            TestMap.class
        ),

        new MapInfo(
            "SecondTestMap",
            "Second Test Map",
            "second testing map.",
            SecondTestMap.class
        )

    );

    public static List<MapInfo> getMaps() {
        return MAPS;
    }
}
