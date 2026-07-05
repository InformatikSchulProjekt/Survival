package com.test.SurvivorGame.entity.abilityObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.test.SurvivorGame.ability.activeAbilty.FireStormAbility;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.enemy.Enemy;
import com.test.SurvivorGame.world.World;
import com.test.SurvivorGame.world.maps.GameMap;

public class FireStorm extends AbilityObject{

    private float deltaDuration;
    private float duration;
    private float damageTimer = 0;

    private float elapsedTime = 0;
    float startSize, endSize;

    private Player player;

    public FireStorm(float x, float y, float startSize, float endSize, Texture texture, float duration, World world) {
        super(x, y, startSize, startSize, texture);

        this.startSize = startSize;
        this.endSize = endSize;
        this.deltaDuration = duration;
        this.duration = duration;

        this.player = world.getPlayer();
    }

    @Override
    public boolean getExpired()
    {
        return deltaDuration <= 0;
    }

    @Override
    public float getDamage()
    {
        return FireStormAbility.getDamage();
    }

    @Override
    public void onHit(Enemy enemy)
    {
        if(damageTimer < FireStormAbility.getDamageInterval())
        {
            return;
        }

        enemy.takeDamage(getDamage(), player.getPlayerState());

        damageTimer = 0;
    }

    @Override
    public void update(float deltaTime, GameMap map)
    {
        deltaDuration -= deltaTime;

        damageTimer += deltaTime;

        collider.setCenter(player.getCenter());

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
