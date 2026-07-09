package com.test.SurvivorGame.world.system;

import com.test.SurvivorGame.entity.drops.DroppedObject;
import com.test.SurvivorGame.world.maps.GameMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DropSystem {

    private final ArrayList<DroppedObject> droppedObjects = new ArrayList<>();

    public void update(float deltaTime, GameMap map) {
        for (int i = droppedObjects.size() - 1; i >= 0; i--) {
            DroppedObject drop = droppedObjects.get(i);

            drop.update(deltaTime, map);

            if (drop.isDespawned()) {
                droppedObjects.remove(i);
            }
        }
    }

    public void addDrop(DroppedObject drop) {
        droppedObjects.add(drop);
    }

    public List<DroppedObject> getDroppedObjects() {
        return Collections.unmodifiableList(droppedObjects);
    }
}
