package com.test.SurvivorGame.ability.activeAbilty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.SurvivorGame.core.PlayerState;
import com.test.SurvivorGame.core.stat.PlayerStats;
import com.test.SurvivorGame.core.stat.StatScope;
import com.test.SurvivorGame.core.stat.StatType;
import com.test.SurvivorGame.entity.Player;
import com.test.SurvivorGame.entity.abilityObjects.projectile.Fireball;
import com.test.SurvivorGame.world.World;

public class FireballAbility extends ActiveAbility {

    public static final String ID = "fireball";

    private final Viewport viewport;
    private final Player player;
    private final World world;
    private final PlayerStats playerStats;
    private final PlayerState playerState;
    private final int level;

    private final float baseDuration = 3f;
    private final float baseWidth = 3f;
    private final float baseHeight = 0.6f;
    private final float baseSpeed = 7f;
    private final float baseDamage = 0.5f;
    private float duration = 2f;
    private float effectSize = 3f;
    private float speed = 6f;
    private float explosionRadius = 2f;
    private float baseCooldown = 1f; // müsst ihr noch anpassen

    private static float damage = 2f;

    private Texture texture = new Texture(Gdx.files.internal("Placeholder/ProjectileAbilityPH.png"));

    private Fireball fireball;


    public FireballAbility(World world, Viewport viewport) {
        this.player = world.getPlayer();
        this.world = world;
        this.playerStats = player.getPlayerState().getPlayerStats();
        this.playerState = player.getPlayerState();
        this.viewport = viewport;
        this.level = playerState.getPlayerData().abilities.getOrDefault(getID(), 0);
    }

    @Override
    public void activate() {
        fireball = new Fireball(
            player.getX(),
            player.getY(),
            baseWidth * getSize(),
            baseHeight * getSize(),
            texture,
            player,
            viewport,
            getSpeed(),
            getDuration(),
            getDamage()
        );
        world.addAbility(fireball);
    }

    public void dispose() {
        texture.dispose();
    }

    public float getDamage() {
        float damage = baseDamage;
        damage *= playerStats.getStat(StatType.MAGIC_DAMAGE);
        damage *= playerStats.getStat(StatScope.FIRE, StatType.MAGIC_DAMAGE);
        if (level >= 2){
            damage *= 1.15f;        }
        if (level==5){
            damage *= 1.15f;
        }
        return damage;
    }

    public float getSize() {
        float size = 1f;
        size *= playerStats.getStat(StatType.MAGIC_SIZE);
        size *= playerStats.getStat(StatScope.FIRE, StatType.MAGIC_SIZE);
        if(level >= 3){
            size *= 1.05f;
        }
        if(level == 5){
            size *= 1.05f;
        }
        return size;
    }

    public float getSpeed() {
        float speed = baseSpeed;

        return speed;
    }

    public float getDuration() {
        float duration = baseDuration;
        duration *= playerStats.getStat(StatType.MAGIC_DURATION);
        duration *= playerStats.getStat(StatScope.FIRE, StatType.MAGIC_DURATION);
        return duration;
    }

    public float getCooldown() {
        float cooldown = baseCooldown;
        cooldown *= playerStats.getStat(StatType.MAGIC_COOLDOWN);
        cooldown *= playerStats.getStat(StatScope.FIRE, StatType.MAGIC_COOLDOWN);
        if (level>=4){
            cooldown *= 0.85f;
        }
        return cooldown;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Fireball Ability";
    }

    @Override
    public int getMaxAmount() {
        return 5;
    }

    @Override
    public String getDescription(int level) {
        switch (level) {
            case 1:
                return "Shoots a fireball that explodes on impact";
            case 2:
                return "Fireball damage increased by 15%";
            case 3:
                return "Fireball size increases by 5%";
            case 4:
                return "Cooldown decreased by 15%";
            case 5:
                return "Size increased by 5% and damage increased by 15%";
            default:
                return "No description available";
        }
    }
}
