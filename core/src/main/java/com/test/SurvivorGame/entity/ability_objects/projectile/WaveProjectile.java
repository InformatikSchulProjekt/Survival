package com.test.SurvivorGame.entity.ability_objects.projectile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.world.maps.GameMap;

public class WaveProjectile extends Projectile {

    private boolean hasHitEnemy = false;
    private float postHitDistance = 0f;
    private final float postHitTravelLimit = 2.5f;

    private final float damage;
    private final float maxDistance = 10f;

    private Enemy lastHitEnemy;
    private float sameEnemyHitLock = 0f;
    private float animationTime = 0f;
    private float distanceTraveled;
    private float speed;

    public WaveProjectile(float x, float y, float width, float height, Texture texture, Player player,
                          Viewport viewport, float speed, float duration, float damage) {
        super(x, y, width, height, texture, player, viewport, speed, duration);
        this.damage = damage;
        this.speed  = speed;


    }

    @Override
    public void update(float deltaTime, GameMap map) {
        super.update(deltaTime, map);
        animationTime += deltaTime;

        if (sameEnemyHitLock > 0) {
            sameEnemyHitLock -= deltaTime;
        }
        distanceTraveled += speed * deltaTime;
        if (hasHitEnemy) {
            postHitDistance += speed * deltaTime;
            if (postHitDistance>=postHitTravelLimit) {
                expire();
                return;
            }
        }
        if (distanceTraveled >= maxDistance) {
            expire();
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

        float slowAmount = 0.6f;
        float slowDuration = 2f;
        applySlowToEnemy(enemy, slowAmount, slowDuration);

        damageEnemy(enemy, getDamage());
        lastHitEnemy = enemy;
        sameEnemyHitLock = 0.25f;

        hasHitEnemy = true;
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
