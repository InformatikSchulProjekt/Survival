package com.test.SurvivorGame.ability.active_abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.SoundManager;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.ability_objects.projectile.WindBulletProjectile;
import com.test.SurvivorGame.world.World;

public class WindBullet extends ActiveAbility {

    // Eindeutige ID der Fähigkeit
    public static final String ID = "wind_bullet";

    // Basiswerte der Fähigkeit
    private static final float BASE_DURATION = 3f;
    private static final float BASE_WIDTH = 0.7f;
    private static final float BASE_HEIGHT = 0.3f;
    private static final float BASE_SPEED = 7.5f;
    private static final float BASE_COOLDOWN = 1f;
    private static final int BASE_PIERCE = 2;
    private static final float BASE_DAMAGE = 0.75f;

    // Platzhaltertextur für das Projektil
    private final Texture texture =
        new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public WindBullet(World world, Viewport viewport) {
        super(ID, world, viewport, StatScope.WIND);
    }

    @Override
    protected void activate() {

        // Erzeugt das Windprojektil an der Position des Spielers.
        // Größe, Schaden und Durchdringung werden anhand des Levels
        // und der Spielerwerte berechnet.
        WindBulletProjectile windBulletProjectile = new WindBulletProjectile(
            player.getX(),
            player.getY(),
            BASE_WIDTH * getSize(),
            BASE_HEIGHT * getSize(),
            texture,
            player,
            viewport,
            BASE_SPEED,
            BASE_DURATION,
            getDamage(),
            getPierce()
        );

        // Fügt das Projektil der Spielwelt hinzu.
        world.addAbility(windBulletProjectile);

        // Spielt den Soundeffekt der Fähigkeit ab.
        SoundManager.playSound("WindBullet.wav", 1f);
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
            damage *= 1.333f;
        }

        if (getLevel() == 5) {
            damage *= 1.75f;
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

    /**
     * Berechnet, wie viele Gegner das Projektil
     * durchdringen kann.
     */
    public int getPierce() {
        int pierce = BASE_PIERCE;

        if (getLevel() >= 3) {
            pierce += 1;
        }

        return pierce;
    }

    @Override
    public float getCooldown() {
        float cooldown = BASE_COOLDOWN;

        // Ab Level 4 wird die Abklingzeit reduziert.
        if (getLevel() >= 4) {
            cooldown *= 0.8f;
        }

        // Berücksichtigt zusätzliche Cooldown-Boni des Spielers.
        float cooldownModifier =
            applyStat(1f, StatType.MAGIC_COOLDOWN_REDUCTION);

        return cooldown / cooldownModifier;
    }

    @Override
    public String getName() {
        return "Wind bullet";
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
            case 1 -> "Creates a bullet of wind that pierces the enemy";
            case 2 -> "Wind Bullet damage increased by 33%";
            case 3 -> "Pierce increased by 1";
            case 4 -> "Size increased by 25% and cooldown reduced by 20%";
            case 5 -> "Damage increased by 75% [MAX LEVEL]";
            default -> "No description available";
        };
    }
}
