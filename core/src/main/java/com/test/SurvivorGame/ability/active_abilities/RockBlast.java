package com.test.SurvivorGame.ability.active_abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.SoundManager;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.ability_objects.projectile.RockBlastProjectile;
import com.test.SurvivorGame.world.World;

public class RockBlast extends ActiveAbility {

    // Eindeutige ID der Fähigkeit
    public static final String ID = "rock_blast";

    // Standardwerte der Fähigkeit
    private static final float BASE_DURATION = 3f;
    private static final float BASE_WIDTH = 2f;
    private static final float BASE_HEIGHT = 1f;
    private static final float BASE_SPEED = 3f;
    private static final float BASE_COOLDOWN = 1f;
    private static final float BASE_DAMAGE = 1.5f;

    // Textur des Steinprojektils
    private final Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public RockBlast(World world, Viewport viewport) {
        // Initialisiert die Fähigkeit und ordnet sie dem Erd-Statbereich zu.
        super(ID, world, viewport, StatScope.EARTH);
    }

    @Override
    protected void activate() {

        // Erstellt ein neues Steinprojektil mit den aktuellen Werten der Fähigkeit.
        RockBlastProjectile rockBlastProjectile = new RockBlastProjectile(
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
        world.addAbility(rockBlastProjectile);

        // Spielt beim Abschuss einen Soundeffekt ab.
        SoundManager.playSound("rockBlast.wav",1f);
    }

    /**
     * Berechnet den aktuellen Schaden der Fähigkeit.
     * Der Schaden steigt mit höheren Leveln und wird anschließend
     * durch Spielerwerte (Magic Damage) angepasst.
     */
    public float getDamage() {
        float damage = BASE_DAMAGE;

        if (getLevel() >= 2) {
            damage *= 1.1f;
        }

        if (getLevel() == 5) {
            damage *= 1.75f;
        }

        return applyStat(damage, StatType.MAGIC_DAMAGE);
    }

    @Override
    public float getCooldown() {
        float cooldown = BASE_COOLDOWN;

        // Mit höheren Leveln kann die Fähigkeit häufiger eingesetzt werden.
        if (getLevel() >= 3) {
            cooldown *= 0.9f;
        }

        if (getLevel() >= 4) {
            cooldown *= 0.85f;
        }

        // Berücksichtigt zusätzliche Cooldown-Reduktionen durch Spielerwerte.
        float cooldownModifier = applyStat(1f, StatType.MAGIC_COOLDOWN_REDUCTION);

        return cooldown / cooldownModifier;
    }

    /**
     * Berechnet die Größe des Projektils.
     * Die Standardgröße bleibt unverändert und kann nur durch Spielerboni
     * (Magic Size) erhöht werden.
     */
    public float getSize() {
        return applyStat(1f, StatType.MAGIC_SIZE);
    }

    /**
     * Gibt die verwendete Textur frei, um Speicherlecks zu vermeiden.
     */
    public void dispose() {
        texture.dispose();
    }

    @Override
    public String getName() {
        return "Rock blast";
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
            case 1 -> "Shoots a rock blast that explodes on impact";
            case 2 -> "Rock blast damage increased by 10%";
            case 3 -> "Rock blast cooldown decreased by 10%";
            case 4 -> "Cooldown reduced by 15%";
            case 5 -> "Damage increased by 75% [MAX LEVEL]";
            default -> "No description available";
        };
    }
}
