package com.test.SurvivorGame.ability.active_abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.SoundManager;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.world.World;

public class SmallHeal extends ActiveAbility {

    // Eindeutige ID der Fähigkeit
    public static final String ID = "small_heal";

    // Standardwerte der Heilfähigkeit
    private static final float BASE_COOLDOWN = 12f;
    private static final float BASE_HEAL = 2.5f;

    // Platzhaltertextur der Fähigkeit
    private final Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public SmallHeal(World world, Viewport viewport) {
        // Initialisiert die Fähigkeit. Sie gehört zum Stat-Bereich ALL,
        // da sie keine bestimmte Magierichtung verwendet.
        super(ID, world, viewport, StatScope.ALL);
    }

    @Override
    protected void activate() {

        // Heilt den Spieler um den aktuell berechneten Heilwert.
        playerState.heal(getHeal());

        // Spielt den Heilungssound ab.
        SoundManager.playSound("SmallHeal.wav", 1f);
    }

    /**
     * Berechnet den aktuellen Heilwert der Fähigkeit.
     * Mit höheren Leveln wird die Heilung stärker.
     */
    public float getHeal() {
        float heal = BASE_HEAL;

        if (getLevel() >= 3) {
            heal *= 1.5f;
        }

        if (getLevel() >= 4) {
            heal *= 1.33f;
        }

        return heal;
    }

    @Override
    public float getCooldown() {
        float cooldown = BASE_COOLDOWN;

        // Höhere Level verkürzen die Zeit bis zur nächsten Heilung.
        if (getLevel() >= 2) {
            cooldown *= 0.9f;
        }

        if (getLevel() == 5) {
            cooldown *= 0.5f;
        }

        // Berücksichtigt zusätzliche Cooldown-Reduktionen durch Spielerwerte.
        float cooldownModifier = applyStat(1f, StatType.MAGIC_COOLDOWN_REDUCTION);

        return cooldown / cooldownModifier;
    }

    /**
     * Gibt die verwendete Textur frei, um Speicherlecks zu vermeiden.
     */
    public void dispose() {
        texture.dispose();
    }

    @Override
    public String getName() {
        return "Small Heal";
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
            case 1 -> "Heal regenerates a small amount of HP";
            case 2 -> "Reduces the ability cooldown by 10%";
            case 3 -> "Heal increases by 50%";
            case 4 -> "Heal increased by 33%";
            case 5 -> "Cooldown decreased by 50% [MAX LEVEL]";
            default -> "No description available";
        };
    }
}
