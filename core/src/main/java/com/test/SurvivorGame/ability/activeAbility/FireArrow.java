package com.test.SurvivorGame.ability.activeAbility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.projectile.FireArrowProjectile;
import com.test.SurvivorGame.world.World;

public class FireArrow extends ActiveAbility {

    public static final String ID = "fire_arrow";

    private final Viewport viewport;
    private final Player player;
    private final World world;

    private float duration = 3f;
    private float width = 3f;
    private float height= 0.6f;
    private float speed = 7f;
    private int pierce = 3;

    private static float damage = 1f;

    private Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public FireArrow(World world, Viewport viewport) {
        this.player = world.getPlayer();
        this.world = world;
        this.viewport = viewport;
    }

    @Override
    public void activate() {
        FireArrowProjectile fireArrowProjectile = new FireArrowProjectile(
            player.getX(),
            player.getY(),
            width,
            height,
            texture,
            player,
            viewport,
            speed,
            duration,
            damage,
            pierce
        );

        world.addAbility(fireArrowProjectile);
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
        return "Fire Arrow";
    }

    @Override
    public int getMaxAmount() {
        return 5;
    }
}
