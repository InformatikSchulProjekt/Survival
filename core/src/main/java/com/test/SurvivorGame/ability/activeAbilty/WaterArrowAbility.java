package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.projectile.FireArrowProjectile;
import com.test.SurvivorGame.entity.abilityObjects.projectile.WaterArrowProjectile;
import com.test.SurvivorGame.world.World;

public class WaterArrowAbility extends ActiveAbility {

    public static final String ID = "water_arrow";

    // Ability base Stats
    private final float baseDuration = 3f;
    private final float baseWidth = 3f;
    private final float baseHeight = 0.6f;
    private final float baseSpeed = 6f;
    private final int basePierce = 3;
    private final float baseDamage = 0.75f;
    private final float baseCooldown = 1f;

    private Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public WaterArrowAbility(World world, Viewport viewport) {
        super(world, viewport);
    }

    @Override
    protected void activate() {
        WaterArrowProjectile waterArrowProjectile = new WaterArrowProjectile(
            player.getX(),
            player.getY(),
            baseWidth * getSize(),
            baseHeight * getSize(),
            texture,
            player,
            viewport,
            getSpeed(),
            baseDuration,
            getDamage(),
            getPierce()
        );

        world.addAbility(waterArrowProjectile);
    }

    public void dispose() {
        texture.dispose();
    }

    public float getDamage() {
        float damage = baseDamage;
        damage *= playerStats.getStat(StatType.MAGIC_DAMAGE);
        damage *= playerStats.getStat(StatScope.WATER, StatType.MAGIC_DAMAGE);
        if (getLevel() >= 2) {
            damage = 1.2f;
        }
        if (getLevel() >= 5) {
            damage = 1.25f;
        }
        return damage;

    }

    public float getSize() {
        float size = 1f;
        if (getLevel() >= 5) {
            size *= 1.05f;
        }
        return size;
    }

    public float getSpeed() {
        float speed = baseSpeed;

        return speed;
    }

    public float getDuration() {
        float duration = baseDuration;
        if (getLevel() >= 4) {
            duration *= 2f;
        }
        return duration;
    }

    public int getPierce() {
        int level = playerState.getPlayerData().abilities.getOrDefault(getID(), 0);
        int pierce = basePierce;
        if (level >= 3) pierce += 3; // wenn die Ability Level 3 erreicht wird pierce um 2 erhöht
        return pierce;
    }

    public float getCooldown() {
        float cooldown = baseCooldown;
        return cooldown;

    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Water Arrow";
    }

    @Override
    public int getMaxAmount() {
        return 5;
    }

    @Override
    public String getDescription(int level) {
        switch (level) {
            case 1:
                return "Shoots a water arrow that explodes on impact";
            case 2:
                return "Water arrow damage increased by 20%";
            case 3:
                return "Water arrow pierce increases by 3";
            case 4:
                return "Duration doubled";
            case 5:
                return "Size increased by 5% and damage increased by 25%";
            default:
                return "No description available";
        }
    }

}
