package com.test.SurvivorGame.entity.abilityObjects.projectile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.world.maps.GameMap;

public class Fireball extends Projectile {

    private float explosionRadius;
    private float explosionDamage;
    private float animationTime = 0f;
    private boolean hasExploded = false;
    private float explosionDuration = 0.6f;
    private float timeSinceExplosion = 0f;
    private boolean expired = false;

    public Fireball(float x, float y, float effectSize, Texture texture, Player player,
                    Viewport viewport, float speed, float duration, float explosionRadius) {
        super(x, y, effectSize, texture, player, viewport, speed, duration);
        this.explosionRadius = explosionRadius;
        this.explosionDamage = getDamage() * 1.5f;
    }

    @Override
    public void update(float deltaTime, GameMap map) {
        animationTime += deltaTime;
        
        if (hasExploded) {
            timeSinceExplosion += deltaTime;
            if (timeSinceExplosion >= explosionDuration) {
                expired = true;
            }
        } else {
            super.update(deltaTime, map);
        }
    }

    @Override
    public void onHit(Enemy enemy) {
        explode(enemy);
    }

    private void explode(Enemy hitEnemy) {
        if (hasExploded) return;
        
        hasExploded = true;
        animationTime = 0f;
        hitEnemy.takeDamage(explosionDamage);

        // Optional: Schaden an anderen Gegnern in Radius infügen
        // Hier würde die Logik für weitere Gegner im Explosionsradius gehen
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
