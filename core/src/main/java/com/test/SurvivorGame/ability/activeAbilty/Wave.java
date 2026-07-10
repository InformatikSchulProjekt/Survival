package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.projectile.WaterBlastProjectile;
import com.test.SurvivorGame.entity.abilityObjects.projectile.WaveProjectile;
import com.test.SurvivorGame.world.World;

public class Wave extends ActiveAbility {

    public static final String ID = "wave";

    private final Viewport viewport;
    private final Player player;
    private final World world;
    private final PlayerStats playerStats;
    private final PlayerState playerState;
    private int level;

    private float duration = 3f;
    private float baseWidth = 2f;
    private float height= 1.2f;
    private float speed = 5f;
    private float baseCooldown = 1f; // müsst ihr noch anpassen

    private static float baseDamage = 0.75f;

    private Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public Wave(World world, Viewport viewport) {
        this.player = world.getPlayer();
        this.world = world;
        this.playerStats = player.getPlayerState().getPlayerStats();
        this.viewport = viewport;
        this.playerState = player.getPlayerState();
        this.level = playerState.getPlayerData().abilities.getOrDefault(getID(), 0);
    }

    @Override
    protected void activate() {
        WaveProjectile waveProjectile = new WaveProjectile(
            player.getX(),
            player.getY(),
            getWidth(),
            height,
            texture,
            player,
            viewport,
            speed,
            duration,
            getDamage()
        );

        world.addAbility(waveProjectile);
    }

    public void dispose() {
        texture.dispose();
    }

    public float getDamage() {
        float damage = baseDamage;
        damage *= playerStats.getStat(StatType.MAGIC_DAMAGE);
        damage *= playerStats.getStat(StatScope.WATER, StatType.MAGIC_DAMAGE);
        if(level >= 2){
            damage *= 1.25f;
        }
        if(level ==5){
            damage *= 1.15f;
        }
        return damage;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Wave";
    }

    @Override
    public int getMaxAmount() {
        return 5;
    }

    public float getCooldown() {
        float cooldown = baseCooldown;
        cooldown *= playerStats.getStat(StatType.MAGIC_COOLDOWN);
        cooldown *= playerStats.getStat(StatScope.WATER, StatType.MAGIC_COOLDOWN);
        if(level >= 3){
            cooldown *= 0.9f;
        }
        return cooldown;
    }
    public float getWidth(){
        float width = baseWidth;
        if(level >= 4){
            width *= 1.25f;
        }
        if (level == 5){
            width *= 1.4;
        }
        return width;
    }
    @Override
    public String getDescription(int level) {
        switch (level) {
            case 1:
                return "creates a wave that deals damage";
            case 2:
                return "Wave damage increased by 25%";
            case 3:
                return "Wave cooldown decreased by 10%";
            case 4:
                return "Width increased by 25%";
            case 5:
                return "Width increased by 40% and damage increased by 15%";
            default:
                return "No description available";
        }
        //reichweite noch einbauen morgen
    }
}
