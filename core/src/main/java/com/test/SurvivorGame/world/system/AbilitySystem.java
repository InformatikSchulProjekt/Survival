package com.test.SurvivorGame.world.system;

import com.test.SurvivorGame.entity.abilityObjects.AbilityObject;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.world.maps.GameMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AbilitySystem {
    private final ArrayList<AbilityObject> abilityObjects = new ArrayList<>();

    public void update(float deltaTime, GameMap map, ArrayList<Enemy> enemies) {
        checkCollisions(enemies);
        updateAbilities(deltaTime, map);
    }

    public void addAbility(AbilityObject abilityObject) {
        abilityObjects.add(abilityObject);
    }

    public List<AbilityObject> getAbilityObjects() {
        return Collections.unmodifiableList(abilityObjects);
    }

    private void updateAbilities(float deltaTime, GameMap map) {
        for (int i = abilityObjects.size() - 1; i >= 0; i--) {
            AbilityObject abilityObject = abilityObjects.get(i);
            abilityObject.update(deltaTime, map);

            if (abilityObject.getExpired()) {
                abilityObjects.remove(i);
            }
        }
    }

    private void checkCollisions(ArrayList<Enemy> enemies) {
        for (AbilityObject ability : abilityObjects) {
            for (Enemy enemy : enemies) {
                if (ability.overlaps(enemy)) {
                    ability.onHit(enemy);
                }
            }
        }
    }
}
