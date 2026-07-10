package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.ability_objects.projectile.Projectile;
import com.test.SurvivorGame.world.World;

public class ProjectileAbility extends ActiveAbility {

    public static final String ID = "projectile";

    private static final float BASE_DURATION = 4f;
    private static final float BASE_WIDTH = 3f;
    private static final float BASE_HEIGHT = 0.6f;
    private static final float BASE_SPEED = 6f;
    private static final float BASE_DAMAGE = 1f;
    private static final float BASE_COOLDOWN = 1f;

    private final Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public ProjectileAbility(World world, Viewport viewport) {
        super(ID, world, viewport, StatScope.ALL);
    }

    @Override
    protected void activate() {
        Projectile projectile = new Projectile(
            player.getX(),
            player.getY(),
            BASE_WIDTH*getSize(),
            BASE_HEIGHT*getSize(),
            texture,
            player,
            viewport,
            BASE_SPEED,
            BASE_DURATION
        );

        world.addAbility(projectile);
    }

    public float getDamage() {
        return applyStat(BASE_DAMAGE, StatType.MAGIC_DAMAGE);
    }

    @Override
    public float getCooldown() {
        float cooldownModifier = applyStat(1f, StatType.MAGIC_COOLDOWN_REDUCTION);

        return BASE_COOLDOWN / cooldownModifier;
    }

    public float getSize() {
        return applyStat(1f, StatType.MAGIC_SIZE);
    }

    public void dispose() {
        texture.dispose();
    }

    @Override
    public String getName() {
        return "Projectile Ability";
    }

    @Override
    public int getMaxAmount() {
        return 5;
    }
}
