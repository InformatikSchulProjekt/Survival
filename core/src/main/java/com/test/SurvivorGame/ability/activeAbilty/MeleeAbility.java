package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.ability_objects.melee.Melee;
import com.test.SurvivorGame.world.World;

public class MeleeAbility extends ActiveAbility {

    public static final String ID = "melee";

    private static final float BASE_DURATION = 1f;
    private static final float BASE_SIZE = 2f;
    private static final float BASE_DAMAGE = 0.5f;
    private static final float DAMAGE_INTERVAL = 0.25f;
    private static final float BASE_COOLDOWN = 1f;

    private final Texture texture = new Texture(Gdx.files.internal("Placeholder/MeleeAbilityPH.png"));

    public MeleeAbility(World world, Viewport viewport) {
        super(ID, world, viewport, StatScope.ALL);
    }

    @Override
    protected void activate() {
        Melee melee = new Melee(
            player.getX(),
            player.getY(),
            getSize(),
            getDuration(),
            texture,
            player,
            viewport
        );

        world.addAbility(melee);
    }

    public float getDamage() {
        float damage = BASE_DAMAGE;

        return applyStat(damage, StatType.MAGIC_DAMAGE);
    }

    public static float getDamageInterval() {
        return DAMAGE_INTERVAL;
    }

    @Override
    public float getCooldown() {
        float cooldown = BASE_COOLDOWN;

        float cooldownModifier = applyStat(1f, StatType.MAGIC_COOLDOWN_REDUCTION);

        return cooldown / cooldownModifier;
    }

    @Override
    public float getDuration() {
        return applyStat(BASE_DURATION, StatType.MAGIC_DURATION);
    }

    public float getSize() {
        return applyStat(BASE_SIZE, StatType.MAGIC_SIZE);
    }

    public void dispose() {
        texture.dispose();
    }

    @Override
    public String getName() {
        return "Melee Ability";
    }

    @Override
    public int getMaxAmount() {
        return 5;
    }
}
