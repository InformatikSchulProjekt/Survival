package com.test.SurvivorGame.ability.activeAbility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.projectile.Projectile;
import com.test.SurvivorGame.world.World;

public class ProjectileAbility extends ActiveAbility {

    public static final String ID = "projectile";
    private final Viewport viewport;

    private float coolDown;
    private float duration = 4f;
    private float effectSize = 2f;

    private float speed = 6f;

    private static float damage = 1f;

    private Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    private Projectile projectile;
    private Player player;
    private World world;


    public ProjectileAbility(World world, Viewport viewport)
    {
        this.player = world.getPlayer();
        this.world = world;
        this.viewport = viewport;
    }

    @Override
    public void activate()
    {
        projectile = new Projectile(player.getX(), player.getY(), effectSize, texture, player, viewport, speed, duration);

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
}
