package com.test.SurvivorGame.entity.ability_objects.projectile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.world.maps.GameMap;

import java.util.ArrayList;

public class WaveProjectile extends Projectile {

    // Strecke, die das Projektil nach einem Treffer noch zurücklegen könnte
    private float postHitDistance = 0f;

    // Maximale Strecke nach einem Treffer
    private final float postHitTravelLimit = 2.5f;

    // Schaden, den die Welle verursacht
    private final float damage;

    // Maximale Flugdistanz des Projektils
    private final float maxDistance = 10f;

    // Sperrzeit für Mehrfachtreffer
    private float sameEnemyHitLock = 0f;

    // Fortschritt der Animation
    private float animationTime = 0f;

    // Bereits zurückgelegte Strecke
    private float distanceTraveled;

    // Bewegungsgeschwindigkeit des Projektils
    private float speed;

    // Speichert alle bereits getroffenen Gegner,
    // damit jeder Gegner nur einmal Schaden erhält.
    private ArrayList<Enemy> enemiesHit = new ArrayList<>();

    public WaveProjectile(
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
        this.speed = speed;
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

        // Berechnet die bisher zurückgelegte Strecke.
        distanceTraveled += speed * deltaTime;

        /*
         * Alternative Logik:
         * Nach dem ersten Treffer könnte das Projektil noch eine
         * bestimmte Strecke weiterfliegen und anschließend verschwinden.
         * Diese Funktion ist momentan deaktiviert.
         */
//        if (hasHitEnemy) {
//            postHitDistance += speed * deltaTime;
//            if (postHitDistance >= postHitTravelLimit) {
//                expire();
//                return;
//            }
//        }

        // Entfernt das Projektil nach Erreichen der maximalen Flugdistanz.
        if (distanceTraveled >= maxDistance) {
            expire();
        }
    }

    @Override
    public void onHit(Enemy enemy) {

        // Bereits abgelaufene Projektile verursachen keinen Schaden mehr.
        if (getExpired()) {
            return;
        }

        // Jeder Gegner kann nur einmal von derselben Welle getroffen werden.
        if (enemiesHit.contains(enemy)) {
            return;
        }

        // Verlangsamt den Gegner.
        float slowAmount = 0.6f;
        float slowDuration = 2f;
        applySlowToEnemy(enemy, slowAmount, slowDuration);

        // Fügt dem Gegner Schaden zu.
        damageEnemy(enemy, getDamage());

        // Speichert den Gegner als bereits getroffen.
        enemiesHit.add(enemy);
    }

    /**
     * Wendet einen Verlangsamungseffekt auf den Gegner an.
     *
     * @param enemy Der zu verlangsamende Gegner.
     * @param speedMultiplier Multiplikator für die Bewegungsgeschwindigkeit.
     * @param duration Dauer des Effekts.
     */
    private void applySlowToEnemy(Enemy enemy, float speedMultiplier, float duration) {

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
