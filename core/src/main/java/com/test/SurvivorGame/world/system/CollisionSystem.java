package com.test.SurvivorGame.world.system;

import com.test.SurvivorGame.entity.abilityObjects.AbilityObject;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.entity.Player;

import java.util.List;

public class CollisionSystem {

    private float damageTimer = 0f;
    private static final float DAMAGE_INTERVAL = 0.5f;

    public void checkCollisions(
        float deltaTime,
        Player player,
        List<Enemy> enemies,
        List<AbilityObject> abilityObjects
    ) {
        checkAbilityEnemyCollisions(abilityObjects, enemies);
        checkPlayerEnemyCollisions(deltaTime, player, enemies);
    }

    private void checkAbilityEnemyCollisions(List<AbilityObject> abilities, List<Enemy> enemies) {
        for (AbilityObject ability : abilities) {
            for (Enemy enemy : enemies) {
                if (ability.overlaps(enemy)) {
                    ability.onHit(enemy);
                }
            }
        }
    }

    private void checkPlayerEnemyCollisions(float deltaTime, Player player, List<Enemy> enemies) {
        damageTimer += deltaTime;

        if (damageTimer >= DAMAGE_INTERVAL) {
            float dmgTaken = 0;

            for (Enemy enemy : enemies) {
                if (player.overlaps(enemy)) {
                    dmgTaken += enemy.getDamage();
                }
            }

            if (dmgTaken > 0) {
                player.takeDamage(dmgTaken);
            }

            damageTimer = 0;
        }
    }
}
