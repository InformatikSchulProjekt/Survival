package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.FireStorm;
import com.test.SurvivorGame.world.World;

public class FireStormAbility extends ActiveAbility{

    public static final String ID = "firestorm";

    private final Player player;
    private final World world;
    private final PlayerStats playerStats;

    private static float damageInterval = 0.01f;
    // Ability base Stats
    private float baseDuration = 1f;
    private float startSize = 0f;
    private float baseEndSize = 5f;
    private static float baseDamage = 0.5f;
    private Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));
    private float baseCooldown = 1f; // müsst ihr noch anpassen

    public FireStormAbility(World world)
    {
        this.player = world.getPlayer();
        this.world = world;
        this.playerStats = player.getPlayerState().getPlayerStats();
    }

    @Override
    protected void activate()
    {
        FireStorm fireStorm = new FireStorm(player.getX(), player.getY(), startSize, baseEndSize, texture, baseDuration, world, getDamage());

        world.addAbility(fireStorm);
    }

    public float getDamage()
    {
        float damage = baseDamage;
        damage *= playerStats.getStat(StatType.MAGIC_DAMAGE);
        damage *= playerStats.getStat(StatScope.FIRE, StatType.MAGIC_DAMAGE);
        return baseDamage;
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

    public float getCooldown() {
        float cooldown = baseCooldown;
        cooldown *= playerStats.getStat(StatType.MAGIC_COOLDOWN);
        cooldown *= playerStats.getStat(StatScope.FIRE, StatType.MAGIC_COOLDOWN);
        return cooldown;
    }

    @Override
    public float getDuration() {
        // temp:
        return baseDuration;
    }
}
