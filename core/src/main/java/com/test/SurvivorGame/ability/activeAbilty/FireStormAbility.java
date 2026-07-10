package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.ability_objects.FireStorm;
import com.test.SurvivorGame.world.World;

public class FireStormAbility extends ActiveAbility {

    public static final String ID = "fire_storm";

    private static float damageInterval = 0.1f;
    // Ability base Stats
    private float baseDuration = 1f;
    private float startSize = 0f;
    private float baseEndSize = 4f;
    private static float baseDamage = 0.5f;
    private Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));
    private float baseCooldown = 1f; // müsst ihr noch anpassen

    public FireStormAbility(World world) {
       super(world);

    }

    @Override
    protected void activate() {
        FireStorm fireStorm = new FireStorm(player.getX(), player.getY(), startSize, getBaseEndSizeSize(), texture, getDuration(), world, getDamage());

        world.addAbility(fireStorm);
    }

    public float getDamage() {
        float damage = baseDamage;
        damage *= playerStats.getStat(StatType.MAGIC_DAMAGE);
        damage *= playerStats.getStat(StatScope.FIRE, StatType.MAGIC_DAMAGE);
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
        return "Firestorm Ability";
    }

    @Override
    public int getMaxAmount() {
        return 5;
    }

    public float getCooldown() {
        float cooldown = baseCooldown;
        if (getLevel()>=3){
            cooldown *= 0.9f;
        }
        return cooldown;
    }
    //@Override
    public float getDuration() {
        float duration = baseDuration;
        if (getLevel()>=4){
            duration *= 1.1f;
        }
        // temp:
        return duration;
    }
    //@Override
    public float getBaseEndSizeSize(){
        baseEndSize = startSize +4f;

        if(getLevel() ==5){
        baseEndSize*=1.25f;
        }
        return baseEndSize;
    }

    @Override
    public String getDescription(int level) {
        switch (level) {
            case 1:
                return "Emits a fire storm that deals damage in a certain interval";
            case 2:
                return "Fire storm damage increased by 25%";
            case 3:
                return "Cooldown decreased by 10%";
            case 4:
                return "duration increased by 10%";
            case 5:
                return "End size increased by 25% and damage increased by 15%";
            default:
                return "No description available";
        }
    }
}
