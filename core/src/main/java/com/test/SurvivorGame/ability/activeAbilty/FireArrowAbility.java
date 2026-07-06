package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.ability.AbilityService;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.core.stat.StatModifier;
import com.test.SurvivorGame.core.stat.ModifierType;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.projectile.FireArrowProjectile;
import com.test.SurvivorGame.world.World;

import java.util.ArrayList;
import java.util.List;

public class FireArrowAbility extends ActiveAbility {

    public static final String ID = "fire_arrow";

    private final Viewport viewport;
    private final Player player;
    private final World world;
    private final PlayerStats playerStats;
    private final  PlayerState playerState;
    private int level;

    // Ability base Stats
    private final float baseDuration = 3f;
    private final float baseWidth = 3f;
    private final float baseHeight= 0.6f;
    private final float baseSpeed = 7f;
    private final int basePierce = 3;
    private final float baseDamage = 0.5f;
    private final float baseCooldown = 1f;

    private Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    public FireArrowAbility(World world, Viewport viewport) {
        this.player = world.getPlayer();
        this.world = world;
        this.viewport = viewport;
        this.playerStats =  player.getPlayerState().getPlayerStats();
        this.playerState = player.getPlayerState();
        this.level = playerState.getPlayerData().abilities.getOrDefault(getID(), 0);
    }

    @Override
    protected void activate() {
        FireArrowProjectile fireArrowProjectile = new FireArrowProjectile(
            player.getX(),
            player.getY(),
            baseWidth*getSize(),
            baseHeight*getSize(),
            texture,
            player,
            viewport,
            getSpeed(),
            baseDuration,
            getDamage(),
            getPierce()
        );

        world.addAbility(fireArrowProjectile);
    }

    public void dispose() {
        texture.dispose();
    }

    public float getDamage() {
        float damage = baseDamage;
        damage *= playerStats.getStat(StatType.MAGIC_DAMAGE);
        damage *= playerStats.getStat(StatScope.FIRE, StatType.MAGIC_DAMAGE);
        if (level >= 2){
            damage = 1.15f;        }
        if (level>=5){
            damage = 1.15f;
        }
        return damage;

    }

    public float getSize() {
        float size = 1f;
        size *= playerStats.getStat(StatType.MAGIC_SIZE);
        size *= playerStats.getStat(StatScope.FIRE, StatType.MAGIC_SIZE);
        if (level>= 5){
            size *= 1.05f;}
        return size;
    }

    public float getSpeed() {
        float speed = baseSpeed;

        return speed;
    }

    public float getDuration(){
        float duration = baseDuration;
        if(level >= 4){
            duration *= 2;
        }
        return duration;
    }

    public int getPierce() {
        int level = playerState.getPlayerData().abilities.getOrDefault(getID(), 0);
        int pierce = basePierce;
        if (level >= 3) pierce += 2; // wenn die Ability Level 3 erreicht wird pierce um 2 erhöht
        return pierce;
    }

    public float getCooldown() {
        float cooldown = baseCooldown;
        cooldown *= playerStats.getStat(StatType.MAGIC_COOLDOWN);
        cooldown *= playerStats.getStat(StatScope.FIRE, StatType.MAGIC_COOLDOWN);
        return cooldown;

    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Fire Arrow";
    }

    @Override
    public int getMaxAmount() {
        return 5;
    }

    @Override
    public String getDescription(int level){
        switch(level){
            case 1:
                return "Shoots a fire arrow that explodes on impact";
            case 2:
                return "Fire arrow damage increased by 15%";
            case 3:
                return "Fire arrow pierce increases by 2";
            case 4:
                return"Duration increased" ;
            case 5:
                return "Size increased by 5% and damage increased by 15%";
            default:
                return "No description available";
        }
    }

//    public List<StatModifier> getModifiers(int amount){
//       int pierce = basePierce;
//        ArrayList<StatModifier> mods = new ArrayList<>();
//        if (amount >= 2){
//            pierce +=2;
//        }
//        // Level 3: +20% fire magic damage, +30% magic duration
//        if (amount >= 3 ) {
//            mods.add(new StatModifier(StatScope.FIRE, StatType.MAGIC_DAMAGE, 0.2f, ModifierType.PERCENT, "ability:" + getID()));
//            mods.add(new StatModifier(StatScope.FIRE, StatType.MAGIC_DURATION, 0.3f, ModifierType.PERCENT, "ability:" + getID()));
//        }
//
//        // Level 4: +30% magic size (cumulated with level 3 rules)
//        if (amount >= 4) {
//            mods.add(new StatModifier(StatScope.FIRE, StatType.MAGIC_SIZE, 0.3f, ModifierType.PERCENT, "ability:" + getID()));
//        }
//
//        // Level 5: increase damage to +70%
//        if (amount >= 5) {
//            mods.add(new StatModifier(StatScope.FIRE, StatType.MAGIC_DAMAGE, 1f, ModifierType.PERCENT, "ability:" + getID()));
//        }
//
//        return mods;

    }


