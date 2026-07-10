package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.ability_objects.projectile.WaveProjectile;
import com.test.SurvivorGame.world.World;

public class Wave extends ActiveAbility {

    public static final String ID = "wave";

    private float duration = 3f;
    private float baseWidth = 0.5f;
    private float height= 5f;
    private float speed = 5f;
    private float baseCooldown = 1f; // müsst ihr noch anpassen

    private static float baseDamage = 0.75f;

    private Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public Wave(World world, Viewport viewport) {
        super(world, viewport);
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
        if(getLevel() >= 2){
            damage *= 1.25f;
        }
        if(getLevel() ==5){
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
        if(getLevel() >= 3){
            cooldown *= 0.9f;
        }
        return cooldown;
    }
    public float getWidth(){
        float width = baseWidth;
        if(getLevel() >= 4){
            width *= 1.25f;
        }
        if (getLevel() == 5){
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
