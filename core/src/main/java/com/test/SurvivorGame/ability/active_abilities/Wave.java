package com.test.SurvivorGame.ability.active_abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.SoundManager;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.ability_objects.projectile.WaveProjectile;
import com.test.SurvivorGame.world.World;

public class Wave extends ActiveAbility {

    // Eindeutige ID der Fähigkeit
    public static final String ID = "wave";

    // Basiswerte der Fähigkeit
    private static final float BASE_DURATION = 3f;
    private static final float BASE_WIDTH = 0.25f;
    private static final float BASE_HEIGHT = 2.5f;
    private static final float BASE_SPEED = 5f;
    private static final float BASE_COOLDOWN = 4.5f;
    private static final float BASE_DAMAGE = 0.5f;

    // Platzhaltertextur für das Projektil
    private final Texture texture =
        new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public Wave(World world, Viewport viewport) {
        super(ID, world, viewport, StatScope.WATER);
    }

    @Override
    protected void activate() {

        // Erzeugt das Wellen-Projektil an der Position des Spielers.
        // Größe und Schaden werden anhand des Levels und der Spielerwerte berechnet.
        WaveProjectile waveProjectile = new WaveProjectile(
            player.getX(),
            player.getY(),
            BASE_WIDTH * getSize(),
            BASE_HEIGHT * getSize(),
            texture,
            player,
            viewport,
            BASE_SPEED,
            BASE_DURATION,
            getDamage()
        );

        // Fügt das Projektil der Spielwelt hinzu.
        world.addAbility(waveProjectile);

        // Spielt den Soundeffekt der Fähigkeit ab.
        SoundManager.playSound("wave.wav", 0.15f);
    }

    /**
     * Gibt den belegten Grafikspeicher der Textur frei.
     */
    public void dispose() {
        texture.dispose();
    }

    /**
     * Berechnet den Schaden abhängig vom aktuellen Level
     * und den Magie-Schadensboni des Spielers.
     */
    public float getDamage() {
        float damage = BASE_DAMAGE;

        if (getLevel() >= 2) {
            damage *= 1.25f;
        }

        if (getLevel() == 5) {
            damage *= 1.25f;
        }

        return applyStat(damage, StatType.MAGIC_DAMAGE);
    }

    /**
     * Berechnet die Größe der Welle.
     */
    public float getSize() {
        float size = 1f;

        if (getLevel() >= 4) {
            size *= 1.25f;
        }

        if (getLevel() == 5) {
            size *= 1.4f;
        }

        return applyStat(size, StatType.MAGIC_SIZE);
    }

    @Override
    public float getCooldown() {
        float cooldown = BASE_COOLDOWN;

        // Ab Level 3 wird die Abklingzeit reduziert.
        if (getLevel() >= 3) {
            cooldown *= 0.9f;
        }

        // Berücksichtigt zusätzliche Cooldown-Boni des Spielers.
        float cooldownModifier =
            applyStat(1f, StatType.MAGIC_COOLDOWN_REDUCTION);

        return cooldown / cooldownModifier;
    }

    @Override
    public String getName() {
        return "Wave";
    }

    @Override
    public int getMaxAmount() {
        return 5;
    }

    /**
     * Beschreibung der Upgrades für jedes Level.
     */
    @Override
    public String getDescription(int level) {
        return switch (level) {
            case 1 -> "Summon a wave of water that deals damage";
            case 2 -> "Wave damage increased by 25%";
            case 3 -> "Wave cooldown decreased by 10%";
            case 4 -> "Size increased by 25%";
            case 5 -> "Size increased by 40% and damage increased by 25% [MAX LEVEL]";
            default -> "No description available";
        };
    }
}
