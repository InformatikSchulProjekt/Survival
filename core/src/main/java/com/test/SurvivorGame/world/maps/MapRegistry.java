package com.test.SurvivorGame.world.maps;

import java.util.List;

public class MapRegistry {

    private static final List<MapInfo> MAPS = List.of(

        new MapInfo(
            "TestMap",
            "Test Map",
            "The first testing map."
        ),

        new MapInfo(
                "SecondMap",
                    "Second Map",
                    "Another testing map."
        )

    );

    public static List<MapInfo> getMaps() {
        return MAPS;
    }
}
