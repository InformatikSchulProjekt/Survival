package com.test.SurvivorGame.world.maps;

import com.test.SurvivorGame.core.data.DataLoader;

public class MapInfo {

    private final String id;
    private final String displayName;
    private DataLoader dataLoader;

    public MapInfo(String id, String displayName, DataLoader dataloader) {
        this.id = id;
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }


}
