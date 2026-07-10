package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.SoundManager;
import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.projectile.Fireball;
import com.test.SurvivorGame.world.World;

public class FireballAbility extends ActiveAbility {

    public static final String ID = "fireball";

    private final float baseExplosionRadius = 2.5f; // deutlich größer als die Hitbox, testet gern rum

    private final float baseDuration = 3f;
    private final float baseWidth = 2f;
    private final float baseHeight = 2f;
    private final float baseSpeed = 5f;
    private final float baseDamage = 2f;
    private final float baseCooldown = 1f;
    // müsst ihr noch anpassen

    private final Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    private Fireball fireball;

    public FireballAbility(World world, Viewport viewport) {
        super(world, viewport);
    }

    @Override
    protected void activate() {
        fireball = new Fireball(
            player.getX(),
            player.getY(),
            baseWidth * getSize(),
            baseHeight * getSize(),
            texture,
            player,
            viewport,
            world,
            getSpeed(),
            baseDuration,
            getDamage(),
            baseExplosionRadius * getSize()   // skaliert mit dem Size-Stat, aber eigenständig
        );
        world.addAbility(fireball);
        SoundManager.playSound("fireBall.wav");
    }

    public void dispose() {
        texture.dispose();
    }

    public float getDamage() {
        float damage = baseDamage;
        damage *= playerStats.getStat(StatType.MAGIC_DAMAGE);
        damage *= playerStats.getStat(StatScope.FIRE, StatType.MAGIC_DAMAGE);
        if (getLevel() >= 2) {
            damage *= 1.15f;
        }
        if (getLevel() == 5) {
            damage *= 1.15f;
        }
        return damage;
    }

    public float getSize() {
        float size = 1f;
        size *= playerStats.getStat(StatType.MAGIC_SIZE);
        size *= playerStats.getStat(StatScope.FIRE, StatType.MAGIC_SIZE);
        if (getLevel() >= 3) {
            size *= 1.05f;
        }
        if (getLevel() == 5) {
            size *= 1.05f;
        }
        return size;
    }

    public float getSpeed() {
        return baseSpeed;
    }

    public float getCooldown() {
        float cooldown = baseCooldown;
        if (getLevel() >= 4) {
            cooldown *= 0.85f;
        }
        System.out.println("FIREBALL CD: "+cooldown);
        return cooldown;
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

    @Override
    public String getDescription(int level) {
        switch (level) {
            case 1:
                return "Shoots a fireball that explodes on impact";
            case 2:
                return "Fireball damage increased by 15%";
            case 3:
                return "Fireball size increases by 5%";
            case 4:
                return "Cooldown decreased by 15%";
            case 5:
                return "Size increased by 5% and damage increased by 15%";
            default:
                return "No description available";
        }
    }
}
