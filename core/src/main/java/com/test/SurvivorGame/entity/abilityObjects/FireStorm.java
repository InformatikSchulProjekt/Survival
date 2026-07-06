package com.test.SurvivorGame.entity.abilityObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.test.SurvivorGame.ability.activeAbilty.FireStormAbility;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.world.World;
import com.test.SurvivorGame.world.maps.GameMap;

import java.util.HashMap;

public class FireStorm extends AbilityObject {

    private float deltaDuration;
    private float duration;

    private float elapsedTime = 0;
    float startSize, endSize;

    private Player player;

    private final float damage;

    private HashMap<Enemy, Float> damageTimers = new HashMap<>();

    public FireStorm(float x, float y, float startSize, float endSize, Texture texture, float duration, World world, float damage) {
        super(x, y, startSize, startSize, texture);

        this.startSize = startSize;
        this.endSize = endSize;
        this.deltaDuration = duration;
        this.duration = duration;

        this.player = world.getPlayer();
        this.damage = damage;
    }

    @Override
    public boolean getExpired()
    {
        return deltaDuration <= 0;
    }

    @Override
    public float getDamage()
    {
        return damage;
    }

    @Override
    public void onHit(Enemy enemy)
    {
        float timer = damageTimers.getOrDefault(enemy, FireStormAbility.getDamageInterval());

        if(timer < FireStormAbility.getDamageInterval())
        {
            return;
        }

        enemy.takeDamage(getDamage());

        damageTimers.put(enemy, 0f);
    }

    @Override
    public void update(float deltaTime, GameMap map)
    {
        deltaDuration -= deltaTime;

        collider.setCenter(player.getCenter());

        for(Enemy enemy : damageTimers.keySet())
        {
            damageTimers.put(enemy, damageTimers.get(enemy) + deltaTime);
        }

        grow(deltaTime);
    }

    public void grow(float deltaTime)
    {
        elapsedTime += deltaTime;

        float t = elapsedTime / duration;
        t = Math.min(t, 1f);

        float size = MathUtils.lerp(startSize, endSize, t);
        setScale(size);
    }

    public void setScale(float size)
    {
        collider.setWidth(size);
        collider.setHeight(size);
    }

}
