package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.projectile.Fireball;
import com.test.SurvivorGame.world.World;

public class FireballAbility extends ActiveAbility{

    public static final String ID = "fireball";
    private final Viewport viewport;

    private float duration = 2f;
    private float effectSize = 3f;
    private float speed = 6f;
    private float explosionRadius = 2f;

    private static float damage = 2f;

    private Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    private Fireball fireball;
    private Player player;
    private World world;

    public FireballAbility(World world, Viewport viewport) {
        this.player = world.getPlayer();
        this.world = world;
        this.viewport = viewport;
    }

    @Override
    public void activate() {
        fireball = new Fireball(player.getX(), player.getY(), effectSize, texture, player, viewport, speed, duration, explosionRadius);
        world.addAbility(fireball);
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
        return "Fireball Ability";
    }

    @Override
    public int getMaxAmount() {
        return 5;
    }
}
