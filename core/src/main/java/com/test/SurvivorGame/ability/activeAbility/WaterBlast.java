package com.test.SurvivorGame.ability.activeAbility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.projectile.WaterBlastProjectile;
import com.test.SurvivorGame.world.World;

public class WaterBlast extends ActiveAbility {

    public static final String ID = "water_blast";

    private final Viewport viewport;
    private final Player player;
    private final World world;

    private float duration = 3f;
    private float width = 2f;
    private float height= 1f;
    private float speed = 7f;

    private static float damage = 0.5f;

    private Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public WaterBlast(World world, Viewport viewport) {
        this.player = world.getPlayer();
        this.world = world;
        this.viewport = viewport;
    }

    @Override
    public void activate() {
        WaterBlastProjectile waterBlastProjectile = new WaterBlastProjectile(
            player.getX(),
            player.getY(),
            width,
            height,
            texture,
            player,
            viewport,
            speed,
            duration,
            damage
        );

        world.addAbility(waterBlastProjectile);
    }

    public void dispose() {
        texture.dispose();
    }

    public static float getDamage() {
        return damage;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Water Blast";
    }

    @Override
    public int getMaxAmount() {
        return 5;
    }
}
