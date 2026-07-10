package com.test.SurvivorGame.world.system;

import com.test.SurvivorGame.entity.ability_objects.AbilityObject;
import com.test.SurvivorGame.entity.ability_objects.projectile.BossProjectile;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.entity.Player;

import java.util.List;

public class CollisionSystem {
    private final Player player;

    public CollisionSystem(Player player) {
        this.player = player;
    }

    public void checkCollisions(

        List<Enemy> enemies,
        List<AbilityObject> abilityObjects
    ) {
        checkAbilityEnemyCollisions(abilityObjects, enemies);
        checkAbilityPlayerCollisions(abilityObjects);

        checkPlayerEnemyCollisions(enemies);
    }

    private void checkAbilityEnemyCollisions(List<AbilityObject> abilities, List<Enemy> enemies) {
        for (AbilityObject ability : abilities) {
            for (Enemy enemy : enemies) {
                if (ability.damagesPlayer()) {
                    continue;
                }

                if (ability.overlaps(enemy)) {
                    ability.onHit(enemy);
                }
            }
        }
    }

    private void checkAbilityPlayerCollisions(
        List<AbilityObject> abilities) {

        for (AbilityObject ability : abilities) {

            if (!ability.damagesPlayer()) {
                continue;
            }

            if (ability.overlaps(player)) {

                ability.onPlayerHit(player);

            }
        }
    }

    private void checkPlayerEnemyCollisions( List<Enemy> enemies) {

        float totalDamage = 0f;

        for (Enemy enemy : enemies) {

            if (!player.overlaps(enemy)) {
                continue;
            }

            if (!enemy.canAttack()) {
                continue;
            }

            enemy.attack();
            System.out.println(enemy.isAttacking());
            totalDamage += enemy.getDamage();
        }

        if (totalDamage > 0f) {
            player.takeDamage(totalDamage);
        }
    }
}
