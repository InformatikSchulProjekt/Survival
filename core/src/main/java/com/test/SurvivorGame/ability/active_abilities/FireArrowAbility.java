package com.test.SurvivorGame.ability.active_abilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.ability_objects.projectile.FireArrowProjectile;
import com.test.SurvivorGame.world.World;

public class FireArrowAbility extends ActiveAbility {

    public static final String ID = "fire_arrow";

    private static final float BASE_DURATION = 3f;
    private static final float BASE_WIDTH = 3f;
    private static final float BASE_HEIGHT = 0.6f;
    private static final float BASE_SPEED = 6f;
    private static final int BASE_PIERCE = 3;
    private static final float BASE_DAMAGE = 0.75f;
    private static final float BASE_COOLDOWN = 1f;

    private final Texture texture =
        new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public FireArrowAbility(World world, Viewport viewport) {
        super(ID, world, viewport, StatScope.FIRE);
    }

    @Override
    protected void activate() {
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

        world.addAbility(fireArrowProjectile);
    }

    public float getDamage() {
        float damage = BASE_DAMAGE;

        if (getLevel() >= 2) {
            damage *= 1.15f;
        }

        if (getLevel() >= 5) {
            damage *= 1.15f;
        }

        return applyStat(damage, StatType.MAGIC_DAMAGE);
    }

    public float getSize() {
        float size = 1f;

        if (getLevel() >= 5) {
            size *= 1.1f;
        }

        return applyStat(size, StatType.MAGIC_SIZE);
    }

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
        if (getLevel() >= 4) {
            cooldown *= 0.85f;
        }

        float cooldownModifier = playerStats.getStat(getScope(), StatType.MAGIC_COOLDOWN_REDUCTION);

        return cooldown / cooldownModifier;
    }

    public void dispose() {
        texture.dispose();
    }

    @Override
    public String getName() {
        return "Fire Arrow";
    }

    @Override
    public int getMaxAmount() {
        return 5;
    }

    @Override
    public String getDescription(int level) {
        return switch (level) {
            case 1 -> "Shoots a fire arrow that explodes on impact";
            case 2 -> "Fire arrow damage increased by 15%";
            case 3 -> "Fire arrow pierce increases by 2";
            case 4 -> "Cooldown decreased by 15%";
            case 5 -> "Size increased by 10%, damage increased by 15% and +2 Pierce";
            default -> "No description available";
        };
    }
}
