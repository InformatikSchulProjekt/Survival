package com.test.SurvivorGame.ability;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.melee.Melee;
import com.test.SurvivorGame.world.World;

public class AttackAbility extends Ability {

    private final Viewport viewport;

    private float coolDown;
    private float duration = 1f;
    private float effectSize = 2f;

    private static float damage = 1f;
    private static float damageInterval = 0.5f; // interfval maye niedriger von der dauer stellen und dmg niederiger. hier wird sonst nur jede halbe sekunde übeprrüft ob es ne halbe sekunde lang overlap ist

    private Texture texture = new Texture(Gdx.files.internal("Placeholder/AttackAbillityPH.png"));

    private Melee melee;
    private Player player;
    private World world;


    public AttackAbility(Player player, World world, Viewport viewport)
    {
        this.player = player;
        this.world = world;
        this.viewport = viewport;
    }

    public void activate()
    {
        melee = new Melee(player.getX(), player.getY(), effectSize, duration, texture, player, viewport);

        world.addAbillity(melee);
    }

    public void dispose()
    {
        texture.dispose();
    }

    public static float getDamage()
    {
        return damage;
    }

    public static float getDamageInterval()
    {
        return damageInterval;
    }

}
