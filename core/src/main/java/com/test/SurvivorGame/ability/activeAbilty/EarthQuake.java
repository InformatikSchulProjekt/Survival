package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.ability_objects.Earthquake;
import com.test.SurvivorGame.world.World;

public class EarthQuake extends ActiveAbility {

    public static final String ID = "earth_quake";

    private static final float DAMAGE_INTERVAL = 1f;

    private static final float BASE_DURATION = 3f;
    private static final float BASE_SIZE = 4f;
    private static final float BASE_DAMAGE = 0.8f;
    private static final float BASE_COOLDOWN = 2f;

    private final Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public EarthQuake(World world) {
        super(ID, world, StatScope.EARTH);
    }

    @Override
    protected void activate() {
        Earthquake earthquake = new Earthquake(player.getX(), player.getY(), getSize(), texture, getDuration(), world, getDamage());

        world.addAbility(earthquake);
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

    @Override
    public float getCooldown() {
        float cooldown = BASE_COOLDOWN;

        if (getLevel() >= 3) {
            cooldown *= 0.85f;
        }

        float cooldownModifier = applyStat(1f, StatType.MAGIC_COOLDOWN_REDUCTION);

        return cooldown / cooldownModifier;
    }

    @Override
    public float getDuration() {
        float duration = BASE_DURATION;

        if (getLevel() >= 4) {
            duration *= 1.10f;
        }

        return applyStat(duration, StatType.MAGIC_DURATION);
    }

    public float getSize() {
        float size = BASE_SIZE;

        if (getLevel() == 5) {
            size *= 1.25f;
        }

        return applyStat(size, StatType.MAGIC_SIZE);
    }

    public static float getDamageInterval() {
        return DAMAGE_INTERVAL;
    }

    public void dispose() {
        texture.dispose();
    }

    @Override
    public String getName() {
        return "Earthquake Ability";
    }

    @Override
    public int getMaxAmount() {
        return 5;
    }

    @Override
    public String getDescription(int level) {
        return switch (level) {
            case 1 -> "Lets the earth rupture and causes cracks in the ground";
            case 2 -> "Earthquake damage increased by 25%";
            case 3 -> "Cooldown decreased by 15%";
            case 4 -> "Duration increased by 10%";
            case 5 -> "Size increased by 25% and damage increased by 15%";
            default -> "No description available";
        };
    }
}
