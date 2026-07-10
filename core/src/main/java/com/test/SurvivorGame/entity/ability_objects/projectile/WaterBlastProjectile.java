package com.test.SurvivorGame.entity.ability_objects.projectile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.world.maps.GameMap;

public class WaterBlastProjectile extends Projectile {

    private final float damage;


    private Enemy lastHitEnemy;
    private float sameEnemyHitLock = 0f;
    private float animationTime = 0f;


    public WaterBlastProjectile(float x, float y, float width, float height, Texture texture, Player player,
                                Viewport viewport, float speed, float duration, float damage) {
        super(x, y, width, height, texture, player, viewport, speed, duration);
        this.damage = damage;

    }

    @Override
    public void update(float deltaTime, GameMap map) {
        super.update(deltaTime, map);
        animationTime += deltaTime;

        if (sameEnemyHitLock > 0) {
            sameEnemyHitLock -= deltaTime;
        }

    }

    @Override
    public void onHit(Enemy enemy) {
        if (getExpired()) {
            return;
        }

        if (enemy == lastHitEnemy && sameEnemyHitLock > 0) {
            return;
        }

        damageEnemy(enemy, getDamage());

        float slowAmount = 0.6f;
        float slowDuration = 0.5f;
        applySlowToEnemy(enemy, slowAmount, slowDuration);

        lastHitEnemy = enemy;
        sameEnemyHitLock = 0.25f;
        expire();

    }
    private void applySlowToEnemy(Enemy enemy, float speedMultiplier, float duration) {
        enemy.applySlow(speedMultiplier, 2f);

    }

    @Override
    public float getDamage() {
        return damage;
    }
    public float getAnimationTime() {
        return animationTime;
    }
}
