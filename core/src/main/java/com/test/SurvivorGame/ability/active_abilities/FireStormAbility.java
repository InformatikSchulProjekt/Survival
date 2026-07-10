package com.test.SurvivorGame.ability.active_abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.ability_objects.FireStorm;
import com.test.SurvivorGame.world.World;

public class FireStormAbility extends ActiveAbility {

    // Eindeutige ID der Fähigkeit
    public static final String ID = "fire_storm";

    // Zeitabstand, in dem Gegner Schaden erhalten
    private static final float DAMAGE_INTERVAL = 0.1f;

    // Standardwerte der Fähigkeit
    private static final float BASE_DURATION = 1f;
    private static final float START_SIZE = 0f;
    private static final float BASE_END_SIZE = 4f;
    private static final float BASE_DAMAGE = 0.5f;
    private static final float BASE_COOLDOWN = 1f;

    // Textur für den Feuersturm
    private final Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public FireStormAbility(World world) {
        // Initialisiert die Fähigkeit und ordnet sie dem Feuer-Statbereich zu.
        super(ID, world, StatScope.FIRE);
    }

    @Override
    protected void activate() {

        // Erstellt einen Feuersturm an der aktuellen Spielerposition.
        // Der Effekt wächst von der Startgröße bis zur Endgröße an.
        FireStorm fireStorm = new FireStorm(
            player.getX(),
            player.getY(),
            START_SIZE,
            getEndSize(),
            texture,
            getDuration(),
            world,
            getDamage()
        );

        // Fügt den Feuersturm der Spielwelt hinzu.
        world.addAbility(fireStorm);
    }

    /**
     * Berechnet den aktuellen Schaden des Feuersturms.
     * Der Schaden erhöht sich abhängig vom Level und wird
     * anschließend durch Spielerwerte (Magic Damage) angepasst.
     */
    public float getDamage() {
        float damage = BASE_DAMAGE;

        if (getLevel() >= 2) {
            damage *= 1.25f;
        }

        if (getLevel() == 5) {
            damage *= 1.3f;
        }

        return applyStat(damage, StatType.MAGIC_DAMAGE);
    }

    @Override
    public float getCooldown() {
        float cooldown = BASE_COOLDOWN;

        // Ab Level 3 wird die Abklingzeit reduziert.
        if (getLevel() >= 3) {
            cooldown *= 0.9f;
        }

        // Berücksichtigt zusätzliche Cooldown-Reduktionen durch Spielerwerte.
        float cooldownModifier = applyStat(1f, StatType.MAGIC_COOLDOWN_REDUCTION);

        return cooldown / cooldownModifier;
    }

    @Override
    public float getDuration() {
        float duration = BASE_DURATION;

        // Ab Level 4 bleibt der Feuersturm länger aktiv.
        if (getLevel() >= 4) {
            duration *= 1.1f;
        }

        return applyStat(duration, StatType.MAGIC_DURATION);
    }

    /**
     * Berechnet die maximale Größe des Feuersturms.
     * Ab Level 5 wächst der Wirkungsbereich stärker an.
     */
    public float getEndSize() {
        float endSize = BASE_END_SIZE;

        if (getLevel() == 5) {
            endSize *= 1.40f;
        }

        return applyStat(endSize, StatType.MAGIC_SIZE);
    }

    /**
     * Gibt das Zeitintervall zurück, in dem der Feuersturm
     * Schaden an Gegnern verursacht.
     */
    public static float getDamageInterval() {
        return DAMAGE_INTERVAL;
    }

    /**
     * Gibt die verwendete Textur frei, um Speicherlecks zu vermeiden.
     */
    public void dispose() {
        texture.dispose();
    }

    @Override
    public String getName() {
        return "Firestorm Ability";
    }

    @Override
    public int getMaxAmount() {
        // Maximale Ausbaustufe der Fähigkeit.
        return 5;
    }

    /**
     * Liefert die Beschreibung der jeweiligen Ausbaustufe,
     * die im Upgrade-Menü angezeigt wird.
     */
    @Override
    public String getDescription(int level) {
        return switch (level) {
            case 1 -> "Emits a fire storm that deals damage in a certain interval";
            case 2 -> "Fire storm damage increased by 25%";
            case 3 -> "Cooldown decreased by 10%";
            case 4 -> "Duration increased by 10%";
            case 5 -> "End size increased by 40% and damage increased by 30% [MAX LEVEL]";
            default -> "No description available";
        };
    }
}
