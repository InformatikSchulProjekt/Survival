package com.test.SurvivorGame.ability.active_abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.SoundManager;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.ability_objects.projectile.FireArrowProjectile;
import com.test.SurvivorGame.world.World;

public class FireArrowAbility extends ActiveAbility {

    // Eindeutige ID der Fähigkeit
    public static final String ID = "fire_arrow";

    // Standardwerte der Fähigkeit
    private static final float BASE_DURATION = 3f;
    private static final float BASE_WIDTH = 3f;
    private static final float BASE_HEIGHT = 0.6f;
    private static final float BASE_SPEED = 6f;
    private static final int BASE_PIERCE = 3;
    private static final float BASE_DAMAGE = 0.75f;
    private static final float BASE_COOLDOWN = 1f;

    // Textur, die für das Projektil verwendet wird
    private final Texture texture =
        new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public FireArrowAbility(World world, Viewport viewport) {
        // Übergibt die wichtigsten Informationen an die Oberklasse
        super(ID, world, viewport, StatScope.FIRE);
    }

    @Override
    protected void activate() {

        // Erzeugt ein neues Feuerpfeil-Projektil mit den aktuellen Werten
        FireArrowProjectile fireArrowProjectile = new FireArrowProjectile(
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

        // Fügt das Projektil der Spielwelt hinzu
        world.addAbility(fireArrowProjectile);

        // Spielt beim Abschuss einen Soundeffekt ab
        SoundManager.playSound("fireArrow.wav", 1f);
    }

    /**
     * Berechnet den aktuellen Schaden der Fähigkeit.
     * Der Schaden steigt abhängig vom Level und wird anschließend
     * durch mögliche Spieler-Boni (Magic Damage) angepasst.
     */
    public float getDamage() {
        float damage = BASE_DAMAGE;

        if (getLevel() >= 2) {
            damage *= 1.15f;
        }

        if (getLevel() >= 5) {
            damage *= 1.5f;
        }

        return applyStat(damage, StatType.MAGIC_DAMAGE);
    }

    /**
     * Berechnet die aktuelle Größe des Projektils.
     * Ab Level 5 wird der Feuerpfeil größer.
     */
    public float getSize() {
        float size = 1f;

        if (getLevel() >= 5) {
            size *= 1.1f;
        }

        return applyStat(size, StatType.MAGIC_SIZE);
    }

    /**
     * Bestimmt, wie viele Gegner der Pfeil durchdringen kann,
     * bevor er verschwindet.
     */
    public int getPierce() {
        int pierce = BASE_PIERCE;

        if (getLevel() >= 3) {
            pierce += 2;
        }

        if (getLevel() >= 5) {
            pierce += 2;
        }

        return pierce;
    }

    @Override
    public float getCooldown() {
        float cooldown = BASE_COOLDOWN;

        // Ab Level 4 wird die Abklingzeit reduziert.
        if (getLevel() >= 4) {
            cooldown *= 0.85f;
        }

        // Berücksichtigt zusätzliche Cooldown-Boni des Spielers.
        float cooldownModifier =
            playerStats.getStat(getScope(), StatType.MAGIC_COOLDOWN_REDUCTION);

        return cooldown / cooldownModifier;
    }

    /**
     * Gibt den verwendeten Grafikspeicher frei.
     * Sollte beim Beenden des Spiels bzw. beim Entfernen der Fähigkeit
     * aufgerufen werden.
     */
    public void dispose() {
        texture.dispose();
    }

    @Override
    public String getName() {
        return "Fire Arrow";
    }

    @Override
    public int getMaxAmount() {
        // Maximale Ausbaustufe der Fähigkeit
        return 5;
    }

    /**
     * Liefert die Beschreibung der jeweiligen Ausbaustufe,
     * die im Upgrade-Menü angezeigt wird.
     */
    @Override
    public String getDescription(int level) {
        return switch (level) {
            case 1 -> "Shoots a fire arrow that explodes on impact";
            case 2 -> "Fire arrow damage increased by 15%";
            case 3 -> "Fire arrow pierce increases by 2";
            case 4 -> "Cooldown decreased by 15%";
            case 5 -> "Size increased by 10%, damage increased by 50% and +2 Pierce [MAX LEVEL]";
            default -> "No description available";
        };
    }
}
