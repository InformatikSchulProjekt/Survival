package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.ability_objects.FireStorm;
import com.test.SurvivorGame.world.World;

public class FireStormAbility extends ActiveAbility {

    public static final String ID = "fire_storm";

    private static final float DAMAGE_INTERVAL = 0.1f;

    private static final float BASE_DURATION = 1f;
    private static final float START_SIZE = 0f;
    private static final float BASE_END_SIZE = 4f;
    private static final float BASE_DAMAGE = 0.5f;
    private static final float BASE_COOLDOWN = 1f;

    private final Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public FireStormAbility(World world) {
        super(ID, world, StatScope.FIRE);
    }

    @Override
    protected void activate() {
        FireStorm fireStorm = new FireStorm(
            player.getX(),
            player.getY(),
            START_SIZE,
            getEndSize(),
            texture,
            getDuration(),
            world,
            getDamage()
        );

        world.addAbility(fireStorm);
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
            cooldown *= 0.9f;
        }

        float cooldownModifier = applyStat(1f, StatType.MAGIC_COOLDOWN_REDUCTION);

        return cooldown / cooldownModifier;
    }

    @Override
    public float getDuration() {
        float duration = BASE_DURATION;

        if (getLevel() >= 4) {
            duration *= 1.1f;
        }

        return applyStat(duration, StatType.MAGIC_DURATION);
    }

    public float getEndSize() {
        float endSize = BASE_END_SIZE;

        if (getLevel() == 5) {
            endSize *= 1.25f;
        }

        return applyStat(endSize, StatType.MAGIC_SIZE);
    }

    public static float getDamageInterval() {
        return DAMAGE_INTERVAL;
    }

    public void dispose() {
        texture.dispose();
    }

    @Override
    public String getName() {
        return "Firestorm Ability";
    }

    @Override
    public int getMaxAmount() {
        return 5;
    }

    @Override
    public String getDescription(int level) {
        return switch (level) {
            case 1 -> "Emits a fire storm that deals damage in a certain interval";
            case 2 -> "Fire storm damage increased by 25%";
            case 3 -> "Cooldown decreased by 10%";
            case 4 -> "Duration increased by 10%";
            case 5 -> "End size increased by 25% and damage increased by 15%";
            default -> "No description available";
        };
    }
}
