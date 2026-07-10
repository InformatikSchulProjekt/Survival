package com.test.SurvivorGame.entity.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.test.SurvivorGame.entity.ability_objects.projectile.BossProjectile;
import com.test.SurvivorGame.world.World;
import com.test.SurvivorGame.world.maps.GameMap;

public class WatcherBoss extends Enemy {
    private static final Vector2 SIZE = new Vector2(2.5f, 3f);
    private static final float MAX_HP = 67f;
    private static final float MOVEMENT_SPEED = 1.5f;
    private static final float DAMAGE = 6.7f;

    private static int bossCount = 1;
    private static int bossWaveCount = 30;

    private float projectileTimer = 0;
    private final Texture projectileTexture = new Texture(Gdx.files.internal("Ability/fireball0001.png"));

    private static final float Projectile_DURATION = 10f;
    private static final float Projectile_WIDTH = 1f;
    private static final float Projectile_HEIGHT = 0.6f;
    private static final float Projectile_SPEED = 10f;
    private static final float Projectile_DAMAGE = 1f;
    private static final float Projectile_COOLDOWN = 0.5f;

    public WatcherBoss(float x, float y, World world, float hpMultiplier, float dmgScale) {
        super(x, y, world, SIZE, MAX_HP, MOVEMENT_SPEED, DAMAGE*dmgScale, hpMultiplier, EnemyType.WATCHER);
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

        float randomOffset = com.badlogic.gdx.math.MathUtils.random(-15f, 15f);

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
        return 0f; // 0%, Bosse sollen normalerweise keine Chest spawnen
    }

    @Override
    public int getXPWorth() {
        return 30;
    }
}
