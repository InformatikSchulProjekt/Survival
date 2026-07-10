package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.SoundManager;
import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.ability_objects.Earthquake;
import com.test.SurvivorGame.world.World;

public class EarthQuake extends ActiveAbility {

    public static final String ID = "earth_quake";



    private static float damageInterval = 1f;
    // Ability base Stats
    private float baseDuration = 3f;
    private float baseSize = 4f;
    private static float baseDamage = 0.8f;
    private Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));
    private float baseCooldown = 2f; // müsst ihr noch anpassen

    public EarthQuake(World world) {
        super(world);
    }


    @Override
    protected void activate() {
        Earthquake earthquake= new Earthquake(
            player.getX(),
            player.getY(),
            getBaseSize(),
            texture,
            getDuration(),
            world,
            getDamage()
        );

        world.addAbility(earthquake);
        SoundManager.playSound("EarthQuake.wav");
    }

    public float getDamage() {
        float damage = baseDamage;
        damage *= playerStats.getStat(StatType.MAGIC_DAMAGE);
        damage *= playerStats.getStat(StatScope.EARTH, StatType.MAGIC_DAMAGE);
        if (getLevel() >= 2){
            damage *= 1.25f;        }
        if (getLevel()==5){
            damage*= 1.15f;
        }
        return damage;
    }

    public static float getDamageInterval() {
        return damageInterval;
    }

    public void dispose() {
        texture.dispose();
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Earthquake Ability";
    }

    @Override
    public int getMaxAmount() {
        return 5;
    }

    public float getCooldown() {
        float cooldown = baseCooldown;
        if (getLevel()>=3){
            cooldown *= 0.85f;
        }
        return cooldown;
    }

    //@Override
    public float getBaseSize(){
       float size = baseSize;
        if(getLevel() == 5) size*=1.25f;
        return size;
    }

    @Override
    public String getDescription(int level) {
        switch (level) {
            case 1:
                return "Lets the earth rupture and causes cracks in the ground";
            case 2:
                return "Earthquake damage increased by 25%";
            case 3:
                return "Cooldown decreased by 15%";
            case 4:
                return "duration increased by 10%";
            case 5:
                return "Size increased by 25% and damage increased by 15%";
            default:
                return "No description available";
        }
    }
}
