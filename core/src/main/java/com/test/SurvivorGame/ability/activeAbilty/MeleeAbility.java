package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.ability_objects.melee.Melee;
import com.test.SurvivorGame.world.World;

public class MeleeAbility extends ActiveAbility {

    public static final String ID = "melee";

    private float duration = 1f;
    private float effectSize = 2f;

    private static float damage = 0.5f;
    private static float damageInterval = 0.25f; // interfval maye niedriger von der dauer stellen und dmg niederiger. hier wird sonst nur jede halbe sekunde übeprrüft ob es ne halbe sekunde lang overlap ist
    private float baseCooldown = 1f; // müsst ihr noch anpassen

    private Texture texture = new Texture(Gdx.files.internal("Placeholder/MeleeAbilityPH.png"));

    private Melee melee;
    private Player player;
    private World world;


    public MeleeAbility(World world, Viewport viewport)
    {
        super(world,viewport);
    }

    @Override
    protected void activate()
    {
        melee = new Melee(player.getX(), player.getY(), effectSize, duration, texture, player, viewport);

        world.addAbility(melee);
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
        return "Melee Ability";
    }

    @Override public int getMaxAmount() {
        return 5;
    }

    public float getCooldown() {
        float cooldown = baseCooldown;
        cooldown *= playerStats.getStat(StatType.MAGIC_COOLDOWN);
        return cooldown;
    }

}
