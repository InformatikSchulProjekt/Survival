package com.test.SurvivorGame.entity.ability_objects.projectile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.world.World;
import com.test.SurvivorGame.world.maps.GameMap;

import java.util.List;

public class Fireball extends Projectile {

    // Referenz auf die Spielwelt, um alle Gegner für die Explosion zu ermitteln.
    private final World world;

    // Eigenschaften der Explosion
    private final float explosionRadius;
    private final float explosionDamage;
    private final float totalLifetime;

    // Direkter Schaden des Feuerballs
    private final float damage;

    // Speichert den Fortschritt der Animation und Lebensdauer
    private float animationTime = 0f;
    private float lifetimeCounter = 0f;

    // Zustände des Projektils
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
        this.damage = damage;
    }

    @Override
    public void update(float deltaTime, GameMap map) {

        // Aktualisiert Animation und verbleibende Lebensdauer.
        animationTime += deltaTime;
        lifetimeCounter += deltaTime;

        // Entfernt den Feuerball nach Ablauf seiner Lebensdauer.
        if (lifetimeCounter >= totalLifetime) {
            expired = true;
            return;
        }

        // Solange der Feuerball noch nicht explodiert ist,
        // bewegt er sich wie ein normales Projektil.
        if (!hasExploded) {
            super.update(deltaTime, map);
        }
    }

    @Override
    public void onHit(Enemy enemy) {

        // Eine Explosion darf nur einmal ausgelöst werden.
        if (hasExploded) {
            return;
        }

        explode(enemy);
    }

    /**
     * Löst die Explosion aus und fügt allen Gegnern innerhalb
     * des Explosionsradius Schaden zu.
     */
    private void explode(Enemy initialEnemy) {

        hasExploded = true;
        animationTime = 0f;

        List<Enemy> allEnemies = world.getEnemies();

        boolean hitAny = false;

        // Prüft für jeden Gegner den Abstand zum Explosionszentrum.
        for (Enemy enemy : allEnemies) {

            float dx = enemy.getX() - getX();
            float dy = enemy.getY() - getY();
            float distance = (float) Math.sqrt(dx * dx + dy * dy);

            // Nur Gegner innerhalb des Radius erhalten Schaden.
            if (distance <= explosionRadius) {
                damageEnemy(enemy, explosionDamage);
                hitAny = true;
            }
        }

        // Falls sich ausnahmsweise kein Gegner im Radius befindet,
        // erhält zumindest der ursprünglich getroffene Gegner Schaden.
        if (!hitAny && initialEnemy != null) {
            damageEnemy(initialEnemy, explosionDamage);
        }
    }

    @Override
    public boolean getExpired() {
        return expired;
    }

    /**
     * Gibt die aktuelle Animationszeit zurück.
     * Diese wird z. B. für die Darstellung der Explosion verwendet.
     */
    public float getAnimationTime() {
        return animationTime;
    }

    /**
     * Gibt zurück, ob der Feuerball bereits explodiert ist.
     */
    public boolean hasExploded() {
        return hasExploded;
    }

    @Override
    public float getDamage() {
        return damage;
    }
}
