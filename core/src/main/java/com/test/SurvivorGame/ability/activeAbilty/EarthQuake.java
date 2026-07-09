package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.Earthquake;
import com.test.SurvivorGame.entity.abilityObjects.FireStorm;
import com.test.SurvivorGame.world.World;

public class EarthQuake extends ActiveAbility {

    public static final String ID = "earth_quake";

    private final Player player;
    private final World world;
    private final PlayerStats playerStats;
    private final PlayerState playerState;
    private final int level;

    private static float damageInterval = 1f;
    // Ability base Stats
    private float baseDuration = 3f;
    private float baseSize = 4f;
    private static float baseDamage = 0.8f;
    private Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));
    private float baseCooldown = 2f; // müsst ihr noch anpassen

    public EarthQuake(World world) {
        this.player = world.getPlayer();
        this.world = world;
        this.playerStats = player.getPlayerState().getPlayerStats();
        this.playerState = player.getPlayerState();
        this.level = playerState.getPlayerData().abilities.getOrDefault(getID(), 0);

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
//float x, float y, float startSize, float endSize, Texture texture, float duration, World world, float damage
        world.addAbility(earthquake);
    }

    public float getDamage() {
        float damage = baseDamage;
        damage *= playerStats.getStat(StatType.MAGIC_DAMAGE);
        damage *= playerStats.getStat(StatScope.EARTH, StatType.MAGIC_DAMAGE);
        if (level >= 2){
            damage *= 1.25f;        }
        if (level==5){
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
        cooldown *= playerStats.getStat(StatType.MAGIC_COOLDOWN);
        cooldown *= playerStats.getStat(StatScope.EARTH, StatType.MAGIC_COOLDOWN);
        if (level>=3){
            cooldown *= 0.85f;
        }
        return cooldown;
    }
    //@Override
    public float getDuration(float duration) {
        float baseDuration = duration;
        if (level>=4){
            duration *= 1.1f;
        }
        // temp:
        return duration;
    }
    //@Override
    public float getBaseSize(){
       float size = baseSize;
        if(level == 5) size*=1.25f;
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
