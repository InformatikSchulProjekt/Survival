package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.ability_objects.projectile.RockBlastProjectile;
import com.test.SurvivorGame.world.World;

// macht etwas mehr DMG weil es halt stein ist
public class RockBlast extends ActiveAbility {

    public static final String ID = "rock_blast";

    private static final float BASE_DURATION = 3f;
    private static final float BASE_WIDTH = 2f;
    private static final float BASE_HEIGHT = 1f;
    private static final float BASE_SPEED = 2f;
    private static final float BASE_COOLDOWN = 1f;
    private static final float BASE_DAMAGE = 1.5f;

    private final Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public RockBlast(World world, Viewport viewport) {
        super(ID, world, viewport, StatScope.EARTH);
    }

    @Override
    protected void activate() {
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

        world.addAbility(rockBlastProjectile);
    }

    public float getDamage() {
        float damage = BASE_DAMAGE;

        if (getLevel() >= 2) {
            damage *= 1.1f;
        }

        if (getLevel() == 5) {
            damage *= 1.3f;
        }

        return applyStat(damage, StatType.MAGIC_DAMAGE);
    }

    @Override
    public float getCooldown() {
        float cooldown = BASE_COOLDOWN;

        if (getLevel() >= 3) {
            cooldown *= 0.9f;
        }

        if (getLevel() >= 4) {
            cooldown *= 0.85f;
        }

        float cooldownModifier = applyStat(1f, StatType.MAGIC_COOLDOWN_REDUCTION);

        return cooldown / cooldownModifier;
    }

    public float getSize() {
        return applyStat(1f, StatType.MAGIC_SIZE);
    }

    public void dispose() {
        texture.dispose();
    }

    @Override
    public String getName() {
        return "Rock blast";
    }

    @Override
    public int getMaxAmount() {
        return 5;
    }

    @Override
    public String getDescription(int level) {
        return switch (level) {
            case 1 -> "Shoots a rock blast that explodes on impact";
            case 2 -> "Rock blast damage increased by 10%";
            case 3 -> "Rock blast cooldown decreased by 10%";
            case 4 -> "Cooldown reduced by 15%";
            case 5 -> "Damage increased by 30%";
            default -> "No description available";
        };
    }
}
