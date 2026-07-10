package com.test.SurvivorGame.ability.active_abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.SoundManager;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.ability_objects.projectile.WaterArrowProjectile;
import com.test.SurvivorGame.world.World;

public class WaterArrowAbility extends ActiveAbility {

    // Eindeutige ID der Fähigkeit
    public static final String ID = "water_arrow";

    // Standardwerte der Fähigkeit
    private static final float BASE_DURATION = 3f;
    private static final float BASE_WIDTH = 3f;
    private static final float BASE_HEIGHT = 0.6f;
    private static final float BASE_SPEED = 6f;
    private static final int BASE_PIERCE = 3;
    private static final float BASE_DAMAGE = 0.75f;
    private static final float BASE_COOLDOWN = 1f;

    // Textur des Wasserpfeil-Projektils
    private final Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public WaterArrowAbility(World world, Viewport viewport) {
        // Initialisiert die Fähigkeit und ordnet sie dem Wasser-Statbereich zu.
        super(ID, world, viewport, StatScope.WATER);
    }

    @Override
    protected void activate() {

        // Erstellt einen Wasserpfeil mit den aktuellen Werten der Fähigkeit.
        WaterArrowProjectile waterArrowProjectile = new WaterArrowProjectile(
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
        world.addAbility(waterArrowProjectile);

        // Spielt den Abschuss-Sound ab.
        SoundManager.playSound("WaterArrow.wav", 1f);
    }

    /**
     * Gibt die verwendete Textur frei, um Speicherlecks zu vermeiden.
     */
    public void dispose() {
        texture.dispose();
    }

    /**
     * Berechnet den aktuellen Schaden des Wasserpfeils.
     * Der Schaden steigt mit höheren Leveln und wird anschließend
     * durch Spielerwerte (Magic Damage) angepasst.
     */
    public float getDamage() {
        float damage = BASE_DAMAGE;

        if (getLevel() >= 2) {
            damage *= 1.2f;
        }

        if (getLevel() >= 5) {
            damage *= 1.5f;
        }

        return applyStat(damage, StatType.MAGIC_DAMAGE);
    }

    /**
     * Berechnet die Größe des Projektils.
     * Ab Level 4 wird der Wasserpfeil größer.
     */
    public float getSize() {
        float size = 1f;

        if (getLevel() >= 4) {
            size *= 1.20f;
        }

        return applyStat(size, StatType.MAGIC_SIZE);
    }

    /**
     * Bestimmt, wie viele Gegner der Wasserpfeil durchdringen kann,
     * bevor er verschwindet.
     */
    public int getPierce() {
        int pierce = BASE_PIERCE;

        if (getLevel() >= 3) {
            pierce += 3;
        }

        return pierce;
    }

    @Override
    public float getCooldown() {

        // Berücksichtigt mögliche Cooldown-Reduktionen durch Spielerwerte.
        float cooldownModifier = applyStat(1f, StatType.MAGIC_COOLDOWN_REDUCTION);

        return BASE_COOLDOWN / cooldownModifier;
    }

    @Override
    public String getName() {
        return "Water Arrow";
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
            case 1 -> "Shoots a water arrow that explodes on impact";
            case 2 -> "Water arrow damage increased by 20%";
            case 3 -> "Water arrow pierce increases by 3";
            case 4 -> "Water Arrow Size increases by 20%";
            case 5 -> "Damage increased by 50% [MAX LEVEL]";
            default -> "No description available";
        };
    }
}
