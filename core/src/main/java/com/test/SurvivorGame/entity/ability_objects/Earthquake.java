package com.test.SurvivorGame.entity.ability_objects;

import com.badlogic.gdx.graphics.Texture;
import com.test.SurvivorGame.ability.active_abilities.EarthQuake;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.world.World;
import com.test.SurvivorGame.world.maps.GameMap;

import java.util.HashMap;

public class Earthquake extends AbilityObject {

    // Verbleibende Lebensdauer des Erdbebens
    private float deltaDuration;

    // Aktuelle Größe des Erdbebens
    float size;

    // Fortschritt der Animation
    private float animationTime = 0f;

    // Referenz auf den Spieler, damit das Erdbeben dessen Position folgt
    private Player player;

    // Schaden, den das Erdbeben verursacht
    private final float damage;

    // Speichert für jeden Gegner den Zeitpunkt des letzten Schadens,
    // damit Schaden nur in festen Intervallen verursacht wird.
    private HashMap<Enemy, Float> damageTimers = new HashMap<>();

    public Earthquake(float x, float y, float size, Texture texture, float duration, World world, float damage) {
        super(x, y, size, size, texture);

        this.size = size;
        this.deltaDuration = duration;
        this.player = world.getPlayer();
        this.damage = damage;
    }

    @Override
    public boolean getExpired() {
        // Das Erdbeben verschwindet nach Ablauf seiner Dauer.
        return deltaDuration <= 0;
    }

    @Override
    public float getDamage() {
        return damage;
    }

    @Override
    public void onHit(Enemy enemy) {

        // Ermittelt, wann der Gegner zuletzt Schaden erhalten hat.
        float timer = damageTimers.getOrDefault(enemy, EarthQuake.getDamageInterval());

        // Verhindert, dass derselbe Gegner in jedem Frame Schaden erhält.
        if (timer < EarthQuake.getDamageInterval()) {
            return;
        }

        enemy.takeDamage(getDamage());

        // Setzt den Timer für diesen Gegner zurück.
        damageTimers.put(enemy, 0f);
    }

    @Override
    public void update(float deltaTime, GameMap map) {

        // Verringert die verbleibende Lebensdauer.
        deltaDuration -= deltaTime;

        // Aktualisiert die Animationszeit.
        animationTime += deltaTime;

        // Das Erdbeben bleibt stets auf der Position des Spielers.
        if (player != null && player.getCenter() != null && collider != null) {
            collider.setCenter(player.getCenter());
        }

        // Erhöht die Schadens-Timer aller Gegner.
        damageTimers.replaceAll((enemy, t) -> t + deltaTime);
    }

    /**
     * Gibt den aktuellen Stand der Animation zurück.
     */
    public float getAnimationTime() {
        return animationTime;
    }

    /**
     * Passt die Größe des Kolliders an.
     * Dadurch verändert sich der Wirkungsbereich des Erdbebens.
     */
    public void setScale(float size) {
        collider.setWidth(size);
        collider.setHeight(size);
    }
}
