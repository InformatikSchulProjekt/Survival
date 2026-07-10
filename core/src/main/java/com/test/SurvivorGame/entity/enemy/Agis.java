package com.test.SurvivorGame.entity.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.test.SurvivorGame.entity.ability_objects.projectile.BossProjectile;
import com.test.SurvivorGame.world.World;
import com.test.SurvivorGame.world.maps.GameMap;

public class Agis extends Enemy {

    private static final Vector2 SIZE = new Vector2(3f, 3f);
    private static final float MAX_HP = 30f;
    private static final float MOVEMENT_SPEED = 1f;
    private static final float DAMAGE = 3f;

    private static int bossCount = 1;
    private static int bossWaveCount = 30;

    private float projectileTimer = 0;
    private final Texture projectileTexture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png")); // omar mach

    private static final float Projectile_DURATION = 3f;
    private static final float Projectile_WIDTH = 3f;
    private static final float Projectile_HEIGHT = 0.6f;
    private static final float Projectile_SPEED = 6f;
    private static final float Projectile_DAMAGE = 0.75f;
    private static final float Projectile_COOLDOWN = 1f;

    public Agis(float x, float y, World world, float hpMultiplier) {
        super(x, y, world, SIZE, MAX_HP, MOVEMENT_SPEED, DAMAGE, hpMultiplier, EnemyType.AGIS);
    }

    @Override
    public void update(float deltaTime, GameMap map) {
        super.update(deltaTime, map);

        projectileTimer -= deltaTime;

        if (projectileTimer <= 0) {
            projectileTimer = Projectile_COOLDOWN;

            shootProjectile();
        }
    }

    private void shootProjectile() {

        Vector2 direction = new Vector2(getWorld().getPlayer().getCenter()).sub(getCenter()).nor();

        getWorld().addAbility(
            new BossProjectile(
                getCenter().x,
                getCenter().y,
                Projectile_WIDTH,
                Projectile_HEIGHT,
                projectileTexture,
                getWorld().getPlayer(),
                Projectile_SPEED,
                Projectile_DURATION,
                Projectile_DAMAGE,
                direction
            )
        );
    }

    public static int getBossCount() {
        return bossCount;
    }

    public static int getBossWaveCount() {
        return bossWaveCount;
    }

    @Override
    protected float getChestChance() {
        return 1f; // 100%
    }

    @Override
    public int getXPWorth() {
        return 25;
    }
}
