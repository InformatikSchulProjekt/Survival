package com.test.SurvivorGame.entity.abilityObjects.projectile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.world.World;
import com.test.SurvivorGame.world.maps.GameMap;

import java.util.List;

public class Fireball extends Projectile {

    private final World world;
    private final float explosionRadius;
    private final float explosionDamage;
    private final float totalLifetime;

    private float animationTime = 0f;
    private float lifetimeCounter = 0f;
    private boolean hasExploded = false;
    private boolean expired = false;

    public Fireball(
        float x,
        float y,
        float width,
        float height,
        Texture texture,
        Player player,
        Viewport viewport,
        World world,
        float speed,
        float duration,
        float damage,
        float explosionRadius
    ) {
        super(x, y, width, height, texture, player, viewport, speed, duration);
        this.world = world;
        this.explosionRadius = explosionRadius;
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

        if (!hasExploded) {
            super.update(deltaTime, map);
        }
    }

    @Override
    public void onHit(Enemy enemy) {
        if (hasExploded) {
            return;
        }
        explode(enemy);
    }

    private void explode(Enemy initialEnemy) {
        hasExploded = true;
        animationTime = 0f;

        List<Enemy> allEnemies = world.getEnemies();

        boolean hitAny = false;
        for (Enemy enemy : allEnemies) {
            float dx = enemy.getX() - getX();
            float dy = enemy.getY() - getY();
            float distance = (float) Math.sqrt(dx * dx + dy * dy);

            if (distance <= explosionRadius) {
                damageEnemy(enemy, explosionDamage);
                hitAny = true;
            }
        }

        // Fallback, falls aus irgendeinem Grund kein Gegner im Radius war
        if (!hitAny && initialEnemy != null) {
            damageEnemy(initialEnemy, explosionDamage);
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
