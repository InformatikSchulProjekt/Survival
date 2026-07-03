package com.test.SurvivorGame.ability.activeAbility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.melee.Melee;
import com.test.SurvivorGame.world.World;

public class MeleeAbility extends ActiveAbility {

    public static final String ID = "melee";
    private final Viewport viewport;

    private float coolDown;
    private float duration = 1f;
    private float effectSize = 2f;

    private static float damage = 0.5f;
    private static float damageInterval = 0.25f; // interfval maye niedriger von der dauer stellen und dmg niederiger. hier wird sonst nur jede halbe sekunde übeprrüft ob es ne halbe sekunde lang overlap ist

    private Texture texture = new Texture(Gdx.files.internal("Placeholder/MeleeAbilityPH.png"));

    private Melee melee;
    private Player player;
    private World world;


    public MeleeAbility(World world, Viewport viewport)
    {
        this.player = world.getPlayer();
        this.world = world;
        this.viewport = viewport;
    }

    @Override
    public void activate()
    {
        melee = new Melee(player.getX(), player.getY(), effectSize, duration, texture, player, viewport);

        world.addAbility(melee);
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


}
