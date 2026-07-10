package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.ability_objects.projectile.WaterBlastProjectile;
import com.test.SurvivorGame.world.World;

public class WaterBlast extends ActiveAbility {

    public static final String ID = "water_blast";

    private static final float BASE_DURATION = 3f;
    private static final float BASE_WIDTH = 2f;
    private static final float BASE_HEIGHT = 1f;
    private static final float BASE_SPEED = 5f;
    private static final float BASE_COOLDOWN = 1f;
    private static final float BASE_DAMAGE = 1f;

    private final Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public WaterBlast(World world, Viewport viewport) {
        super(ID, world, viewport, StatScope.WATER);
    }

    @Override
    protected void activate() {
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

        world.addAbility(waterBlastProjectile);
    }

    public void dispose() {
        texture.dispose();
    }

    public float getDamage() {
        float damage = BASE_DAMAGE;

        if (getLevel() >= 2) {
            damage *= 1.1f;
        }

        if (getLevel() == 5) {
            damage *= 1.25f;
        }

        return applyStat(damage, StatType.MAGIC_DAMAGE);
    }

    public float getSize() {
        return applyStat(1f, StatType.MAGIC_SIZE);
    }

    @Override
    public float getCooldown() {
        float cooldown = BASE_COOLDOWN;

        if (getLevel() >= 3) {
            cooldown *= 0.9f;
        }

        if (getLevel() >= 4) {
            cooldown *= 0.8f;
        }

        float cooldownModifier = applyStat(1f, StatType.MAGIC_COOLDOWN_REDUCTION);

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

    @Override
    public String getDescription(int level) {
        return switch (level) {
            case 1 -> "Shoots a water blast that explodes on impact";
            case 2 -> "Water blast damage increased by 10%";
            case 3 -> "Water blast cooldown decreased by 10%";
            case 4 -> "Cooldown reduced by 20%";
            case 5 -> "Damage increased by 25%";
            default -> "No description available";
        };
    }
}
