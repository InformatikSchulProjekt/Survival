package com.test.SurvivorGame.entity.ability_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.test.SurvivorGame.ability.active_abilities.FireStormAbility;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.world.World;
import com.test.SurvivorGame.world.maps.GameMap;

import java.util.HashMap;

public class FireStorm extends AbilityObject {

    // Verbleibende Lebensdauer des Feuersturms
    private float deltaDuration;

    // Gesamtdauer des Feuersturms
    private float duration;

    // Fortschritt der Animation
    private float animationTime = 0f;

    // Verstrichene Zeit seit der Erzeugung des Feuersturms
    private float elapsedTime = 0;

    // Anfangs- und Endgröße des Feuersturms
    private float startSize, endSize;

    // Referenz auf den Spieler, damit der Feuersturm dem Spieler folgt
    private Player player;

    // Schaden, den der Feuersturm verursacht
    private final float damage;

    // Speichert für jeden Gegner den Zeitpunkt des letzten Schadens,
    // damit ein Gegner nur in festen Zeitabständen Schaden erhält.
    private HashMap<Enemy, Float> damageTimers = new HashMap<>();

    public FireStorm(
        float x,
        float y,
        float startSize,
        float endSize,
        Texture texture,
        float duration,
        World world,
        float damage
    ) {
        super(x, y, startSize, startSize, texture);

        this.startSize = startSize;
        this.endSize = endSize;
        this.deltaDuration = duration;
        this.duration = duration;

        this.player = world.getPlayer();
        this.damage = damage;
    }

    @Override
    public boolean getExpired() {
        // Der Feuersturm verschwindet nach Ablauf seiner Lebensdauer.
        return deltaDuration <= 0;
    }

    @Override
    public float getDamage() {
        return damage;
    }

    @Override
    public void onHit(Enemy enemy) {

        // Ermittelt, wann der Gegner zuletzt Schaden erhalten hat.
        float timer = damageTimers.getOrDefault(enemy, FireStormAbility.getDamageInterval());

        // Verhindert, dass derselbe Gegner in jedem Frame Schaden erhält.
        if (timer < FireStormAbility.getDamageInterval()) {
            return;
        }

        // Fügt dem Gegner Schaden zu.
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

        // Der Feuersturm bleibt stets auf der Position des Spielers.
        collider.setCenter(player.getCenter());

        // Erhöht die Timer aller bereits getroffenen Gegner.
        damageTimers.replaceAll((enemy, timer) -> timer + deltaTime);

        // Vergrößert den Feuersturm kontinuierlich.
        grow(deltaTime);
    }

    /**
     * Vergrößert den Feuersturm gleichmäßig von der Startgröße
     * bis zur Endgröße über seine gesamte Lebensdauer.
     */
    public void grow(float deltaTime) {

        elapsedTime += deltaTime;

        // Berechnet den Fortschritt der Animation (0 bis 1).
        float t = elapsedTime / duration;
        t = Math.min(t, 1f);

        // Interpoliert die aktuelle Größe zwischen Start- und Endgröße.
        float size = MathUtils.lerp(startSize, endSize, t);

        setScale(size);
    }

    /**
     * Passt die Größe des Kolliders an und verändert dadurch
     * den Wirkungsbereich des Feuersturms.
     */
    public void setScale(float size) {
        collider.setWidth(size);
        collider.setHeight(size);
    }

    /**
     * Gibt den aktuellen Stand der Animation zurück.
     */
    public float getAnimationTime() {
        return animationTime;
    }
}
