package com.test.SurvivorGame.ability.active_abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.ability_objects.projectile.WaveProjectile;
import com.test.SurvivorGame.world.World;

public class Wave extends ActiveAbility {

    public static final String ID = "wave";

    private static final float BASE_DURATION = 3f;
    private static final float BASE_WIDTH = 0.5f;
    private static final float BASE_HEIGHT = 5f;
    private static final float BASE_SPEED = 5f;
    private static final float BASE_COOLDOWN = 1f;
    private static final float BASE_DAMAGE = 0.75f;

    private final Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public Wave(World world, Viewport viewport) {
        super(ID, world, viewport, StatScope.WATER);
    }

    @Override
    protected void activate() {
        WaveProjectile waveProjectile = new WaveProjectile(
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

        world.addAbility(waveProjectile);
    }

    public void dispose() {
        texture.dispose();
    }

    public float getDamage() {
        float damage = BASE_DAMAGE;

        if (getLevel() >= 2) {
            damage *= 1.25f;
        }

        if (getLevel() == 5) {
            damage *= 1.15f;
        }

        return applyStat(damage, StatType.MAGIC_DAMAGE);
    }

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

        if (getLevel() >= 3) {
            cooldown *= 0.9f;
        }

        float cooldownModifier = applyStat(1f, StatType.MAGIC_COOLDOWN_REDUCTION);

        return cooldown / cooldownModifier;
    }

    @Override
    public String getName() {
        return "Wave";
    }

    @Override
    public int getMaxAmount() {
        return 5;
    }

    @Override
    public String getDescription(int level) {
        return switch (level) {
            case 1 -> "Summon a wave of water that deals damage";
            case 2 -> "Wave damage increased by 25%";
            case 3 -> "Wave cooldown decreased by 10%";
            case 4 -> "Size increased by 25%";
            case 5 -> "Size increased by 40% and damage increased by 15%";
            default -> "No description available";
        };
    }
}
