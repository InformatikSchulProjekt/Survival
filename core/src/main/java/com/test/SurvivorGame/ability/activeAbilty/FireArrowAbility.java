package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.projectile.FireArrowProjectile;
import com.test.SurvivorGame.world.World;

public class FireArrowAbility extends ActiveAbility {

    public static final String ID = "fire_arrow";

    private final Viewport viewport;
    private final Player player;
    private final World world;
    private final PlayerStats playerStats;

    // Ability base Stats
    private final float baseDuration = 3f;
    private final float baseWidth = 3f;
    private final float baseHeight= 0.6f;
    private final float baseSpeed = 7f;
    private final int basePierce = 3;
    private final float baseDamage = 0.5f;

    private Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public FireArrowAbility(World world, Viewport viewport) {
        this.player = world.getPlayer();
        this.world = world;
        this.viewport = viewport;
        this.playerStats =  player.getPlayerState().getPlayerStats();
    }

    @Override
    public void activate() {
        FireArrowProjectile fireArrowProjectile = new FireArrowProjectile(
            player.getX(),
            player.getY(),
            baseWidth*getSize(),
            baseHeight*getSize(),
            texture,
            player,
            viewport,
            getSpeed(),
            getDuration(),
            getDamage(),
            getPierce()
        );

        world.addAbility(fireArrowProjectile);
    }

    public void dispose() {
        texture.dispose();
    }

    public float getDamage() {
        float damage = baseDamage;
        damage *= playerStats.getStat(StatType.MAGIC_DAMAGE);
        damage *= playerStats.getStat(StatScope.FIRE, StatType.MAGIC_DAMAGE);
        return damage;
    }

    public float getSize() {
        float size = 1f;
        size *= playerStats.getStat(StatType.MAGIC_SIZE);
        size *= playerStats.getStat(StatScope.FIRE, StatType.MAGIC_SIZE);
        return size;
    }

    public float getSpeed() {
        float speed = baseSpeed;

        return speed;
    }

    public float getDuration() {
        float duration = baseDuration;
        duration *= playerStats.getStat(StatType.MAGIC_DURATION);
        duration *= playerStats.getStat(StatScope.FIRE, StatType.MAGIC_DURATION);
        return duration;
    }

    public int getPierce() {
        int pierce = basePierce;

        return pierce;
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
