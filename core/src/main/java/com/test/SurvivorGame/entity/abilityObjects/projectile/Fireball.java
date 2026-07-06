package com.test.SurvivorGame.entity.abilityObjects.projectile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.world.maps.GameMap;

public class Fireball extends Projectile {

    private float explosionRadius;
    private float explosionDamage;
    private float animationTime = 0f;
    private boolean hasExploded = false;
    private float lifetimeCounter = 0f;
    private float totalLifetime = 3f;
    private boolean expired = false;

    public Fireball(
        float x,
        float y,
        float effectSize,
        float height,
        Texture texture,
        Player player,
        Viewport viewport,
        float speed,
        float duration,
        float damage
    ) {
        super(x, y, effectSize, texture, player, viewport, speed, duration);
        this.explosionRadius = effectSize;
        this.explosionDamage = damage;
        this.totalLifetime = duration;
    }

    @Override
    public void update(float deltaTime, GameMap map) {
        animationTime += deltaTime;
        lifetimeCounter += deltaTime;

        if (lifetimeCounter >= totalLifetime) {
            expired = true;
            return;
        }

        if (hasExploded) {
            // Explosion animation is playing, just waiting for lifetime to expire
        } else {
            super.update(deltaTime, map);
        }
    }

    @Override
    public void onHit(Enemy enemy) {
        if (!hasExploded) {
            hasExploded = true;
            animationTime = 0f;
            damageEnemy(enemy, explosionDamage);
        }
    }

    @Override
    public boolean getExpired() {
        return expired;
    }

    public float getAnimationTime() {
        return animationTime;
    }

    public boolean hasExploded() {
        return hasExploded;
    }

    public float getExplosionRadius() {
        return explosionRadius;
    }
}
