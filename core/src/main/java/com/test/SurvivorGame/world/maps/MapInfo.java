package com.test.SurvivorGame.world.maps;

public class MapInfo {

    private final String id;
    private final String displayName;
    private final String description;
    private final Class<? extends GameMap> mapClass;

    public MapInfo(String id,
                   String displayName,
                   String description,
                   Class<? extends GameMap> mapClass) {

        this.id = id;
        this.displayName = displayName;
        this.description = description;
        this.mapClass = mapClass;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public Class<? extends GameMap> getMapClass() {
        return mapClass;
    }
}
