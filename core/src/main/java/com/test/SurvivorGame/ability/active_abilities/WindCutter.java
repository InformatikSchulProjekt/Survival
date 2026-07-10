package com.test.SurvivorGame.ability.active_abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.SoundManager;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.ability_objects.projectile.WindCutterProjectile;
import com.test.SurvivorGame.world.World;

public class WindCutter extends ActiveAbility {

    // Eindeutige ID der Fähigkeit
    public static final String ID = "wind_cutter";

    // Basiswerte der Fähigkeit
    private static final float BASE_DURATION = 3f;
    private static final float BASE_WIDTH = 3f;
    private static final float BASE_HEIGHT = 0.7f;
    private static final float BASE_SPEED = 6f;
    private static final float BASE_COOLDOWN = 1f;
    private static final float BASE_DAMAGE = 1f;

    // Platzhaltertextur für das Projektil
    private final Texture texture =
        new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public WindCutter(World world, Viewport viewport) {
        super(ID, world, viewport, StatScope.WIND);
    }

    @Override
    protected void activate() {

        // Erzeugt das Windprojektil an der Position des Spielers.
        // Größe und Schaden werden anhand des Levels und der Spielerwerte berechnet.
        WindCutterProjectile windCutterProjectile = new WindCutterProjectile(
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
        world.addAbility(windCutterProjectile);

        // Spielt den Soundeffekt der Fähigkeit ab.
        SoundManager.playSound("WindCutter.wav", 1f);
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
            damage *= 1.1f;
        }

        if (getLevel() >= 4) {
            damage *= 1.25f;
        }

        if (getLevel() == 5) {
            damage *= 1.60f;
        }

        return applyStat(damage, StatType.MAGIC_DAMAGE);
    }

    /**
     * Berechnet die Größe des Projektils.
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

        // Ab Level 3 wird die Abklingzeit leicht reduziert.
        if (getLevel() >= 3) {
            cooldown *= 0.95f;
        }

        // Berücksichtigt zusätzliche Cooldown-Boni des Spielers.
        float cooldownModifier =
            applyStat(1f, StatType.MAGIC_COOLDOWN_REDUCTION);

        return cooldown / cooldownModifier;
    }

    @Override
    public String getName() {
        return "Wind cutter";
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
            case 1 -> "Creates a blade of wind that cuts the enemy";
            case 2 -> "Wind cutter damage increased by 10%";
            case 3 -> "Wind cutter cooldown decreased by 5%";
            case 4 -> "Size increased by 25% and increases damage by 25%";
            case 5 -> "Size increased by 40% and damage increased by 60% [MAX LEVEL]";
            default -> "No description available";
        };
    }
}
