package com.test.SurvivorGame.entity.abilityObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.test.SurvivorGame.ability.activeAbilty.EarthQuake;
import com.test.SurvivorGame.ability.activeAbilty.FireStormAbility;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.world.World;
import com.test.SurvivorGame.world.maps.GameMap;

import java.util.HashMap;

public class Earthquake extends AbilityObject {

    private float deltaDuration;

    float size;

    private Player player;

    private final float damage;

    private HashMap<Enemy, Float> damageTimers = new HashMap<>();

    public Earthquake(float x, float y, float size, Texture texture, float duration, World world, float damage) {
        super(x, y, size, size, texture);

        this.size = size;
        this.deltaDuration = duration;
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
        float timer = damageTimers.getOrDefault(enemy, EarthQuake.getDamageInterval());

        if(timer < EarthQuake.getDamageInterval())
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

        if(player != null && player.getCenter() != null && collider != null) {
            collider.setCenter(player.getCenter());
        }

        // Safely increment timers without risking ConcurrentModificationException
        // (replaceAll updates values in-place)
        damageTimers.replaceAll((enemy, t) -> t + deltaTime);

    }


    public void setScale(float size)
    {
        collider.setWidth(size);
        collider.setHeight(size);
    }

}
