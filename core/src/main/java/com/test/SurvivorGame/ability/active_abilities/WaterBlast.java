package com.test.SurvivorGame.ability.active_abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.SoundManager;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.ability_objects.projectile.WaterBlastProjectile;
import com.test.SurvivorGame.world.World;

public class WaterBlast extends ActiveAbility {

    // Eindeutige ID der Fähigkeit
    public static final String ID = "water_blast";

    // Basiswerte der Fähigkeit
    private static final float BASE_DURATION = 3f;
    private static final float BASE_WIDTH = 2f;
    private static final float BASE_HEIGHT = 1f;
    private static final float BASE_SPEED = 5f;
    private static final float BASE_COOLDOWN = 1f;
    private static final float BASE_DAMAGE = 1f;

    // Platzhaltertextur für das Projektil
    private final Texture texture =
        new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public WaterBlast(World world, Viewport viewport) {
        super(ID, world, viewport, StatScope.WATER);
    }

    @Override
    protected void activate() {

        // Erzeugt das Wasserprojektil an der Position des Spielers.
        // Größe und Schaden werden anhand des Levels und der Spielerwerte berechnet.
        WaterBlastProjectile waterBlastProjectile = new WaterBlastProjectile(
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
        world.addAbility(waterBlastProjectile);

        // Spielt den Soundeffekt der Fähigkeit ab.
        SoundManager.playSound("WaterBlast.wav", 1f);
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

        if (getLevel() == 5) {
            damage *= 1.5f;
        }

        return applyStat(damage, StatType.MAGIC_DAMAGE);
    }

    /**
     * Berechnet die Größe des Projektils.
     * Der Basiswert ist bewusst etwas kleiner gewählt.
     */
    public float getSize() {
        return applyStat(0.75f, StatType.MAGIC_SIZE);
    }

    @Override
    public float getCooldown() {
        float cooldown = BASE_COOLDOWN;

        // Je nach Level wird die Abklingzeit weiter reduziert.
        if (getLevel() >= 3) {
            cooldown *= 0.9f;
        }

        if (getLevel() >= 4) {
            cooldown *= 0.8f;
        }

        if (getLevel() >= 5) {
            cooldown *= 0.9f;
        }

        // Berücksichtigt zusätzliche Cooldown-Boni des Spielers.
        float cooldownModifier =
            applyStat(1f, StatType.MAGIC_COOLDOWN_REDUCTION);

        return cooldown / cooldownModifier;
    }

    @Override
    public String getName() {
        return "Water Blast";
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
            case 1 -> "Shoots a water blast that explodes on impact";
            case 2 -> "Water blast damage increased by 10%";
            case 3 -> "Water blast cooldown decreased by 10%";
            case 4 -> "Cooldown reduced by 20%";
            case 5 -> "Damage increased by 50% and decreases Cooldown by 10% [MAX LEVEL]";
            default -> "No description available";
        };
    }
}
