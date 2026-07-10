package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.projectile.Projectile;
import com.test.SurvivorGame.world.World;

public class ProjectileAbility extends ActiveAbility {

    public static final String ID = "projectile";

    private float duration = 4f;
    private float width = 3f;
    private float height = 0.6f;
    private float speed = 6f;
    private static float damage = 1f;
    private float baseCooldown = 1f; // müsst ihr noch anpassen

    private Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    private Projectile projectile;
    private Player player;
    private World world;


    public ProjectileAbility(World world, Viewport viewport)
    {
        super(world, viewport);
    }

    @Override
    protected void activate()
    {
        projectile = new Projectile(player.getX(), player.getY(), width, height, texture, player, viewport, speed, duration);

        world.addAbility(projectile);
    }

    public void dispose()
    {
        texture.dispose();
    }

    public static float getDamage()
    {
        return damage;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Projectile Ability";
    }

    @Override
    public int getMaxAmount() {
        return 5;
    }

    public float getCooldown() {
        float cooldown = baseCooldown;
        cooldown *= playerStats.getStat(StatType.MAGIC_COOLDOWN);
        return cooldown;
    }
}
