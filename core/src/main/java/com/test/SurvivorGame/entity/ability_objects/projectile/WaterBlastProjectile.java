package com.test.SurvivorGame.entity.ability_objects.projectile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.world.maps.GameMap;

public class WaterBlastProjectile extends Projectile {

    // Schaden, den das Wasserprojektil verursacht
    private final float damage;

    // Speichert den zuletzt getroffenen Gegner, um Mehrfachtreffer
    // innerhalb eines kurzen Zeitraums zu verhindern.
    private Enemy lastHitEnemy;

    // Sperrzeit, bevor derselbe Gegner erneut getroffen werden kann.
    private float sameEnemyHitLock = 0f;

    // Fortschritt der Projektil-Animation
    private float animationTime = 0f;

    public WaterBlastProjectile(
        float x,
        float y,
        float width,
        float height,
        Texture texture,
        Player player,
        Viewport viewport,
        float speed,
        float duration,
        float damage
    ) {
        super(x, y, width, height, texture, player, viewport, speed, duration);

        this.damage = damage;
    }

    @Override
    public void update(float deltaTime, GameMap map) {

        // Führt die normale Bewegung des Projektils aus.
        super.update(deltaTime, map);

        // Aktualisiert die Animationszeit.
        animationTime += deltaTime;

        // Verringert die Sperrzeit für Mehrfachtreffer.
        if (sameEnemyHitLock > 0) {
            sameEnemyHitLock -= deltaTime;
        }
    }

    @Override
    public void onHit(Enemy enemy) {

        // Bereits abgelaufene Projektile verursachen keinen Schaden mehr.
        if (getExpired()) {
            return;
        }

        // Verhindert, dass derselbe Gegner innerhalb der Sperrzeit
        // mehrfach getroffen wird.
        if (enemy == lastHitEnemy && sameEnemyHitLock > 0) {
            return;
        }

        // Fügt dem Gegner Schaden zu.
        damageEnemy(enemy, getDamage());

        // Verlangsamt den getroffenen Gegner für kurze Zeit.
        float slowAmount = 0.6f;
        float slowDuration = 0.5f;
        applySlowToEnemy(enemy, slowAmount, slowDuration);

        // Speichert den zuletzt getroffenen Gegner und startet die Sperrzeit.
        lastHitEnemy = enemy;
        sameEnemyHitLock = 0.25f;

        // Das Projektil verschwindet nach dem Treffer.
        expire();
    }

    /**
     * Wendet einen Verlangsamungseffekt auf den Gegner an.
     *
     * @param enemy Der zu verlangsamende Gegner.
     * @param speedMultiplier Multiplikator für die Bewegungsgeschwindigkeit.
     * @param duration Dauer des Effekts.
     */
    private void applySlowToEnemy(Enemy enemy, float speedMultiplier, float duration) {

        // Verlangsamt den Gegner für eine bestimmte Zeit.
        enemy.applySlow(speedMultiplier, duration);
    }

    @Override
    public float getDamage() {
        return damage;
    }

    /**
     * Gibt den aktuellen Stand der Animation zurück.
     */
    public float getAnimationTime() {
        return animationTime;
    }
}
