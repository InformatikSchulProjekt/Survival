package com.test.SurvivorGame.ability.active_abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.test.SurvivorGame.core.SoundManager;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.ability_objects.Earthquake;
import com.test.SurvivorGame.world.World;

public class EarthQuake extends ActiveAbility {

    // Eindeutige ID der Fähigkeit
    public static final String ID = "earth_quake";

    // Zeit zwischen zwei Schadensereignissen
    private static final float DAMAGE_INTERVAL = 1f;

    // Basiswerte der Fähigkeit
    private static final float BASE_DURATION = 4f;
    private static final float BASE_SIZE = 4f;
    private static final float BASE_DAMAGE = 0.5f;
    private static final float BASE_COOLDOWN = 2f;

    // Platzhaltertextur für das Ability-Objekt
    private final Texture texture =
        new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public EarthQuake(World world) {
        super(ID, world, StatScope.EARTH);
    }

    @Override
    protected void activate() {

        // Erzeugt ein Earthquake-Objekt an der Position des Spielers.
        // Größe, Dauer und Schaden werden anhand des aktuellen Levels
        // und der Spielerwerte berechnet.
        Earthquake earthquake = new Earthquake(
            player.getX(),
            player.getY(),
            getSize(),
            texture,
            getDuration(),
            world,
            getDamage()
        );

        // Fügt das Earthquake der Spielwelt hinzu.
        world.addAbility(earthquake);

        // Spielt den Soundeffekt der Fähigkeit ab.
        SoundManager.playSound("EarthQuake.wav", 1f);
    }

    /**
     * Berechnet den Schaden abhängig vom Level
     * und den Magie-Schadensboni des Spielers.
     */
    public float getDamage() {
        float damage = BASE_DAMAGE;

        if (getLevel() >= 2) {
            damage *= 1.25f;
        }

        if (getLevel() == 5) {
            damage *= 1.5f;
        }

        return applyStat(damage, StatType.MAGIC_DAMAGE);
    }

    @Override
    public float getCooldown() {
        float cooldown = BASE_COOLDOWN;

        // Ab Level 3 wird die Abklingzeit verkürzt.
        if (getLevel() >= 3) {
            cooldown *= 0.85f;
        }

        // Berücksichtigt zusätzliche Cooldown-Boni des Spielers.
        float cooldownModifier =
            applyStat(1f, StatType.MAGIC_COOLDOWN_REDUCTION);

        return cooldown / cooldownModifier;
    }

    @Override
    public float getDuration() {
        float duration = BASE_DURATION;

        // Ab Level 4 hält das Erdbeben länger an.
        if (getLevel() >= 4) {
            duration *= 1.10f;
        }

        return applyStat(duration, StatType.MAGIC_DURATION);
    }

    /**
     * Berechnet die Größe des Erdbebens.
     */
    public float getSize() {
        float size = BASE_SIZE;

        // Auf Maximallevel wird der Wirkungsbereich vergrößert.
        if (getLevel() == 5) {
            size *= 1.50f;
        }

        return applyStat(size, StatType.MAGIC_SIZE);
    }

    /**
     * Gibt das Zeitintervall zurück,
     * in dem Gegner Schaden erhalten können.
     */
    public static float getDamageInterval() {
        return DAMAGE_INTERVAL;
    }

    /**
     * Gibt den belegten Grafikspeicher wieder frei.
     */
    public void dispose() {
        texture.dispose();
    }

    @Override
    public String getName() {
        return "Earthquake Ability";
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
            case 1 -> "Lets the earth rupture and causes cracks in the ground";
            case 2 -> "Earthquake damage increased by 25%";
            case 3 -> "Cooldown decreased by 15%";
            case 4 -> "Duration increased by 10%";
            case 5 -> "Size increased by 50% and damage increased by 50% [MAX LEVEL]";
            default -> "No description available";
        };
    }
}
