package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.FireStorm;
import com.test.SurvivorGame.world.World;

public class FireStormAbility extends ActiveAbility {

    public static final String ID = "fire_storm";

    private final Player player;
    private final World world;
    private final PlayerStats playerStats;
    private final PlayerState playerState;
    private final int level;

    private static float damageInterval = 0.1f;
    // Ability base Stats
    private float baseDuration = 1f;
    private float startSize = 0f;
    private float baseEndSize = 4f;
    private static float baseDamage = 0.5f;
    private Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));
    private float baseCooldown = 1f; // müsst ihr noch anpassen

    public FireStormAbility(World world) {
        this.player = world.getPlayer();
        this.world = world;
        this.playerStats = player.getPlayerState().getPlayerStats();
        this.playerState = player.getPlayerState();
        this.level = playerState.getPlayerData().abilities.getOrDefault(getID(), 0);

    }

    @Override
    protected void activate() {
        FireStorm fireStorm = new FireStorm(player.getX(), player.getY(), startSize, getBaseEndSizeSize(), texture, baseDuration, world, getDamage());

        world.addAbility(fireStorm);
    }

    public float getDamage() {
        float damage = baseDamage;
        damage *= playerStats.getStat(StatType.MAGIC_DAMAGE);
        damage *= playerStats.getStat(StatScope.FIRE, StatType.MAGIC_DAMAGE);
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
        if (level>=3){
            cooldown *= 0.9f;
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
    public float getBaseEndSizeSize(){
        baseEndSize = startSize +4f;

        if(level ==5){
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
