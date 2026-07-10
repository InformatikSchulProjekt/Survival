package com.test.SurvivorGame.entity.ability_objects.projectile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.test.SurvivorGame.entity.Player;

public class BossProjectile extends  Projectile{

    private final float damage;

    public BossProjectile(float x, float y, float width, float height, Texture texture, Player player,
                          float speed, float duration, float damage, Vector2 direction) {

        super(
            x,
            y,
            1f,
            1f,
            texture,
            direction,
            5f,
            5f
        );

        this.damage = damage;
    }

    @Override
    public float getDamage()
    {
        return damage;
    }

    @Override
    public void onPlayerHit(Player player)
    {
        player.takeDamage(damage);

        expire();
    }

    @Override
    public boolean damagesPlayer()
    {
        return true;
    }
}
