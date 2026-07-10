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

    public static final String ID = "wind_bullet";

    private static final float BASE_DURATION = 3f;
    private static final float BASE_WIDTH = 0.7f;
    private static final float BASE_HEIGHT = 0.3f;
    private static final float BASE_SPEED = 7.5f;
    private static final float BASE_COOLDOWN = 1f;
    private static final int BASE_PIERCE = 2;
    private static final float BASE_DAMAGE = 0.75f;

    private final Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public WindBullet(World world, Viewport viewport) {
        super(ID, world, viewport, StatScope.WIND);
    }

    @Override
    protected void activate() {
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

        world.addAbility(windBulletProjectile);
        SoundManager.playSound("WindBullet.wav",1f);
    }

    public void dispose() {
        texture.dispose();
    }

    public float getDamage() {
        float damage = BASE_DAMAGE;

        if (getLevel() >= 2) {
            damage *= 1.333f;
        }

        if (getLevel() == 5) {
            damage *= 1.5f;
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

        if (getLevel() >= 4) {
            cooldown *= 0.8f;
        }

        float cooldownModifier = applyStat(1f, StatType.MAGIC_COOLDOWN_REDUCTION);

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

    @Override
    public String getDescription(int level) {
        return switch (level) {
            case 1 -> "Creates a bullet of wind that pierces the enemy";
            case 2 -> "Wave damage increased by 33%";
            case 3 -> "Pierce increased by 1";
            case 4 -> "Width increased by 25% and cooldown reduced by 20%";
            case 5 -> "Damage increased by 50%";
            default -> "No description available";
        };
    }
}
