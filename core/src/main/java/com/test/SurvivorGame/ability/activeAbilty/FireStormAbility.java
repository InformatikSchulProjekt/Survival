package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.FireStorm;
import com.test.SurvivorGame.entity.abilityObjects.melee.Melee;
import com.test.SurvivorGame.world.World;

public class FireStormAbility extends ActiveAbility{

    public static final String ID = "firestorm";
    private float duration = 2f;

    private float startSize = 0f;
    private float endSize = 5f;

    private static float damage = 0.5f;
    private static float damageInterval = 0.1f;

    private Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    private FireStorm fireStorm;
    private Player player;
    private World world;

    public FireStormAbility(World world)
    {
        this.player = world.getPlayer();
        this.world = world;
    }

    @Override
    public void activate()
    {
        fireStorm = new FireStorm(player.getX(), player.getY(), startSize, endSize, texture, duration, world);

        world.addAbility(fireStorm);
    }

    public static float getDamage()
    {
        return damage;
    }

    public static float getDamageInterval()
    {
        return damageInterval;
    }

    public void dispose()
    {
        texture.dispose();
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Firestorm Ability";
    }

    @Override
    public int getMaxAmount() {
        return 5;
    }
}
