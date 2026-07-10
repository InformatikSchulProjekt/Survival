package com.test.SurvivorGame.entity.ability_objects.projectile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.world.maps.GameMap;

public class WindBulletProjectile extends Projectile {

    // Schaden, den das Projektil verursacht
    private final float damage;

    // Anzahl der Gegner, die das Projektil noch durchdringen kann
    private int pierceLeft;

    // Speichert den zuletzt getroffenen Gegner, um Mehrfachtreffer
    // innerhalb eines kurzen Zeitraums zu verhindern.
    private Enemy lastHitEnemy;

    // Sperrzeit, bevor derselbe Gegner erneut getroffen werden kann.
    private float sameEnemyHitLock = 0f;

    // Fortschritt der Projektil-Animation
    private float animationTime = 0f;

    public WindBulletProjectile(
        float x,
        float y,
        float width,
        float height,
        Texture texture,
        Player player,
        Viewport viewport,
        float speed,
        float duration,
        float damage,
        int pierceLeft
    ) {
        super(x, y, width, height, texture, player, viewport, speed, duration);

        this.damage = damage;
        this.pierceLeft = pierceLeft;
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

        // Speichert den zuletzt getroffenen Gegner und startet die Sperrzeit.
        lastHitEnemy = enemy;
        sameEnemyHitLock = 0.25f;

        // Verringert die verbleibende Anzahl an Gegnern,
        // die das Projektil noch durchdringen kann.
        pierceLeft--;

        // Verschwindet, sobald keine Durchdringungen mehr übrig sind.
        if (pierceLeft <= 0) {
            expire();
        }
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
