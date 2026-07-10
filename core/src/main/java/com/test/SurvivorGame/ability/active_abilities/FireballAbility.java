package com.test.SurvivorGame.ability.active_abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.SoundManager;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.ability_objects.projectile.Fireball;
import com.test.SurvivorGame.world.World;

public class FireballAbility extends ActiveAbility {

    // Eindeutige ID der Fähigkeit
    public static final String ID = "fireball";

    // Standardwerte der Feuerball-Fähigkeit
    private static final float BASE_EXPLOSION_RADIUS = 2.5f;
    private static final float BASE_DURATION = 3f;
    private static final float BASE_WIDTH = 2f;
    private static final float BASE_HEIGHT = 2f;
    private static final float BASE_SPEED = 5f;
    private static final float BASE_DAMAGE = 2f;
    private static final float BASE_COOLDOWN = 3f;

    // Textur des Feuerball-Projektils
    private final Texture texture =
        new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public FireballAbility(World world, Viewport viewport) {
        // Initialisiert die Fähigkeit und ordnet sie dem Feuer-Statbereich zu.
        super(ID, world, viewport, StatScope.FIRE);
    }

    @Override
    protected void activate() {

        // Erstellt einen Feuerball mit den aktuellen Werten der Fähigkeit.
        Fireball fireball = new Fireball(
            player.getX(),
            player.getY(),
            BASE_WIDTH * getSize(),
            BASE_HEIGHT * getSize(),
            texture,
            player,
            viewport,
            world,
            BASE_SPEED,
            BASE_DURATION,
            getDamage(),
            BASE_EXPLOSION_RADIUS * getSize()
        );

        // Fügt den Feuerball der Spielwelt hinzu.
        world.addAbility(fireball);

        // Spielt den Abschuss-Sound ab.
        SoundManager.playSound("fireBall.wav",1f);
    }

    /**
     * Berechnet den aktuellen Schaden des Feuerballs.
     * Der Schaden steigt mit höheren Leveln und wird anschließend
     * durch Spieler-Attribute (Magic Damage) beeinflusst.
     */
    public float getDamage() {
        float damage = BASE_DAMAGE;

        if (getLevel() >= 2) {
            damage *= 1.15f;
        }

        if (getLevel() >= 5) {
            damage *= 1.50f;
        }

        return applyStat(damage, StatType.MAGIC_DAMAGE);
    }

    /**
     * Berechnet die Größe des Feuerballs.
     * Dadurch werden sowohl das Projektil als auch die Explosionsgröße
     * mit höheren Leveln größer.
     */
    public float getSize() {
        float size = 1f;

        if (getLevel() >= 3) {
            size *= 1.10f;
        }

        if (getLevel() >= 5) {
            size *= 1.15f;
        }

        return applyStat(size, StatType.MAGIC_SIZE);
    }

    @Override
    public float getCooldown() {
        float cooldown = BASE_COOLDOWN;

        // Ab Level 4 wird die Abklingzeit verkürzt.
        if (getLevel() >= 4) {
            cooldown *= 0.85f;
        }

        // Zusätzliche Cooldown-Reduktionen durch Spielerwerte berücksichtigen.
        float cooldownModifier =
            playerStats.getStat(getScope(), StatType.MAGIC_COOLDOWN_REDUCTION);

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
        return "Fireball Ability";
    }

    @Override
    public int getMaxAmount() {
        // Maximale Ausbaustufe der Fähigkeit.
        return 5;
    }

    /**
     * Liefert die Beschreibung der jeweiligen Ausbaustufe
     * für das Upgrade-Menü.
     */
    @Override
    public String getDescription(int level) {
        return switch (level) {
            case 1 -> "Shoots a fireball that explodes on impact";
            case 2 -> "Fireball damage increased by 15%";
            case 3 -> "Fireball size increases by 10%";
            case 4 -> "Cooldown decreased by 15%";
            case 5 -> "Size increased by 15% and damage increased by 50% [MAX LEVEL]";
            default -> "No description available";
        };
    }
}
